<template>
  <div class="content-item">
    <div class="left">
      <van-checkbox :name="item.productId" v-if="showCheckbox" />
      <img :src="item.images[0]" alt="" />
      <div class="text">
        <div class="title">{{ item.name }}</div>
        <van-stepper v-model="item.quantity"
                     min="0"
                     :name="item.name"
                     @change="handleChange"
        />
      </div>
    </div>
    <div class="price">{{ item.price }}</div>
  </div>
</template>


<script lang="ts">
import { defineComponent, PropType } from 'vue';

export interface CartItem {
  productId: number;
  name: string;
  price: number;
  images: string[];
  quantity: number;
  add: boolean;
}

export default defineComponent({
  props: {
    item: {
      type: Object as PropType<CartItem>,
      required: true,
    },
    showCheckbox: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    handleChange(value: number) {
      this.$emit('change', value);
    },
  },
});
</script>

<style scoped>
.content-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.left {
  display: flex;
  align-items: center;
  flex: 1;

  img {
    width: 60px;
    height: 60px;
    border-radius: 10px;
    margin-left: 10px;
    margin-right: 10px;
  }

  .text {
    flex: 1;
    position: relative;
    height: 100%;
    justify-content: space-between;
    flex-flow: column;
    display: flex;

    .title {
      font-size: 16px;
    }

    .van-icon {
      position: absolute;
      right: 4px;
      bottom: 4px;
      font-size: 20px;
      color: red;
    }
  }
}

.price {
  font-size: 16px;
  font-weight: 600;
  color: #ff6a24;
}
</style>