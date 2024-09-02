<script setup lang="ts">
import Footer from '@/components/Footer.vue'
import Empty from '@/components/Empty.vue'
import Header from "@/components/Header.vue";
import CartDetails from "@/components/views/cart/components/CartDetails.vue";
import { onMounted, ref, watch } from "vue";
import { useStore } from "vuex";

const isShow = ref(true);
const store = useStore();

// Function to update `isShow` based on cart list length
const updateVisibility = () => {
  isShow.value = store.state.cartList.length > 0 && store.state.cartList.some((item: { quantity: number }) => item.quantity > 0);
};

// Initialize on mount
onMounted(() => {
  updateVisibility();
});

// Watch for changes in cartList and update `isShow`
watch(() => store.state.cartList.length, () => {
  updateVisibility();
});
</script>

<template>
  <div class="cart">
    <Header title="Cart" />
    <CartDetails v-if="isShow"/>
    <Empty v-else/>
    <Footer />
  </div>
</template>

<style lang="less" scoped>
.cart {
  display: flex;
  flex-flow: column;
  height: 100%;
  font-size: 12px;
}
</style>