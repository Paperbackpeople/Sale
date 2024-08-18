<template>
  <div v-for="(item, index) in navList" :key="index">
    <nav-list-item :itemContent="item" />
  </div>
</template>

<script>
import NavListItem from "@/components/views/home/components/NavListItem.vue";

export default {
  name: 'NavList',
  components: {NavListItem},
  props: ["navList"]
};
</script>
```