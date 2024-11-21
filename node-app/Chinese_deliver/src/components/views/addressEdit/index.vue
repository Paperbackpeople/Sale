<script setup lang="ts">
import Header  from "@/components/Header.vue";
import { ref } from 'vue';
import { areaList } from '@vant/area-data';
import { Locale } from 'vant';
// 引入英文语言包
import enUS from 'vant/es/locale/lang/en-US';

Locale.use('en-US', enUS);

const searchResult = ref([]);
</script>

<template>
  <Header title="Address Edit" />
  <van-address-edit
      :area-list="areaList"
      show-delete
      show-set-default
      show-search-result
      :search-result="searchResult"
      :area-columns-placeholder="['Choose', 'Choose', 'Choose']"
      @save="onSave"
      @delete="onDelete"
  />

</template>

<style scoped>

</style>