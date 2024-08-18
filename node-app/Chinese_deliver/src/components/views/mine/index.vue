<script setup lang="ts">
import Footer from '@/components/Footer.vue'
</script>

<template>
  <div class="mine">
    <div class = "content"> </div>
    <Footer />
  </div>
</template>

<style lang="less" scoped>
.mine {
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