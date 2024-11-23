<template>
  <div class="order-detail">
    <Header title="Order Detail" />
    <div class="content">
      <div v-for="(item, index) in order.orderItems" :key="index">
        <van-card
            :num="item.quantity"
            :price="item.price"
            :title="item.product.name"
            :thumb="item.product.images[0] || '/images/default-image.jpg'"
        />
      </div>
    </div>
    <div class="pay-wrap">
      <div class="amount-container">
        <span>Total:</span>
        <span>${{ order.totalAmount }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import Header from '@/components/Header.vue';
import axios from 'axios';

const route = useRoute();
const orderId = route.query.orderId;
const order = ref({
  contactName: '',
  contactTel: '',
  location: '',
  orderItems: [],
  totalAmount: 0,
});

const fetchOrderDetail = async () => {
  try {
    const response = await axios.get(`/api/order/${orderId}`, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    order.value = response.data;
  } catch (error) {
    console.error('Failed to fetch order detail:', error);
  }
};

onMounted(() => {
  fetchOrderDetail();
});
</script>

<style scoped>
.order-detail {
  display: flex;
  flex-flow: column;
  height: 100%;
}
.content {
  flex: 1;
  overflow-y: auto;
  padding: 10px 0;
  font-size: 18px;
}
.content > div {
  margin: 10px 0;
}
.van-card {
  font-size: 16px;
}
.pay-wrap {
  padding: 10px 0;
  width: 100%;
  background-color: #fff;
  border-top: 1px solid #e9e9e9;
}
.amount-container {
  display: flex;
  justify-content: space-between;
  padding: 0 5%;
  font-size: 14px;
  margin-bottom: 10px;
}
.amount-container span:nth-child(2) {
  color: red;
  font-size: 18px;
}
</style>