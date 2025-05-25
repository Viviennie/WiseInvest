<template>
  <!-- 页面整体容器 -->
  <div class="create-account-container">
    <!-- 卡片容器，动态 class 控制是否展示风险评估 -->
    <div class="card-container" :class="{ 'show-assessment': isTestLevel }">
      <!-- 注册信息卡片 -->
      <el-card class="register-card">
        <div class="card-header">
          <!-- Logo -->
          <img src="@/assets/logo.png" alt="WiseInvest" class="logo-img"/>
          <h2>用户注册</h2>
        </div>

        <!-- 表单组件，绑定模型与校验规则 -->
        <el-form :model="form" :rules="rules" ref="formRef" label-position="top" class="form-container">
          <!-- 姓名 -->
          <el-form-item label="姓名" prop="name">
            <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
          </el-form-item>

          <!-- 手机号 -->
          <el-form-item label="手机号" prop="phoneNumber">
            <el-input v-model="form.phoneNumber" placeholder="请输入手机号"></el-input>
          </el-form-item>

          <!-- 验证码 -->
          <el-form-item label="验证码" prop="code">
            <el-input v-model="form.code" placeholder="请输入验证码">
              <!-- 右侧插槽：发送验证码按钮 -->
              <template #append>
                <el-button @click="sendCodeService" type="primary" size="small">发送验证码</el-button>
              </template>
            </el-input>
          </el-form-item>

          <!-- 身份证号 -->
          <el-form-item label="身份证号码" prop="idNumber">
            <el-input v-model="form.idNumber" placeholder="请输入身份证号码"></el-input>
          </el-form-item>

          <!-- 密码 -->
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" placeholder="请输入密码" type="password" maxlength="16"></el-input>
          </el-form-item>

          <!-- 确认密码 -->
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input v-model="form.confirmPassword" placeholder="请确认密码" type="password" ></el-input>
          </el-form-item>

          <!-- 是否进行风险评估 -->
          <el-form-item>
            <el-button type="success" @click="isTestLevel = !isTestLevel" class="assessment-button">
              {{ isTestLevel ? "跳过风险评估" : "进行风险评估" }}
            </el-button>
          </el-form-item>

          <!-- 是否添加银行卡 -->
          <el-form-item>
            <el-button type="warning" @click="isBankcardNumber = !isBankcardNumber" class="bankcard-button">
              {{isBankcardNumber ? "跳过添加银行卡" : "添加银行卡"}}
            </el-button>
          </el-form-item>

          <!-- 银行卡号（条件渲染） -->
          <el-form-item v-if="isBankcardNumber" label="银行卡号" prop="bankcardNumber">
            <el-input v-model="form.bankcardNumber" placeholder="请输入银行卡号"></el-input>
          </el-form-item>

          <!-- 提交开户按钮 -->
          <el-form-item>
            <el-button type="primary" @click="handleSubmit" class="submit-button">确定开户</el-button>
          </el-form-item>
        </el-form>
      </el-card>

      <!-- 风险评估组件（条件展示） -->
      <div v-if="isTestLevel" class="assessment-container">
        <RiskAssessment @update:level="handleLevelChange" />
      </div>
    </div>
  </div>
</template>
