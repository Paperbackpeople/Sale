<script setup lang="ts">

</script>

<template>
  <div>
      <div class="footer">
        <div class="item">
          <router-link to="/home" class="nav-item" active-class="nav-color">
            <van-icon name="wap-home-o" size="30" />
            Home</router-link>
        </div>
    <div class="item">
      <router-link to="/cart" class="nav-item" active-class="nav-color">
        <van-icon name="shopping-cart-o" size="30" />
        Cart</router-link>
    </div>
    <div class="item">
      <router-link to="/order" class="nav-item" active-class="nav-color">
        <van-icon name="coupon-o" size="30" />
        Order</router-link>
    </div>
    <div class="item">
      <router-link to="/mine" class="nav-item" active-class="nav-color">
        <van-icon name="manager-o" size="30" />
        Mine</router-link>
    </div>
      </div>

  </div>

</template>

<style scoped>
.footer{
  display: flex;
  background-color: #fff;
  border-top: 1px solid #eee;
  .item{
    flex: 1;
    .nav-color{
      color: #ffc400;
    }
    .nav-item{
      display: flex;
      flex-flow: column;
      align-items: center;
    }
  }
}

</style>