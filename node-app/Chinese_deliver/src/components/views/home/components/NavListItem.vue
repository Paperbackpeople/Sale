<script>
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

export default {
  props: ['itemContent', 'type'],
  name: 'NavListItem',
  setup(props) {
    const router = useRouter();
    const store = useStore();

    const goDetail = () => {
      store.dispatch('setCurrentItem', props.itemContent);
      router.push({ path: "/detail", query: { type: props.type } });
    };

    const getFirstImage = () => {
      // 如果 type 是 movie，直接使用 itemContent.img 字段
      if (props.type === 'movie') {
        return props.itemContent.img || '/images/default-image.jpg'; // 如果 img 为空，则返回默认图片
      }

      // 如果不是 movie，检查 images 数组
      if (props.itemContent.images && props.itemContent.images.length > 0) {
        return `/images/${props.itemContent.images[0]}`; // 使用本地路径
      }

      return '/images/default-image.jpg'; // 默认图片路径
    };
    const getTitle = () => {
      return props.type === 'movie' ? props.itemContent.nm : props.itemContent.name;
    };

    const getSubtitle = () => {
      return props.type === 'movie'
          ? `Stars: ${props.itemContent.star || 'N/A'}`
          : `Sales: ${props.itemContent.saleVolume || 0}`;
    };

    const getAdditionalInfo = () => {
      return props.type === 'movie'
          ? props.itemContent.showInfo
          : `Price: $${props.itemContent.price || 0}`;
    };

    return {
      goDetail,
      getFirstImage,
      getTitle,
      getSubtitle,
      getAdditionalInfo,
    };
  },
};
</script>

<template>
  <div class="item" @click="goDetail">
    <img :src="getFirstImage()" alt="Image"/>
    <div class="item-right">
      <!-- 动态显示标题 -->
      <div class="name">{{ getTitle() }}</div>
      <!-- 动态显示副标题 -->
      <div class="subtitle">{{ getSubtitle() }}</div>
      <!-- 动态显示额外信息 -->
      <div class="additional-info">{{ getAdditionalInfo() }}</div>
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
    border: 5px solid #ccc;
    object-fit: cover;
  }

  .item-right {
    display: flex;
    flex-flow: column;
    justify-content: space-between;
    border-left: 2px solid #f5f5f5;
    padding-left: 10px;

    .name {
      font-size: 18px;
      font-weight: 600;
    }

    .subtitle {
      font-size: 14px;
      color: #666;
    }

    .additional-info {
      font-size: 14px;
      color: #888;
    }
  }
}
</style>