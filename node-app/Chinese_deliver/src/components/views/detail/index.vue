<script setup lang="ts">
import Header from "@/components/Header.vue";
import Cart from "@/components/Cart.vue";
import {useRouter} from "vue-router";
import { reactive, toRefs } from "vue";
let data = reactive({
  title: "Food details",
  img: "https://img.yzcdn.cn/vant/apple-1.jpg",
  storeData:[
    {

    }
  ]
});
const { title, img } = toRefs(data);
const router = useRouter();
const toCart = () => {
  router.push("/cart");
};
</script>

<template>
  <div class="storeDetails">
    <Header title="Food details" />
    <div class="content">
      <div class="img"></div>
      <div class="foodSort">
        <div class="name">
          {{title}}
          <img class="store-img" :src="img" alt="" />
        </div>
        <van-tabs color="#ffc400">
          <van-tab title="Introduction">Introduction</van-tab>
          <van-tab title="Pictures">Pictures</van-tab>
          <van-tab title="Comments">Comments</van-tab>
        </van-tabs>
      </div>
    </div>
    <van-action-bar>
      <van-action-bar-icon icon="chat-o" text="Cart" badge="5" @click="toCart"/>
      <van-stepper/>
      <van-action-bar-button type="warning" text="Add to cart"/>
      <van-action-bar-button type="danger" text="Buy now"/>
    </van-action-bar>
  </div>
</template>

<style scoped>
.storeDetails {
  height: 100%;
  display: flex;
  flex-flow: column;

  .content {
    flex: 1;
    overflow-y: auto;

    .img {
      background: url("https://img.yzcdn.cn/vant/apple-1.jpg") no-repeat center/cover;
      height: 150px;
    }

    .foodSort {
      height: 500px;
      background-color: #fff;
      margin-top: -30px;
      border-radius: 20px 20px 0 0;

      .sort {
        margin-top: 10px;
      }

      .name {
        display: flex;
        padding: 20px;
        justify-content: space-between;

        .store-img {
          width: 80px;
          height: 90px;
          border-radius: 10px;
          margin-top: -30px;
        }
      }
    }
  }
}
</style>