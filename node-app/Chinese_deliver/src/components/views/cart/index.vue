<script setup lang="ts">
import Footer from '@/components/Footer.vue'
import Empty from '@/components/Empty.vue'
import Header from "@/components/Header.vue";
import CartDetails from "@/components/views/cart/components/CartDetails.vue";
import {onMounted, ref} from "vue";
import {useStore} from "vuex";
const isShow = ref(true)
const store = useStore()
const init = () => {
  // if(store.state.cartList.length === 0) {
  if(1===1) {
    isShow.value = false
  }
};
onMounted(() => {
  init()
})
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
  .content {
    flex: 1;
    overflow-y: auto;
  }
}
</style>