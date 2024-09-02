<template>
  <div id="app">
  <div class="container">
    <el-card class="content">
      <p class="intro-text">Last step, what do you want us to call you?</p>
      <div class="form-group">
      <el-input
          v-model="username"
          placeholder="Input your username"
          class="username-input"
      ></el-input>
      <el-button type="primary" @click="submitUsername">Start Ordering</el-button>
      </div>
    </el-card>
  </div>
  </div>
</template>

<script>
import { computed, ref, onBeforeMount } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import axios from "../axios";

export default {
  name: 'UsernameInput',
  setup() {
    const store = useStore();
    const router = useRouter();
    const username = ref('');

    onBeforeMount(() => {
      // 检查用户是否已登录
      console.log('User:', store.state.user);
      if (!store.state.user) {
        ElMessage.error('You need to log in first');
        router.push('/login');
      }
    });

    const submitUsername = () => {
      if (!username.value) {
        ElMessage.error('Please enter a username');
      }
      store.dispatch('saveUsername', username.value);
      router.push('/home');
    };

    return {
      username,
      submitUsername,
    };
  },
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

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
  padding: 0;
  width: 100vw;
  box-sizing: border-box;
}

.content {
  text-align: center;
  opacity: 0;
  transform: translateX(-100%);
  animation: slide-in 1s forwards;
  width: 100%;
  max-width: 500px;
  padding: 20px;
  background: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
}

.intro-text {
  margin-bottom: 20px;
  font-size: 18px;
  color: #333;
}

.username-input {
  width: 300px;
  margin-bottom: 20px;
}

.form-group {
  display: flex;
  flex-direction: column; /* Stack elements vertically */
  align-items: center;    /* Center align elements */
}

@keyframes slide-in {
  to {
    opacity: 1;
    transform: translateX(0);
  }
}
</style>