<script setup lang="ts">

</script>

<template>
  <div class="empty">
  <svg class="icon" aria-hidden="true">
  <use xlink:href="#icon-inbox-full"></use>
  </svg>
    No Food in Cart
  </div>
</template>

<style scoped lang="less">
.empty {
  flex: 1;
  margin :20px;
  font-size: 16px;
  overflow-y: auto;
  display: flex;
  flex-flow: column;
  justify-content: center;
  align-items: center;
  background-color: #fff;
  border-radius: 10px;
  height: 72vh;
  .icon {
    margin-bottom: 10px;
  }
}

</style>