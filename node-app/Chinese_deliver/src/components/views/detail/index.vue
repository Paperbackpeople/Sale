<script setup lang="ts">
import Header from "@/components/Header.vue";
import Cart from "@/components/views/cart/index.vue";
import Introduction from "@/components/views/detail/components/Introduction.vue";
import Pictures from "@/components/views/detail/components/Pictures.vue";
import Comments from "@/components/views/detail/components/Comments.vue";
import {useRouter} from "vue-router";
import {useStore} from "vuex";
import {reactive, toRefs, computed, ref} from "vue";
const store = useStore();
const currentItem = computed(() => store.state.currentItem);
const img = computed(() => currentItem.value.images.length > 0 ? currentItem.value.images[0] : 'path/to/default-image.jpg');
const img_background = computed(() => currentItem.value.images.length > 1 ? currentItem.value.images[1] : currentItem.value.images[0]);
const title = computed(() => currentItem.value.name);
const router = useRouter();
const quantity = ref(0); // 使用 ref 创建响应式数据

// 更新 quantity 的初始值
const itemInCart = store.state.cartList.find((item: { productId: number; quantity: number }) => item.productId === currentItem.value.productId);
quantity.value = itemInCart ? itemInCart.quantity : 0;
const cartItemCount = computed(() => {
  // 计算购物车中的商品数量
  return store.state.cartList.reduce((total: number, item: { quantity: number }) => total + item.quantity, 0);
});
const addToCart = () => {
  store.commit('addItemToCart', {
    ...currentItem.value,
    quantity: quantity.value,
  });
};
const toCart = () => {
  router.push("/cart");
};
</script>

<template>
  <div class="storeDetails">
    <Header title="Food details" />
    <div class="content">
      <div class="img" :style="{ backgroundImage: `url(${img_background})` }"></div>
      <div class="foodSort">
        <div class="name">
          {{title}}
          <img class="store-img" :src="img" alt="" />
        </div>
        <van-tabs color="#ffc400">
          <!-- 引用 Introduction 组件 -->
          <van-tab title="Introduction">
            <Introduction />
          </van-tab>

          <!-- 引用 Pictures 组件 -->
          <van-tab title="Pictures">
            <Pictures />
          </van-tab>

          <!-- 引用 Comments 组件 -->
          <van-tab title="Comments">
            <Comments />
          </van-tab>
        </van-tabs>
      </div>
    </div>
    <van-action-bar>
      <van-action-bar-icon icon="chat-o" text="Cart" :badge="cartItemCount" @click="toCart"/>
      <van-stepper v-model="quantity" min="0"/>
      <van-action-bar-button type="warning" text="Add to cart" @click="addToCart" />
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
      height: 150px;
      background-size: cover;
      background-position: center;
      background-repeat: no-repeat;
    }

    .foodSort {
      height: 100%;
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
        font-size: 23px;
        font-weight: 600;


        .store-img {
          width: 80px;
          height: 90px;
          border-radius: 10px;
          margin-top: -30px;
        }
      }
      van-tabs {
        flex: 1;
        display: flex;
        flex-direction: column;
      }
      van-tab {
        flex: 1;
        overflow-y: auto;
      }
    }
  }
}
</style>