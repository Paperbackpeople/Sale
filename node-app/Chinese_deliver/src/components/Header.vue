<!-- <script>
import { useRouter } from "vue-router";
export default {
  props: ["title"],
setup(){
  const router = useRouter();
  const toBack = () => {
    router.back();
  };
  return {
    toBack,
  };
},
};
</script>

<template>
<div  class="header">
  <van-icon name="arrow-left" class="icon" @click="toBack"/>
  <div>{{title}}</div>
</div>
</template>

<style>
.header {
  background-color: #fff;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  font-weight: 600;
  border-bottom: 1px solid #d7d7d7;
  .edit {
    font-size: 16px;
    position: absolute;
    right: 15px;
    font-weight: normal;
  }
  .icon {
    position: absolute;
    left: 10px;
  }
}
</style> -->

<script>
import { useRouter } from "vue-router";

export default {
  props: {
    title: String,
    disableBack: { // 新增 prop，控制是否禁用返回功能
      type: Boolean,
      default: false,
    },
  },
  setup(props) {
    const router = useRouter();
    const toBack = () => {
      if (!props.disableBack) {
        router.back();
      }
    };
    return {
      toBack,
    };
  },
};
</script>

<template>
  <div class="header">
    <!-- 判断是否显示返回按钮 -->
    <van-icon
      v-if="!disableBack"
      name="arrow-left"
      class="icon"
      @click="toBack"
    />
    <div>{{ title }}</div>
  </div>
</template>

<style scoped>
.header {
  background-color: #fff;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  font-weight: 600;
  border-bottom: 1px solid #d7d7d7;
}

.icon {
  position: absolute;
  left: 10px;
}
</style>