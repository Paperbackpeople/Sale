<script lang="ts">
import { defineComponent, computed, ref } from 'vue';
import { useStore } from 'vuex';
import ListItem from '@/components/ListItem.vue';
import { CartItem } from '@/components/ListItem.vue';
import router from "@/router";

export default defineComponent({
  components: { ListItem },
  setup() {
    const store = useStore();
    const checked = ref<number[]>([]);  // 用户选择的商品 ID 列表

    const filteredCartList = computed(() =>
        store.state.cartList.filter((item: CartItem) => item.quantity > 0)
    );
    // 初始化默认选中项
    const initializeChecked = () => {
      checked.value = filteredCartList.value.map((item: CartItem)=> item.productId); // 默认选中所有
    };

    initializeChecked();
    const totalTip = computed(() => {
      const selectedItems = store.state.cartList.filter((item: CartItem) =>
          checked.value.includes(item.productId)
      );

      const itemCount = selectedItems.length;
      const totalQuantity = selectedItems.reduce(
          (sum: number, item: CartItem) => sum + item.quantity,
          0
      );

      return `Total ${itemCount} items (${totalQuantity} pcs)`;
    });
    const handleAdd = (productId: number) => {
      const item = store.state.cartList.find((item: { productId: number }) => item.productId === productId);
      if (item && item.quantity > 0) {
        store.commit('addItemToCart', item);
      }
    };

    const totalPrice = computed(() =>
        store.state.cartList
            .filter((item: CartItem) => checked.value.includes(item.productId))  // Only include checked items
            .reduce((total: number, item: CartItem) =>
                total + item.price * item.quantity * 100, 0
            )
    );

    const onSubmit = () => {
      // 选中商品的信息
      const selectedItems = store.state.cartList.filter((item: CartItem) =>
          checked.value.includes(item.productId)
      );
      console.log('selectedItems:', selectedItems);
      // 将选中商品存入 Vuex
      store.dispatch('updateCheckedList', {
        items: selectedItems,
        totalPrice: totalPrice.value,
      });

      // 跳转到订单创建页面
      router.push('/createOrder');
    };

    return {
      store,
      checked,
      handleAdd,
      filteredCartList,
      totalPrice,
      onSubmit,
      totalTip,
    };
  },
});
</script>

<template>
  <div class="cartDetail">
    <div class="content">
      <van-checkbox-group v-model="checked">
        <div v-for="item in filteredCartList" :key="item.productId">
          <ListItem :item="item"
                    :handleAdd="handleAdd"
                    :showCheckbox="true" />
        </div>
      </van-checkbox-group>
    </div>
    <van-submit-bar v-if="filteredCartList.length > 0" class="submit-all"
                    label="Total:"
                    :price="totalPrice"
                    :button-text="'Buy now'"
                    :tip="totalTip"
                    :tip-icon="'shopping-cart-o'"
                    :disabled="filteredCartList.length === 0"
                    @submit="onSubmit"
    />
  </div>
</template>

<style scoped>
.cartDetail {
  display: flex;
  flex: 1;
  position: relative;
  overflow-y: auto;
  padding: 20px 20px 55px;
  font-size: 14px;
  .submit-all {
    position: fixed;
    bottom: 48px;
    border-top: 1px solid #ebedf0;
    border-radius: 10px;
    background-color: #fff;
  }
  .content {
    padding: 10px;
    background-color: #fff;
    border-radius: 10px;
    width: 100%;
  }
}
</style>