<script setup lang="ts">
import Footer from '@/components/Footer.vue'
</script>

<template>
  <div class="order">
    <div class = "content"> </div>
    <Footer />
  </div>
</template>

<style lang="less" scoped>
.order {
  display: flex;
  font-size: 12px;
  flex-flow: column;
  height: 100%;
  .content {
    flex: 1;
    overflow-y: auto;
  }
}
</style>