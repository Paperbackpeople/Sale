<template>
  <div class="order-list">
    <Header title="My Orders" />
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
/* 1. 设置最外层容器为 Flex 布局，垂直方向 */
.order-list {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 视口高度 */
  background-color: #f9f9f9;
  font-size: 12px; /* 全局字体大小 */
  box-sizing: border-box;
}

/* 2. 内容区域可伸缩，填充剩余空间 */
.content {
  padding: 20px; /* 添加内边距 */
  flex: 1;
  margin-bottom: 20px; /* 与 Footer 之间的间距 */
}

/* 3. 订单卡片样式 */
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

/* 4. 订单头部布局 */
.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

/* 5. 订单ID样式 */
.order-id {
  font-weight: bold;
  font-size: 12px; /* 调整为12px */
}

/* 6. 订单状态样式 */
.order-status {
  padding: 2px 6px;
  border-radius: 8px;
  font-size: 10px; /* 更小的字体 */
  font-weight: bold;
  text-transform: capitalize;
}

/* 状态颜色 */
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

/* 7. 订单主体布局 */
.order-body {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 8. 订单金额样式 */
.order-amount {
  font-size: 12px; /* 调整为12px */
  color: #333;
}

/* 9. 订单日期样式 */
.order-date {
  font-size: 12px; /* 调整为12px */
  color: #888;
}

/* 10. Footer 样式调整 */
footer {
  text-align: center;
  background-color: #fff;
  border-top: 1px solid #e9e9e9;
  padding: 0; /* 去除内边距 */
}
</style>