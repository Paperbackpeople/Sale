<template>
  <div class="mine">
    <Header title="My Profile" />
    <div class="mine-content">
      <!-- 头像和用户名 -->
      <div class="profile-info">
        <img class="avatar" src="@/assets/avatar.png" alt="Avatar" />
        <span class="username">{{ username }}</span>
      </div>
      <!-- 默认地址展示 -->
      <div class="default-address">
        <van-icon name="location-o" size="24" />
        <!-- template部分 -->
        <span>
          {{ defaultAddress.province }} {{ defaultAddress.city }} {{ defaultAddress.state }} {{ defaultAddress.addressLine }}
          <template v-if="!defaultAddress.province && !defaultAddress.city && !defaultAddress.state && !defaultAddress.addressLine">
            No address available
          </template>
        </span>
      </div>
      <!-- '全部地址'按钮 -->
      <div class="menu-item">
        <van-button class="custom-button" type="primary" block @click="navigateTo('/address-list')">
          All Addresses
        </van-button>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import { onMounted, ref } from 'vue';
import { useStore } from "vuex";
import Footer from '@/components/Footer.vue';
import Header from '@/components/Header.vue';
import { Icon as VanIcon, Button as VanButton } from 'vant';
import axios from "axios";

const router = useRouter();
const store = useStore();

const navigateTo = (path: string) => {
  router.push(path);
};

const username = ref('Loading...');
const defaultAddress = ref({
  id: '',
  name: '',
  tel: '',
  address: '',
  isDefault: false,
  addressLine: '',
  province: '',
  city: '',
  state: '',
});

// 获取用户名
const fetchUsername = async () => {
  try {
    const response = await axios.get('/api/user/username', {
      params: { email: store.state.user },
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    username.value = response.data || 'No username set';
  } catch (error) {
    console.error('Failed to fetch username:', error);
    username.value = 'Error fetching username';
  }
};

// 获取默认地址或任意地址
// script部分
const fetchDefaultAddress = async () => {
  try {
    const response = await axios.get('/api/address/all', {
      params: { email: store.state.user },
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });

    const addresses = response.data || [];
    if (addresses.length === 0) {
      defaultAddress.value.address = 'No address available'; // 无地址
    } else {
      // 查找默认地址或使用第一个地址
      const defaultAddr = addresses.find((addr) => addr.isPrimary) || addresses[0];

      // 更新defaultAddress，注意这里需要匹配defaultAddress的结构
      defaultAddress.value = {
        id: defaultAddr.addressId,
        name: defaultAddr.name || '', // 如果有name字段
        tel: defaultAddr.tel,
        address: defaultAddr.addressLine,
        province: defaultAddr.province,
        city: defaultAddr.city,
        state: defaultAddr.state,
        addressLine: defaultAddr.addressLine,
        isDefault: defaultAddr.isPrimary,
      };
    }
  } catch (error) {
    console.error('Failed to fetch addresses:', error);
    defaultAddress.value.address = 'Error fetching address';
  }
};
onMounted(() => {
  fetchUsername();
  fetchDefaultAddress();
});
</script>

<style scoped>
.mine {
  display: flex;
  flex-direction: column;
  font-size: 12px;
  height: 100%; /* 撑满视口高度 */
}

.mine-content {
  flex: 1; /* 填充剩余空间 */
  margin-top: 20px;
  padding: 15px;
}

.profile-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
}

.username {
  margin-left: 15px;
  font-size: 18px;
  font-weight: bold;
}

.default-address {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.default-address span {
  margin-left: 10px;
  font-size: 16px;
}

:deep(.custom-button) {
  background-color: #ffc400 !important; /* 背景色 */
  border-color: #ffc400 !important;    /* 边框色 */
  color: #fff !important;              /* 字体颜色 */
}
</style>