<script lang="ts">
import { defineComponent, computed, ref, onMounted } from 'vue';
import { useStore } from 'vuex';
import Header from '@/components/Header.vue';
import axios from 'axios';
import router from "@/router";
import {ElMessage} from "element-plus";

export default defineComponent({
  components: { Header },
  setup() {
    const store = useStore();
    const isSubmitting = ref(false);  // 定义按钮状态
    const contact = ref({
      tel: '',
      name: '',
      address: ''
    });
    const selectedAddressId = ref(null);
    // 获取选中商品和总金额
    const checkedList = computed(() => store.state.checkedList);
    const items = computed(() => checkedList.value.items);
    const totalAmount = computed(() => checkedList.value.totalPrice / 100);
    const isTokenExpired = computed(() => store.getters.isTokenExpired);

    const onEdit = () => {
      // 跳转到地址列表页面，并携带当前选中的地址ID
      router.push({
        path: '/address-list',
        query: { selectedAddressId: selectedAddressId.value }
      });
    };


    onMounted(async () => {
      const email = store.state.username;
      const selectedAddressId = store.state.selectedAddressId;

      try {
        // 获取用户姓名
        const userResponse = await axios.get('/api/user/username', {
          params: { email: store.state.user },
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        });
        const userName = userResponse.data || 'No username set';

        // 获取所有地址
        const addressResponse = await axios.get('/api/address/all', {
          params: { email : store.state.user },
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        });
        const addresses = addressResponse.data;

        let selectedAddress;
        if (addresses && addresses.length > 0) {
          if (selectedAddressId) {
            // 根据选中的地址ID获取地址
            selectedAddress = addresses.find(addr => addr.addressId === parseInt(selectedAddressId));
          }
          if (!selectedAddress) {
            // 如果没有选中的地址，使用默认地址或第一个地址
            selectedAddress = addresses.find(addr => addr.isPrimary) || addresses[0];
          }

          if (selectedAddress) {
            contact.value = {
              name: userName, // 姓名保持不变
              tel: selectedAddress.tel,
              address: `${selectedAddress.addressLine}, ${selectedAddress.city}, ${selectedAddress.state}, ${selectedAddress.province}`
            };
          }
        }
      } catch (error) {
        console.error('获取用户或地址信息时出错:', error);
      }
    });
    const onPay = async () => {
      // 验证 name 和 tel 是否为空
      if (!contact.value.name || !contact.value.tel) {
        ElMessage.error('Please add your address before paying.');
        onEdit(); // 跳转到地址编辑页面
        return;
      }

      let tokenInfo = store.state.tokenInfo;

      // 检查 token 是否已被使用
      if (!tokenInfo.token || tokenInfo.tokenUsed || !isTokenExpired.value) {
        try {
          isSubmitting.value = true;
          const tokenResponse = await axios.get('/api/generateToken');
          const newToken = tokenResponse.data;
          store.commit('setTokenInfo', newToken);
          tokenInfo = store.state.tokenInfo;
          console.log('Token generated:', tokenInfo);
        } catch (error) {
          console.error('Error generating token:', error);
          isSubmitting.value = false;
          return;
        }
      }

      const orderItems = items.value.map(item => ({
        productId: item.productId, // 从商品数据中获取 productId
        quantity: item.quantity,
        price: item.price,
      }));

      try {
        const response = await axios.post(
            '/api/createOrder',
            {
              userId: store.state.user,
              totalAmount: totalAmount.value,
              orderItems: orderItems,
              location: contact.value.address,
              status: 'pending',
              token: tokenInfo.token, // 发送请求时附带 Token
            },
            {
              headers: {
                'Content-Type': 'application/json',
              }
            }
        );

        if (response.status === 201) {
          console.log('Order created successfully:', response.data);

          // 标记 Token 已使用
          store.commit('markTokenAsUsed');

          // 清空购物车
          store.commit('clearCart');

          // 跳转到订单页面
          router.push('/order');
        } else {
          console.error('Order creation failed');
        }
      } catch (error) {
        console.error('An error occurred while creating the order:', error);
      } finally {
        isSubmitting.value = false; // 恢复按钮状态
      }
    };

    return {
      contact,
      items,
      totalAmount,
      onEdit,
      onPay,
      isSubmitting
    };
  }
});
</script>
<template>
  <div class="create-order">
    <Header title="Create Order" />
    <van-contact-card type="edit" :tel="contact.tel" :name="contact.name" :address="contact.address" @click="onEdit" />
    <div class="content">
      <div v-for="(item, index) in items" :key="index">
        <van-card
            :num="item.quantity"
            :price="item.price"
            :title="item.name"
            :thumb="item.image || (item.images && item.images[0]) || '/images/default-image.jpg'"
        />
      </div>
    </div>
    <div class="pay-wrap">
      <div class="amount-container">
      <span>Total:</span>
      <span>${{ totalAmount}}</span>  <!-- Assuming totalAmount was multiplied by 100 -->
    </div>
      <van-button class="pay-btn"
                  type="primary"
                  block color="#ffc400"
                  @click="onPay"
                  :disabled="isSubmitting">Pay Now</van-button>
    </div>
  </div>
</template>

<style scoped>
.create-order {
  display: flex;
  flex-flow: column;
  height: 100%;
  .content{
    flex: 1;
    overflow-y: auto;
    padding: 10px 0;
    font-size: 18px;

    > div{
      margin: 10px 0;
      .van-card{
        font-size: 16px;
      }
    }
  }
  .pay-wrap {
    padding: 10px 0;
    width: 100%;
    background-color: #fff;
    border-top: 1px solid #e9e9e9;
    display: flex;
    flex-direction: column; /* 垂直布局，使按钮位于底部 */
  }

  .pay-wrap .amount-container {
    display: flex;
    justify-content: space-between; /* 将两个元素分别放在左右两边 */
    padding: 0 5%; /* 给左右两边添加一些内边距 */
    font-size: 14px;
    margin-bottom: 10px; /* 给金额和按钮之间添加一些间距 */
  }

  .pay-wrap .amount-container span:nth-child(2) {
    color: red;
    font-size: 18px;
  }

  .pay-wrap .pay-btn {
    width: 90%;
    margin: 0 auto; /* 居中按钮 */
  }
}
</style>