<template>

<div class="create-account-container">
    <h1 style="padding: 50px; text-align: center; color: #0b407ce0;">同济基金交易系统 开户界面</h1>
    <el-form :model="form" :rules="rules" ref="formRef" label-width="120px" class="form-container">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="phoneNumber">
        <el-input v-model="form.phoneNumber" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="code">
        <el-input v-model="form.code" placeholder="请输入验证码">
          <template #append>
            <el-button @click="sendCodeService" type="primary" size="small">发送验证码</el-button>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="身份证号码" prop="idNumber">
        <el-input v-model="form.idNumber" placeholder="请输入身份证号码"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" placeholder="请输入密码" type="password" maxlength="16"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="form.confirmPassword" placeholder="请确认密码" type="password" ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="isTestLevel = !isTestLevel">{{isTestLevel ? "跳过风险评估" : "进行风险评估（可跳过）"}}</el-button>
      </el-form-item>
      <el-form-item v-if="isTestLevel">
        <RiskAssessment @update:level="handleLevelChange" />
      </el-form-item>
      <el-form-item >
        <el-button type="warning" @click="isBankcardNumber = !isBankcardNumber" style=" margin-top: 20px;">
          {{isBankcardNumber ? "跳过添加银行卡" : "添加银行卡（可跳过）"}}
        </el-button>
      </el-form-item>
      <el-form-item v-if="isBankcardNumber" label="银行卡号" prop="bankcardNumber">
        <el-input v-model="form.bankcardNumber" placeholder="请输入银行卡号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit" style="width: 15%; margin-top: 20px;">确定开户</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>
<script setup lang="ts">
</script>
