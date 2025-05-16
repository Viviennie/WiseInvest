<template>
  <div class="reset-password-container">
    <el-card class="reset-password-form">
      <div class="logo-container">
        <img src="@/assets/logo.png" alt="WiseInvest" class="logo-img"/>
        <h2 class="reset-title">找回密码</h2>
      </div>
      <el-form :model="form" :rules="rules">
        <el-form-item label="手机号" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="验证码" prop="code">
          <el-input v-model="form.code" placeholder="请输入验证码">
            <template #append>
              <el-button @click="sendCodeService(form.phoneNumber)" type="primary" size="small">发送验证码</el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入新密码" />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" type="password" placeholder="请确认密码" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" style="width: 100%">提交</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
  
<script setup lang="ts">
import { ref } from 'vue';
import { ElForm, ElFormItem, ElInput, ElButton, ElMessage } from 'element-plus';
import { resetPwService,sendCodeService } from '@/api/login';
import type { LoginDTO } from '@/types/login';
import { useRouter } from 'vue-router';
  
interface FormData {
  phoneNumber: string;
  code: string;
  password: string;
  confirmPassword: string;
}

const form = ref<FormData>({
  phoneNumber: '',
  code: '',
  password: '',
  confirmPassword: '',
});
  
const router = useRouter();

// 验证规则
const rules = {
  phoneNumber: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1\d{10}$/, message: '手机号必须是11位数字', trigger: 'blur' },
  ],
  code: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, max: 16, message: '密码长度在6到16位之间', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
  ]
};
  
// 提交表单
const handleSubmit = async () => {
  if(form.value.password === ''){
    ElMessage.warning('密码不可以为空');
    return;
  }
  if(form.value.code !== '123'){
    ElMessage.error('验证码错误');
    return;
  }
  if(form.value.password === form.value.confirmPassword){
    const data: LoginDTO = {
      phoneNumber: form.value.phoneNumber,
      password: form.value.password,
      userType: 1,
    }
    await resetPwService(data);
    ElMessage.success('密码重置成功'); 
    router.push('/login');
  }else{
    ElMessage.error('请输入两次一致的密码');
    form.value.password = '';
    form.value.confirmPassword = '';
  }
};
</script>

<style scoped>
.reset-password-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #9cb7e7;
}

.reset-password-form {
  width: 400px;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.logo-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-bottom: 20px;
}

.logo-img {
  justify-self: left;
  height: 60px;
  object-fit: contain;
}

.reset-title {
  font-size: 30px;
  color: #344dbe;
  margin-bottom: 30px;
  font-weight: bold;
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
  
