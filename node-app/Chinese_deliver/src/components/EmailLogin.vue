<template>
  <div id="app">
    <div class="login-container">
      <el-card class="login-card">
        <h2>Email Login</h2>
        <el-form :model="loginForm" :rules="rules" ref="loginRef" label-width="80px">
          <el-form-item label="Email" prop="email">
            <el-input v-model="loginForm.email" placeholder="Input Email Address"></el-input>
          </el-form-item>
          <el-form-item label="Code" prop="code">
            <el-row :gutter="10" type="flex" justify="start" align="middle">              
              <el-col :span="19">
                <el-input v-model="loginForm.code" placeholder="Input the validation code" style="width: 100%;"></el-input>
              </el-col>
              <el-col :span="5">
                <el-button :disabled="isButtonDisabled" @click="handleSendCode" class="code-button" style="width: 100%;">
                  {{ timer > 0 ? timer + 's' : 'Send' }}
                </el-button>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item>
            <el-button :disabled="isSubmitButtonDisabled" type="primary" @click="submitForm">Login</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>
import { ref, onBeforeUnmount, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import axios from '../axios';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

export default {
  name: 'EmailLogin',
  setup() {
    const router = useRouter();
    const store = useStore();

    // 初始化表单数据
    const loginForm = ref({
      email: localStorage.getItem('email') || '', // 从 localStorage 获取 email 值
      code: ''
    });

    const rules = {
      email: [
        { required: true, message: 'Please enter your email' }
      ],
      code: [{ required: true, message: 'Please enter the code' }]
    };

    const timer = ref(0);
    const isButtonDisabled = ref(false);
    const isSubmitButtonDisabled = ref(false);
    const timerInterval = ref(null);

    // 校验 email
    const validateEmail = () => {
      const emailRegex = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
      if (!emailRegex.test(loginForm.value.email)) {
        ElMessage.error('Please enter a valid email address');
        return false;
      }
      return true;
    };

    // 校验 code
    const validateCode = () => {
      const codeRegex = /^\d{6}$/;
      if (!codeRegex.test(loginForm.value.code)) {
        ElMessage.error('Please enter a 6-digit code');
        return false;
      }
      return true;
    };

    const sendCode = async () => {
      if (timer.value > 0) return;

      // 启动计时器并禁用按钮
      timer.value = 70;
      isButtonDisabled.value = true;
      timerInterval.value = setInterval(() => {
        timer.value -= 1;
        if (timer.value <= 0) {
          clearInterval(timerInterval.value);
          isButtonDisabled.value = false;
        }
      }, 1000);
      try {
        console.log('Sending code to:', loginForm.value.email);
        await axios.post('/api/send_code', { email: loginForm.value.email }, {
          headers: { 'Content-Type': 'application/json' }
        });
        ElMessage.success('Code sent successfully');
      } catch (error) {
        console.error('Error details:', error);

        ElMessage.error('Failed to send code');

        // 如果请求失败，重置计时器和按钮状态
        clearInterval(timerInterval.value);
        timer.value = 0;
        isButtonDisabled.value = false;
      }
    };

    const handleSendCode = () => {
      if (validateEmail()) {
        sendCode().catch(error => {
          ElMessage.error('An error occurred while sending the code');
          console.error('Send Code Error:', error);
        });
      }
    };

    const submitForm = async () => {
      if (validateEmail() && validateCode() && !isSubmitButtonDisabled.value) {
        isSubmitButtonDisabled.value = true;
        try {
          // 保存 email 到 localStorage
          localStorage.setItem('email', loginForm.value.email);

          // 调用 Vuex 的 login action
          await store.dispatch('login', {
            email: loginForm.value.email,
            code: loginForm.value.code
          });
          await store.dispatch('clearCartList');
        } catch (error) {
          ElMessage.error('Login failed');
        } finally {
          isSubmitButtonDisabled.value = false;
        }
      } else {
        ElMessage.error('Please complete the form');
      }
    };

    // 页面销毁时清除计时器
    onBeforeUnmount(() => {
      if (timerInterval.value) {
        clearInterval(timerInterval.value);
      }
    });

    return {
      loginForm,
      rules,
      timer,
      isButtonDisabled,
      isSubmitButtonDisabled,
      handleSendCode,
      submitForm
    };
  }
};
</script>

<style scoped>
#app {
  height: 100vh;
  width: 100vw;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
  padding: 0;
  width: 100vw;
  box-sizing: border-box;
}

.login-card {
  width: 100%;
  max-width: 360px;
  padding: 30px 20px;
  background: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.code-button {
  margin-left: 10px;
}

@media (max-width: 600px) {
  .login-card {
    padding: 15px;
  }

  .code-button {
    font-size: 12px;
  }
}
</style>
