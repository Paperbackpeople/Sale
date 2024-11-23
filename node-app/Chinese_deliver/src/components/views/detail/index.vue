<script setup lang="ts">
import Header from "@/components/Header.vue";
import Introduction from "@/components/views/detail/components/Introduction.vue";
import Pictures from "@/components/views/detail/components/Pictures.vue";
import Comments from "@/components/views/detail/components/Comments.vue";
import { useRouter, useRoute } from "vue-router";
import { useStore } from "vuex";
import { computed, ref } from "vue";
import axios from "axios";

const store = useStore();
const router = useRouter();
const route = useRoute();
const type = computed(() => route.query.type);

const currentItem = computed(() => store.state.currentItem);

const img = computed(() => {
  if (type.value === 'movie') {
    return currentItem.value.img || '/images/default.jpg';
  } else {
    return currentItem.value.images && currentItem.value.images.length > 0
        ? `/images/${currentItem.value.images[0]}`
        : '/images/default.jpg';
  }
});

const img_background = computed(() => {
  if (type.value === 'movie') {
    return currentItem.value.img || '/images/default.jpg';
  } else {
    return currentItem.value.images && currentItem.value.images.length > 1
        ? `/images/${currentItem.value.images[1]}`
        : '/images/default.jpg';
  }
});

const title = computed(() => {
  return currentItem.value.nm || currentItem.value.name || 'No Title';
});

const quantity = ref(0);

const itemInCart = store.state.cartList.find(
    (item: { productId: number; quantity: number }) =>
        item.productId === currentItem.value.productId
);
quantity.value = itemInCart ? itemInCart.quantity : 0;

const cartItemCount = computed(() => {
  return store.state.cartList.reduce(
      (total: number, item: { quantity: number }) => total + item.quantity,
      0
  );
});
const addToCart = async () => {
  const name = currentItem.value.nm || currentItem.value.name;
  const imageUrl = type.value === 'movie'
      ? currentItem.value.img || '/images/default.jpg' // 电影的图片路径，通常是完整的 URL
      : currentItem.value.images && currentItem.value.images.length > 0
          ? currentItem.value.images[0].startsWith('http://') || currentItem.value.images[0].startsWith('https://') || currentItem.value.images[0].startsWith('/images/')
              ? currentItem.value.images[0] // 如果已经是完整的 URL 或以 '/images/' 开头，则直接使用
              : '/images/' + currentItem.value.images[0] // 否则添加 '/images/' 前缀
          : '/images/default.jpg'; // 默认图片路径

  let productId = currentItem.value.productId;

  if (type.value === 'movie') {
    // 如果是电影，检查数据库中是否存在
    try {
      const response = await axios.post('/api/products/checkAndInsert', {
        name: name,
        imageUrl: imageUrl,
      });
      productId = response.data; // 获取返回的 productId
    } catch (error) {
      console.error('Failed to check or insert product:', error);
      return;
    }
  }

  store.commit('addItemToCart', {
    name: name,
    quantity: quantity.value,
    image: imageUrl,
    images: type.value === 'movie'
        ? [imageUrl]
        : currentItem.value.images.map(img =>
            img.startsWith('http://') || img.startsWith('https://') || img.startsWith('/images/') ? img : '/images/' + img
        ),
    price: currentItem.value.price || 40,
    productId: productId,
  });
};
const toCart = () => {
  router.push("/cart");
};
const buyNow = async () => {
  // 调用 addToCart 添加到购物车
  await addToCart();
  // 跳转到购物车页面
  toCart();
};
</script>

<template>
  <div class="storeDetails">
    <Header :title="type === 'movie' ? 'Movie Details' : 'Food Details'" />
    <div class="content">
      <div class="img" :style="{ backgroundImage: `url(${img_background})` }"></div>
      <div class="foodSort">
        <div class="name">
          {{ title }}
          <img class="store-img" :src="img" alt="" />
        </div>
        <!-- Conditionally render the tabs or movie details -->
        <div v-if="type !== 'movie'">
          <van-tabs color="#ffc400">
            <van-tab title="Introduction">
              <Introduction />
            </van-tab>
            <van-tab title="Pictures">
              <Pictures />
            </van-tab>
            <van-tab title="Comments">
              <Comments />
            </van-tab>
          </van-tabs>
        </div>
        <div v-else class="movie-details">
          <p><strong>Name :</strong> {{ currentItem.nm }}</p>
          <p><strong>Price:</strong> ¥40 </p>
          <p><strong>Stars:</strong> {{ currentItem.star }}</p>
          <p><strong>Score:</strong> {{ currentItem.sc }}</p>
          <p><strong>Wish:</strong> {{ currentItem.wish }}</p>
          <p><strong>Release Time:</strong> {{ currentItem.rt }}</p>
        </div>
      </div>
    </div>
    <van-action-bar>
      <van-action-bar-icon
          icon="chat-o"
          text="Cart"
          :badge="cartItemCount"
          @click="toCart"
      />
      <van-stepper v-model="quantity" min="0" />
      <van-action-bar-button type="warning" text="Add to cart" @click="addToCart" />
      <van-action-bar-button type="danger" text="Buy now" @click="buyNow" />
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

      .movie-details {
        padding: 20px;
        font-size: 16px;

        p {
          margin: 10px 0;
        }

        strong {
          font-weight: bold;
        }
      }
    }
  }
}
</style>