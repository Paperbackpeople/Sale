<script>
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
export default {
  props: ['itemContent'],
  name: 'NavListItem',
  setup(props) {  // Accept 'props' as a parameter
    const router = useRouter();
    const store = useStore();

    const goDetail = () => {
      store.dispatch('setCurrentItem', props.itemContent);
      router.push('/detail');
    };

    const getFirstImage = () => {
      // Use 'props.itemContent' to access 'itemContent'
      return props.itemContent.images.length > 0
          ? props.itemContent.images[0]
          : 'path/to/default-image.jpg';
    };

    return {
      goDetail,
      getFirstImage
    };
  }
};
</script>

<template>
<div class="item" @click="goDetail">
  <img :src="getFirstImage()" alt=""/>
  <div class="item-right">
    <div class="name">{{itemContent.name}}</div>
    <div class="price">Price: ${{itemContent.price}}</div>
    <div class="sales">Sales: {{itemContent.saleVolume}}</div>
    <div class="rating">Rating: {{itemContent.rating}}</div>
    <div class="short-discription">
      <div>
        {{itemContent.shortDescription}}
      </div>
    </div>
  </div>
</div>
</template>
<style scoped>
.item {
  display: flex;
  padding: 10px;
  border-radius: 10px;
  background-color: #fff;
  align-items: center;
  img {
    width: 100px;
    height: 100px;
    border-radius: 10px;
    border: 5px solid #ccc; /* 外边框效果 */
    object-fit: cover; /* 确保图片不失真 */
  }
  .item-right {
    display: flex;
    flex-flow: column;
    justify-content: space-between;
    border-left: 2px solid #f5f5f5;
    .name {
      font-size: 18px;
      font-weight: 600;
    }
    .price {
      font-size: 14px;
      color: #f40;
    }
    .sales {
      font-size: 14px;
      color: #666;
    }
    .rating {
      font-size: 14px;
      color: #666;
    }
    .short-discription {
      display: flex;
      div{
        background-color: #ffc400;
        padding: 2px 5px;
        color: #666;
        border-radius: 5px;
      }

    }
  }
}
</style>