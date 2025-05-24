<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { fetchProducts, searchProducts, fetchNetValue, fetchTransactionDate } from '@/api/product';
import type { Product } from '@/types/product';
import { useRoute, useRouter } from 'vue-router';
import { fetchRecommendations } from '@/api/product'; // 新增api导入
import { ArrowDownBold } from '@element-plus/icons-vue'

const searchKeyword = ref('');
const products = ref<Product[]>([]);
const totalProducts = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const appliedSearchKeyword = ref('');
const dialogVisible = ref(false);
const selectedProduct = ref<Product | null>(null);
const selectedProductNetValue = ref<number | null>(null);
const transactionDate = ref<string | null>(null);
const router = useRouter();
const route = useRoute();

const recommendedCurrentPage = ref(1);
const recommendedPageSize = ref(5);
const recommendedTotal = ref(0);



const recommendedProducts = ref<Product[]>([]); // 新增推荐商品列表

// Update paginatedProducts as computed property
const paginatedProducts = computed(() => {
  let filteredProducts = products.value;
  if (appliedSearchKeyword.value) {
    filteredProducts = products.value.filter((product) =>
        product.productName.toLowerCase().includes(appliedSearchKeyword.value.toLowerCase())
    );
  }
  return filteredProducts.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
});

// Search handler
const handleSearch = async () => {
  if (searchKeyword.value.trim()) {
    try {
      const res = await searchProducts(searchKeyword.value);
      products.value = res.data as Product[];
      totalProducts.value = products.value.length;
      appliedSearchKeyword.value = searchKeyword.value;
    } catch (error) {
      ElMessage.error('搜索失败');
    }
  } else {
    loadProducts();
    appliedSearchKeyword.value = '';
  }
};

// Load products
const loadProducts = async () => {
  try {
    const res = await fetchProducts(currentPage.value, pageSize.value);
    products.value = res.data as Product[];
    totalProducts.value = products.value.length;
    appliedSearchKeyword.value = '';
  } catch (error) {
    ElMessage.error('加载产品失败');
  }
};

// Show product details
const showProductDetails = async (product: Product) => {
  selectedProduct.value = product;
  try {
    if(!product.productId || !transactionDate.value){
      return ElMessage.error('获取交易日期失败');
    }
    const res = await fetchNetValue(product.productId, transactionDate.value);
    selectedProductNetValue.value = res.data;
  } catch (error) {
    ElMessage.error('获取净值失败');
    selectedProductNetValue.value = null;
  }
  dialogVisible.value = true;
};

// Clear selected product
const clearSelectedProduct = () => {
  selectedProduct.value = null;
};

// Handle page change
const handlePageChange = (newPage: number) => {
  currentPage.value = newPage;
  loadProducts();
};

// Get transaction date
const getTransactionDate = async () => {
  try {
    const res = await fetchTransactionDate();
    transactionDate.value = res.data as string;
  } catch (error) {
    ElMessage.error('获取交易日期失败');
    transactionDate.value = null;
  }
};

// Level function
const Level = (level:number | undefined) => {
  switch (level) {
    case 0: return '低风险型';
    case 1: return '中低风险型';
    case 2: return '中风险型';
    case 3: return '中高风险型';
    default: return '高风险型';
  }
};


// 新增获取推荐商品方法
const getRecommendedProducts = async () => {
  try {
    const token = localStorage.getItem('token'); // 假设token存储在localStorage
    if (!token) {
      ElMessage.error('请先登录');
      return;
    }

    const res = await fetchRecommendations({
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    recommendedProducts.value = res.data as Product[];
    recommendedTotal.value = recommendedProducts.value.length;  // 设置总条数
  } catch (error) {
    ElMessage.error('获取推荐商品失败');
  }
};
const recommendedPaginated = computed(() => {
  const start = (recommendedCurrentPage.value - 1) * recommendedPageSize.value;
  const end = start + recommendedPageSize.value;
  return recommendedProducts.value.slice(start, end);
});

const handleRecommendedPageChange = (newPage: number) => {
  recommendedCurrentPage.value = newPage;
};

onMounted(() => {
  loadProducts();
  getTransactionDate();
  getRecommendedProducts(); // 新增获取推荐商品
});

const subscription = (productId:number,productName:string) => {
  router.push({
    path: '/subscription',
    query: {
      productId,
      productName,
    },
  });
};

const redemption = (productId:number,productName:string) => {
  router.push({
    path: '/redemption',
    query: {
      productId,
      productName,
    },
  });
};


</script>

<template>
  <el-card class="main-container">
    <!-- 搜索框保持原样 -->
    <div style="text-align: center; margin-bottom: 20px;">
      <el-input class="input" v-model="searchKeyword" placeholder="请输入关键词" style="width: 70%;" />
      <el-button class="inputbutton" @click="handleSearch" style="margin-left: 20px;" type="primary">搜索</el-button>
    </div>

    <!-- 主产品表格 -->
    <el-table :data="paginatedProducts" style="width: 100%; margin-bottom: 40px;">
      <!-- 原有列定义保持完全不变 -->
      <el-table-column prop="productName" label="产品名称" width="400" align="center"/>
      <el-table-column prop="productType" label="产品类型" width="300" align="center"/>
      <el-table-column prop="riskLevel" label="风险等级" width="300" align="center">
        <template #default="scope">
          <span>{{ Level(scope.row.riskLevel) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300" align="center">
        <template #default="scope">
          <el-button @click="showProductDetails(scope.row)" type="text">查看详情</el-button>
          <el-button @click="subscription(scope.row.productId,scope.row.productName)" type="text">申购</el-button>
          <el-button @click="redemption(scope.row.productId,scope.row.productName)" type="text">赎回</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页保持原样 -->
    <div class="pagination-container" style="margin-top: 20px; display: flex; justify-content: center;">
      <el-pagination
          layout="prev, pager, next"
          :total="totalProducts"
          :page-size="pageSize"
          :current-page="currentPage"
          @current-change="handlePageChange"
          background
      />
    </div>

    <!-- 推荐商品 -->
    <div class="recommendation-section" v-if="recommendedProducts.length > 0" style="margin-top: 20px;">
      <div class="title-container">
        <h3 style="color: #0b407ce0; margin: 0px">推荐商品</h3>
        <el-icon :size="20" color="#0b407ce0" class="hand-icon">
          <ArrowDownBold />
        </el-icon>
      </div>
      <el-table :data="recommendedPaginated" style="width: 100%;">
        <el-table-column prop="productName" label="产品名称" width="400" align="center"/>
        <el-table-column prop="productType" label="产品类型" width="300" align="center"/>
        <el-table-column prop="riskLevel" label="风险等级" width="300" align="center">
          <template #default="scope">
            <span>{{ Level(scope.row.riskLevel) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="300" align="center">
          <template #default="scope">
            <el-button @click="showProductDetails(scope.row)" type="text">查看详情</el-button>
            <el-button @click="subscription(scope.row.productId,scope.row.productName)" type="text">申购</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-container" style="margin-top: 20px; display: flex; justify-content: center;">
        <el-pagination
            layout="prev, pager, next"
            :total="recommendedTotal"
            :page-size="recommendedPageSize"
            :current-page="recommendedCurrentPage"
            @current-change="handleRecommendedPageChange"
            background
        />
      </div>
    </div>

    <!-- 原有对话框保持完全不变 -->
    <el-dialog
        v-model="dialogVisible"
        :title="selectedProduct ? selectedProduct.productName : '产品详情'"
        custom-class="custom-dialog"
        width="50%"
        top="15vh"
        @close="clearSelectedProduct"
    >
      <!-- 对话框内容保持不变 -->
      <div v-if="selectedProduct" class="product-details-content">
        <dl class="product-info">
          <dt>产品名称：</dt><dd>{{ selectedProduct.productName }}</dd>
          <dt>产品类型：</dt><dd>{{ selectedProduct.productType || '未指定' }}</dd>
          <dt>风险等级：</dt><dd>{{ Level(selectedProduct.riskLevel) }}</dd>
          <dt>净值：</dt><dd>{{ selectedProductNetValue !== null ? selectedProductNetValue.toFixed(4) : '无信息' }}</dd>
        </dl>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<style scoped>
.main-container {
  width: 99.9%;
  min-height: 100vh; /* 改为最小高度 */
  padding: 20px;
  box-sizing: border-box;
}

/* 修改2：添加间距 */
.el-table {
  margin-bottom: 40px;
}

/* 修改3：推荐商品区域调整 */
.recommendation-section {
  border-top: 2px solid #f0f0f0;
  padding-top: 40px;
}

.title-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  margin: -10px 0 20px;
}

/* 手指图标动画 */
.hand-icon {
  animation: point-down 1.5s infinite;
  transform-origin: center;
  cursor: pointer;
}

@keyframes point-down {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(5px);
  }
}

.product-details-content {
  text-align: center;
  display: flex;
  flex-direction: column;
}

.product-info {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 10px;
  font-size: 1.3em;
}

.product-info dt {
  font-weight: bold;
  color: #555;
}

.product-info dd {
  margin: 0;
}

.dialog-footer {
  text-align: right;
}

.dialog-footer .el-button {
  background-color: #409EFF;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  transition: background-color 0.3s, transform 0.3s;
}

.dialog-footer .el-button:hover {
  background-color: #66B1FF;
  transform: translateY(-2px);
}

.dialog-footer .el-button:active {
  background-color: #3A8EE6;
  transform: translateY(1px);
}

/* 新增推荐商品间距 */
.recommendation-section {
  margin-top: 40px;
  border-top: 1px solid #eee;
  padding-top: 30px;
}
.recommendation-section .pagination-container {
  margin: 15px 0 10px;
}
</style>
