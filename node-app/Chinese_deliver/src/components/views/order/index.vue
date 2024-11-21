<template>
  <div class="order">
    <div class="content">
      <div v-for="order in orders" :key="order.orderId" class="order-item" @click="viewOrderDetail(order)">
        <div class="order-info">
          <span class="order-time">{{ order.createdAt }}</span>
          <span class="order-amount">Total: ${{ order.totalAmount }}</span>
        </div>
        <div class="order-image">
          <img :src="getFirstItemImage(order)" alt="First Item" />
        </div>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import store from "@/stores";
import { useRouter } from 'vue-router';

const orders = ref([]);
const router = useRouter();

const fetchOrders = async () => {
  try {
    const response = await axios.get('/api/orders', {
      params: { email: store.state.user },
      headers: {
        'Content-Type': 'application/json',
      },
    });
    orders.value = response.data;
  } catch (error) {
    console.error('Failed to fetch orders:', error);
  }
};

const getFirstItemImage = (order) => {
  if (order.orderItems && order.orderItems.length > 0) {
    return order.orderItems[0].product.images[0]; // 获取第一个商品的图片
  }
  return 'default-image.jpg'; // 如果没有商品，显示默认图片
};

const viewOrderDetail = (order) => {
  router.push({ path: '/order-detail', query: { orderId: order.orderId } });
};

onMounted(() => {
  fetchOrders();
});
</script>
<style scoped>
.order-item {
  display: flex;
  justify-content: space-between;
  padding: 10px;
  border-bottom: 1px solid #eaeaea;
}
.order-info {
  flex: 1;
}
.order-image img {
  width: 60px;
  height: 60px;
}
</style>