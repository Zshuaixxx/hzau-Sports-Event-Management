<script setup>
import { ref } from 'vue'

// 响应式数据
const loginForm = ref({
  UserAccount: '',
  UserPassword: ''
})

// 校验规则
// const rules = {
//   //   UserAccount: [
//   //     { required: true, message: '请输入账号', trigger: 'blur' },
//   //     { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
//   //   ],
//   //   UserPassword: [
//   //     { required: true, message: '请输入密码', trigger: 'blur' },
//   //     { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' }
//   //   ]
// }

// 引用表单
const loginFormRef = ref(null)

import { userLoginService } from '@/api/user'
import { useRouter } from 'vue-router'
const router = useRouter()

//点击登录
import { useUserStore } from '@/stores'

const userStore = useUserStore()
const handleSubmit = async () => {
  await loginFormRef.value.validate()
  const res = await userLoginService({
    AdminPassword: loginForm.value.UserPassword,
    AdminAccount: loginForm.value.UserAccount
  })
  console.log('登录接口返回:', res)
  if (res.data === 1) {
    //  userStore.setToken(res.data.token || '') // 假设token在data字段中，需根据实际情况调整
    userStore.setToken(loginForm.value.UserAccount)
    ElMessage.success('登录成功')
    router.push('/home/game')
  } else {
    ElMessage.error('账号或密码错误')
  }
}
</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" :offset="6" class="login-form-page">
      <div class="loginbox">
        <h3 class="login-title">欢迎登录运动会管理系统</h3>
        <div>
          <el-form
            :model="loginForm"
            ref="loginFormRef"
            label-width="80px"
            class="login-form"
          >
            <el-form-item label="账号" prop="UserAccount">
              <el-input v-model="loginForm.UserAccount"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="UserPassword">
              <el-input
                type="password"
                v-model="loginForm.UserPassword"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSubmit" class="but"
                >登录</el-button
              >
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped>
.login-page {
  height: 100vh;
  background-color: #fff;
  background-image: url('@/assets/logo.png');
  background-size: cover;
  background-position: center;
}
.loginbox {
  width: 300px;
  height: 300px;
  border-radius: 20px;
  justify-content: center;
  align-items: center;
  padding-right: 80px;
  background-color: rgba(255, 255, 255, 0.7);
}
.login-title {
  writing-mode: horizontal-tb; /* 确保文本水平排列 */
  text-align: center; /* 文字居中 */
  margin-left: 80px;
  margin-bottom: 50px;
}
.login-form-page {
  /* 垂直居中 */
  display: flex;
  justify-content: center;
  align-items: center;
  /* background-color: rgba(0, 0, 0, 0.5); */
}
.login-form {
  /* background-color: #eee3e3; */
  /* width: 400px;
  height: 400px; */
  /* background-color: rgba(0, 0, 0, 0.5); */
}
.but {
  width: 212.5px;
}
</style>
