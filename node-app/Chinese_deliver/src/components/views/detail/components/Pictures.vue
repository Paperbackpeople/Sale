<template>
  <div class="pictures-content">
    <img
        v-for="(image, index) in images"
        :key="index"
        :src="image"
        alt=""
        class="picture-item"
    />
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";

const store = useStore();
const images = computed(() => store.state.currentItem.images);
</script>

<style scoped>
.pictures-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 20px;
  height: 100%;
}

.picture-item {
  width: 100%;
  max-width: 300px;
  border-radius: 10px;
  object-fit: cover;
}
</style>