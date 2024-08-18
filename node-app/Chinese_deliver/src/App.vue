<template>
  <div id="app" @mousemove="resetTimeout" @keydown="resetTimeout">
    <RouterView />
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router'
import { computed, onMounted } from 'vue';
import { useStore } from 'vuex';

const store = useStore();

const userEmail = computed(() => store.state.email);

const resetTimeout = () => {
  store.dispatch('resetSessionTimeout');
};

onMounted(() => {
  console.log('User Email:', userEmail.value);
  store.dispatch('resetSessionTimeout');
});
</script>

<style scoped>
/* Your styles here */
</style>