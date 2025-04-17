<template>
  <div class="risk-assessment">
    <el-card class="risk-card">
      <template #header>
        <div class="card-header">
          <h2>用户风险承受能力评估问卷</h2>
        </div>
      </template>
      <el-form ref="formRef" label-position="top">
        <el-form-item label="您对投资风险的理解程度如何?" prop="q1">
          <el-radio-group v-model="responses.q1">
            <div class="radio-item">
              <el-radio :label="0">非常保守</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="1">稍微保守</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="2">中等</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="3">稍微激进</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="4">非常激进</el-radio>
            </div>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="您投资的主要目标是什么?" prop="q2">
          <el-radio-group v-model="responses.q2">
            <div class="radio-item">
              <el-radio :label="0">长期财富增值</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="1">中期资金增长</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="2">短期资本</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="3">稳定资金流需求</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="4">非常追求收益</el-radio>
            </div>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="您能接受的最大年度投资损失是多少?" prop="q3">
          <el-radio-group v-model="responses.q3">
            <div class="radio-item">
              <el-radio :label="0">不超过5%的损失</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="1">5%-10%的损失</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="2">10%-20%的损失</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="3">超过20%的损失</el-radio>
            </div>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="您的投资经验和知识水平如何?" prop="q4">
          <el-radio-group v-model="responses.q4">
            <div class="radio-item">
              <el-radio :label="0">完全没有投资经验</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="1">了解一些基础投资知识</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="2">有一定投资经验</el-radio>
            </div>
            <div class="radio-item">
              <el-radio :label="3">经验丰富</el-radio>
            </div>
          </el-radio-group>
        </el-form-item>
        
        <el-button type="success" @click="submitForm" class="submit-button">提交评估</el-button>
      </el-form>
      
      <div v-if="submitted" class="result">
        <el-divider>评估结果</el-divider>
        <p>您的风险承受能力评分是：{{ score }}</p>
        <p>根据评分，您的风险偏好为：{{ riskLevel }}</p>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, defineEmits } from 'vue';
import { ElMessage } from 'element-plus';

const emit = defineEmits(['update:level']);
const formRef = ref();
const level = ref(0);
const submitted = ref(false);
const responses = ref<{ [key: string]: number }>({
  q1: -1,
  q2: -1,
  q3: -1,
  q4: -1
});

const score = ref<number | null>(null);
const riskLevel = ref<string>('');

// 计算风险等级
const calculateRiskLevel = (totalScore: number): string => {
  level.value = Math.floor(totalScore / 4);
  
  switch (level.value) {
    case 0:
      return '低风险的保守型';
    case 1:
      return '中低风险的较稳健型';
    case 2:
      return '中风险的稳健型';
    case 3:
      return '中高风险的进取型';
    default:
      return '高风险的激进型';
  }
};

// 验证所有问题是否已回答
const validateResponses = (): boolean => {
  const values = Object.values(responses.value);
  for (const val of values) {
    if (val === -1) {
      ElMessage.warning('请回答所有问题');
      return false;
    }
  }
  return true;
};

// 提交表单并展示结果
const submitForm = () => {
  if (!validateResponses()) return;
  
  score.value = Object.values(responses.value).reduce((acc, score) => acc + score, 0);
  submitted.value = true;
  riskLevel.value = calculateRiskLevel(score.value);
  
  // 将风险等级传回父组件
  emit('update:level', level.value);
  
  ElMessage.success(`评估完成，您的风险偏好为: ${riskLevel.value}`);
};
</script>

<style scoped>
.risk-assessment {
  width: 100%;
}

.risk-card {
  height: 875px;
  position: relative;
  border-radius: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

}

.card-header {
  display: flex;
  justify-content: center;
  align-items: center;
}

.card-header h2 {
  color: #34a853;
  font-size: 25px;
  font-weight: bold;
}

.radio-item {
  margin: 20px 8px;
}

/* 修改所有单选框文字大小 */
:deep(.el-radio) {
  font-size: 18px !important;
}

/* 修改表单项标签的字体大小 */
:deep(.el-form-item__label) {
  font-size: 16px !important;
  font-weight: 500;
  margin-bottom: 12px;
}

.submit-button {
  position:relative;
  left:25%;
  width: 50%;
  height:45px;
  font-size: 17px;
  margin-top: 20px;
  background-color: #34a853;
  border-radius: 10px;
  border-color:#34a853;
}

.result {
  margin-top: 20px;
  padding: 15px;
  background-color: #f0f9eb;
  border-radius: 8px;
  border:none;
}

.result p {
  margin: 10px 0;
  font-size: 20px;
}

.result p:last-child {
  font-weight: bold;
  color: #409eff;
}

/* 修改分割线文字大小 */
:deep(.el-divider__text) {
  font-size: 20px;
  font-weight: bold;
  color: #34a853;
}
</style> 