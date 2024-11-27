<template>
  <div class="order-list">
    <Header title="My Orders" :disableBack="true" />
    <div class="content">
      <div
          v-for="order in orders"
          :key="order.orderId"
          class="order-card"
      >
        <div class="order-header">
          <span class="order-id">Order ID: {{ order.orderId }}</span>
          <span
              class="order-status"
              :class="'status-' + order.status.toLowerCase()"
          >
            {{ order.status }}
          </span>
        </div>
        <div class="order-body">
          <div class="order-amount">Total: ${{ order.totalAmount }}</div>
          <div class="order-date">Date: {{ order.createdAt }}</div>
        </div>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import axios from 'axios';
import store from '@/stores';

const router = useRouter();
const orders = ref([]);

const fetchOrders = async () => {
  try {
    const response = await axios.get('/api/orders', {
      params: { email: store.state.user },
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    orders.value = response.data;
  } catch (error) {
    console.error('Failed to fetch orders:', error);
  }
};

const goToOrderDetail = (orderId) => {
  router.push({ path: '/order-detail', query: { orderId } });
};

onMounted(() => {
  fetchOrders();
});
</script>

<style scoped>
/* 1. 设置最外层容器为 Flex 布局，高度为 100vh */
.order-list {
  display: flex;
  flex-direction: column;
  height: 100%;
  background-color: #f9f9f9;
  font-size: 12px;
  box-sizing: border-box;
}

/* 2. Header 样式调整（确保高度固定） */
header {
  flex-shrink: 0;
  height: 60px; /* 假设 Header 高度为 60px，根据实际情况调整 */
}

/* 3. 内容区域 */
.content {
  flex: 1; /* 填充剩余空间 */
  overflow-y: auto; /* 内容超出时滚动 */
  padding: 20px;
}

/* 调整内容区域高度，减去 Header 和 Footer 的高度 */
.content {
  height: calc(100vh - 60px - 50px); /* 100vh - Header高度 - Footer高度 */
}

/* 4. 订单卡片样式（保持不变） */
.order-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 15px;
  padding: 15px;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  cursor: pointer;
}

.order-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

/* 5. 订单头部布局（保持不变） */
.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

/* 6. 订单ID样式（保持不变） */
.order-id {
  font-weight: bold;
  font-size: 12px;
}

/* 7. 订单状态样式（保持不变） */
.order-status {
  padding: 2px 6px;
  border-radius: 8px;
  font-size: 10px;
  font-weight: bold;
  text-transform: capitalize;
}

/* 状态颜色（保持不变） */
.status-pending {
  background-color: #ffe5b4;
  color: #996600;
}

.status-completed {
  background-color: #d4edda;
  color: #155724;
}

.status-cancelled {
  background-color: #f8d7da;
  color: #721c24;
}

/* 8. 订单主体布局（保持不变） */
.order-body {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 9. 订单金额样式（保持不变） */
.order-amount {
  font-size: 12px;
  color: #333;
}

/* 10. 订单日期样式（保持不变） */
.order-date {
  font-size: 12px;
  color: #888;
}

/* 11. Footer 固定在底部 */
footer {
  position: fixed;
}
</style>