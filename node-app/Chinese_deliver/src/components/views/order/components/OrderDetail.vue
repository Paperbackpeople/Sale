<template>
  <div class="order-detail">
    <Header title="Order Detail" />
    <div class="content">
      <van-contact-card :tel="order.contactTel" :name="order.contactName" />
      <div class="address">{{ order.location }}</div>
      <div v-for="item in order.orderItems" :key="item.orderItemId">
        <van-card
            :num="item.quantity"
            :price="item.price"
            :title="item.product.name"
            :thumb="item.product.images[0]"
        />
      </div>
      <div class="total-amount">Total Amount: ${{ order.totalAmount }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const orderId = route.query.orderId;
const order = ref(null);

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