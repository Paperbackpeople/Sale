<template>
  <div>
    <Header title="Address List" />
    <div>
      <van-address-list
          v-model="chosenAddressId"
          :list="list"
          @add="onAdd"
          @edit="onEdit"
          @select="onSelect"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { Toast } from 'vant';
import Header from '@/components/Header.vue';
import axios from 'axios';
import store from "@/stores";

const router = useRouter();
const route = useRoute();
const chosenAddressId = ref(null);
const list = ref([]);
if (route.query.selectedAddressId) {
  chosenAddressId.value = parseInt(route.query.selectedAddressId);
}
// 获取地址列表并确保默认地址排在最前面
// 获取地址列表并确保默认地址排在最前面
const fetchAddresses = async () => {
  try {
    const response = await axios.get('/api/address/all', {
      params: { email: store.state.user },
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    console.log(response.data);
    if (response.data && response.data.length > 0) {
      // 格式化数据
      const formattedAddresses = response.data.map((addr) => ({
        id: addr.addressId,
        name: store.state.user,
        tel: addr.tel, // 电话号码
        address: `${addr.province} ${addr.city} ${addr.state} ${addr.addressLine}`, // 拼接完整地址
        isDefault: addr.isPrimary, // 将 isPrimary 转换为 isDefault
      }));

      // 将默认地址放在第一位
      list.value = formattedAddresses.sort((a, b) => b.isDefault - a.isDefault);

      // 如果没有选择地址，则选中默认地址或第一个地址
      if (!chosenAddressId.value) {
        const defaultAddress = formattedAddresses.find((addr) => addr.isDefault);
        chosenAddressId.value = defaultAddress ? defaultAddress.id : formattedAddresses[0].id;
      }
    } else {
      Toast('No addresses found');
      list.value = [];
    }
  } catch (error) {
    console.error('Failed to fetch addresses:', error);
    Toast.fail('Failed to load addresses');
  }
};

// 添加新地址
const onAdd = () => {
  router.push('/address-edit');
};

// 编辑地址
const onEdit = (item) => {
  router.push({ path: '/address-edit', query: { id: item.id } });
};

// onSelect 函数
const onSelect = (address) => {
  // 保存选中的地址ID到全局状态
  store.commit('setSelectedAddressId', address.id);

  // 返回上一页
  router.back();
};
onMounted(() => {
  fetchAddresses();
});
</script>

<style scoped>
:deep(.van-address-list__item) {
  border-bottom: 1px solid #f0f0f0;
}

:deep(.van-button) {
 background-color: #ffc400 !important; /* 设置按钮背景颜色为黄色 */
 color: #fff !important; /* 设置文字颜色为白色 */
 border-color: #ffc400 !important; /* 设置按钮边框颜色为黄色 */
}
</style>