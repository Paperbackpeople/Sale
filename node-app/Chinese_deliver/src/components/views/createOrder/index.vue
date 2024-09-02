<script lang="ts">
import {defineComponent, computed, ref} from 'vue';
import { useStore } from 'vuex';
import Header from '@/components/Header.vue';
import axios from 'axios';
export default defineComponent({
  components: { Header },
  setup() {
    const store = useStore();
    const isSubmitting = ref(false);  // 定义按钮状态

    // 获取选中商品和总金额
    const checkedList = computed(() => store.state.checkedList);
    const items = computed(() => checkedList.value.items);
    const totalAmount = computed(() => checkedList.value.totalPrice / 100);

    const onEdit = () => {
      // 处理编辑联系人信息逻辑
    };
    // 在创建订单时的使用示例
    const onPay = async () => {
      let tokenInfo = store.state.tokenInfo;

      // 检查 token 是否已被使用
      if (!tokenInfo.token || tokenInfo.tokenUsed) {
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

      try {
        const response = await axios.post('/api/createOrder', {
          userId: store.state.user,
          totalAmount: totalAmount.value,
          orderItems: items.value,
          location: 'Your delivery address here',
          status: 'pending',
          token: tokenInfo.token,  // 发送请求时附带 Token
        }, {
          headers: {
            'Content-Type': 'application/json',
          }});

        if (response.status === 201) {
          console.log('Order created successfully:', response.data);
          store.commit('markTokenAsUsed');  // 标记 Token 已使用
        } else {
          isSubmitting.value = false;
          console.error('Order creation failed');
        }
      } catch (error) {
        console.error('An error occurred while creating the order:', error);
      }
    };

    return {
      contact: {
        tel: '', // 获取并设置联系人电话
        name: '' // 获取并设置联系人姓名
      },
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
    <van-contact-card type="edit" :tel="contact.tel" :name="contact.name" @click="onEdit" />
    <div class="content">
      <div v-for="(item, index) in items" :key="index">
        <van-card
            :num="item.quantity"
            :price="item.price"
            :title="item.name"
            :thumb="item.images[0]"
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