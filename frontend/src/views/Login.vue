<template>
  <div class="login-container">
      <el-card class="login-card">
        <div class="logo-container">
          <img src="@/assets/logo.png" alt="WiseInvest" class="logo-img"/>
          <h2 class="login-title">用户登录</h2>
        </div>
        
        <el-form 
          ref="loginFormRef"
          :model="loginForm" 
          :rules="rules" 
          label-position="top"
        >
          <el-form-item prop="phoneNumber">
            <template #label>
              <span class="required-label">手机号</span>
            </template>
            <el-input v-model="loginForm.phoneNumber" placeholder="请输入您的手机号" />
          </el-form-item>
          <el-form-item prop="password">
            <template #label>
              <span class="required-label">密码</span>
            </template>
            <el-input v-model="loginForm.password" type="password" placeholder="请输入您的密码" />
          </el-form-item>
          <div class="button-group">
            <el-button type="primary" @click="handleLogin" :loading="loading" class="login-button">登录</el-button>
            <el-button type="default" @click="router.push('/resetpw')" class="forget-button">忘记密码</el-button>
          </div>
          <el-button type="success" @click="router.push('/create')" class="register-button">开户</el-button>
        </el-form>  
      </el-card>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loginFormRef = ref()
const loading = ref(false)

const loginForm = reactive({
  phoneNumber: '',
  password: ''
})

const rules = {
  phoneNumber: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
  ]
}

// 模拟的用户数据
const mockUsers = [
  {
    phoneNumber: '13345678900',
    password: '123456',
    token: '123',
    userName: 'test user'
  }
]

const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  try {
    await loginFormRef.value.validate()
    loading.value = true
    
    // 模拟后端验证过程
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    const user = mockUsers.find(u => 
      u.phoneNumber === loginForm.phoneNumber && 
      u.password === loginForm.password
    )
    
    if (user) {
      // 保存用户信息到 localStorage
      localStorage.setItem('token', user.token)
      localStorage.setItem('userName', user.userName)
      
      ElMessage.success('登录成功')
      // 登录成功后跳转到产品页面
      router.push('/product')
    } else {
      ElMessage.error('手机号或密码错误')
    }
  } catch (error) {
    console.error('登录验证失败:', error)
    ElMessage.error('请检查输入是否正确')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #9cb7e7;
}
  
.login-card {
  width: 400px;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.logo-container {
  display: flex;
  justify-content:space-around;
  align-items: center;
  margin-bottom: 20px;
}

.logo-img {
  justify-self: left;
  height: 60px;
  object-fit: contain;
}

.login-title {
  font-size: 30px;

  color: #344dbe;
  margin-bottom: 30px;
  font-weight: bold;
}

.required-label::before {
  content: '';
  color: #f56c6c;
  margin-right: 4px;
}

.button-group {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}

.login-button {
  flex: 1;
  background-color: #1a73e8;
}

.forget-button {
  flex: 1;
  color: #1a73e8;
  border-color: #1a73e8;
  background-color: #f8f9fa;
}

.register-button {
  width: 100%;
  background-color: #34a853;
}

:deep(.el-input__wrapper) {
  background-color: #f8f9fa;
}

:deep(.el-form-item__label) {
  color: #333;
  font-size: 14px;
  margin-bottom: 8px;
}

:deep(.el-button) {
  height: 40px;
  font-size: 14px;
}

:deep(.el-input__inner) {
  height: 40px;
}
</style>
