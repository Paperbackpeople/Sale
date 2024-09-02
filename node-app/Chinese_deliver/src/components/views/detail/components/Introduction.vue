<template>
  <div class="introduction-content">
    <p>{{ description }}</p>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";

const store = useStore();
const description = computed(() => store.state.currentItem.fullDescription);
</script>

<style scoped>
.introduction-content {
  padding: 20px;
  font-family: 'Georgia', serif; /* 使用更具文艺感的字体 */
  font-size: 18px; /* 增加字体大小 */
  line-height: 1.8; /* 增加行高 */
  letter-spacing: 0.5px; /* 增加字间距 */
  color: #333; /* 深色文字，提高可读性 */
  text-align: justify; /* 文本对齐方式，提升视觉效果 */
  height: auto; /* 高度根据内容自适应 */
  max-width: 800px; /* 限制最大宽度，提高阅读体验 */
  margin: 0 auto; /* 使内容居中 */
}
</style>