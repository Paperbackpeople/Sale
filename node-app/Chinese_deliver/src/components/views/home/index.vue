<script setup lang="ts">
import {ref, reactive, onMounted} from 'vue'
import axios from 'axios';
import Footer from '@/components/Footer.vue'
import NavList from "@/components/views/home/components/NavList.vue";
import {useStore} from 'vuex'
const location = ref('Location');
const active = ref(0);
const store = useStore();
const fetchLocation = async () => {
  try {
    const response = await axios.get('/api/location')
    if (response.data && response.data.location) {
      location.value = response.data.location;
    } else {
      console.error('Location data not found');
    }
  } catch (error) {
    console.error('Failed to fetch location:', error);
  }
};
onMounted(() => {
  fetchLocation();
  store.dispatch('fetchProducts');
  store.dispatch('fetchMovies');
});
const data = reactive({
  big_sort: [
    {
      icon: 'icon-hanbaobao',
      name: 'Food'
    },
    {
      icon: 'icon-tiandianmianbaodangaoshuiguotiandian',
      name: 'Dessert'
    },
    {
      icon: 'icon-icon-test',
      name: 'Fruit'
    },
    {
      icon: 'icon-yiyaoxiang',
      name: 'Medicine'
    }
  ],
  small_sort: [
    {
      icon: 'icon-pijiu',
      name: 'Wine'
    },
    {
      icon: 'icon-zhenzhunaicha',
      name: 'Milk Tea'
    },
    {
      icon: 'icon-wucan',
      name: 'Lunch'
    },
    {
      icon: 'icon-paomian',
      name: 'Snack'
    },
    {
      icon: 'icon-pisa',
      name: 'Pizza'
    },
  ],
})
</script>

<template>
  <div class="home">
    <div class="content">
      <div class="header">
        <div class="text">
          Chinese Cuisine
        </div>
        <div class="location" @click="fetchLocation"> <!-- 点击触发请求 -->
          <van-icon name="location-o" size="20" />
          <span>{{ location }}</span> <!-- 动态显示位置 -->
          <van-icon name="arrow" size="20" />
        </div>
      </div>

      <div class="main">
        <div class="main-bg">
          <div class="search">
            <input type="text" placeholder="Search for anything" />
            <div class="search-text">Search</div>
          </div>
          <div class="sort">
            <div class="big-sort">
              <div v-for="(item, index) in data.big_sort" :key="index">
                <svg class="icon" aria-hidden="true">
                  <!-- 使用 Vue 的动态绑定语法 -->
                  <use :xlink:href="`#${item.icon}`"></use>
                </svg>
                {{ item.name }}
              </div>
            </div>
            <div class="small-sort">
              <div v-for="(item, index) in data.small_sort" :key="index">
                <svg class="icon" aria-hidden="true">
                  <use :xlink:href="`#${item.icon}`"></use>
                </svg>
                {{ item.name }}
              </div>
            </div>
          </div>
        </div>
        <van-tabs v-model:active="active">
          <!-- Popular 标签 -->
          <van-tab title="Popular">
            <nav-list :navList="store.getters.getContentNavList.popular" type="popular" />
          </van-tab>

          <!-- Recommended 标签 -->
          <van-tab title="Recommended">
            <nav-list :navList="store.getters.getContentNavList.recommend" type="recommend" />
          </van-tab>

          <!-- Movies 标签 -->
          <van-tab title="Movies">
            <nav-list :navList="store.getters.getContentNavList.movies" type="movie" />
          </van-tab>
        </van-tabs>
      </div>
    </div>
    <Footer />
  </div>
</template>
<style lang="less" scoped>
.home {
  display: flex;
  flex-flow: column;
  height: 100%;
  font-size: 12px;
  .content {
    flex: 1;
    overflow-y: auto;
    .header{
      background-image: linear-gradient(#ffc400,#fff);
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 20px 20px 40px 20px;
      .text{
        font-size: 20px;
        font-weight: 600;
      }
      .location{
        font-size: 14px;
        span{
          margin: 0 5px;
        }
      }
    }
    .main{
      margin-top: -30px;
      .main-bg{
        background-image: linear-gradient(#fff,#f5f5f5);
        padding: 10px 20px 0px 20px;
        border-radius: 30px 30px 0 0;
        .search{
          position: relative;
          input{
            width: 100%;
            height: 30px;
            border-radius: 20px;
            border: 1px solid #ffc400;
          }
          .search-text{
            position: absolute;
            right: -6px;
            top: 1px;
            background-color: #ffc400;
            border-radius: 16px;
            width: 50px;
            height: 32px;
            line-height: 32px;
            font-size: 14px;
            text-align: center;
          }
        }
        .sort{
          padding: 20px 0;
          .big-sort{
            display: flex;
            div{
              flex: 1;
              display: flex;
              justify-content: center;
              flex-flow: column;
              align-items: center;
              .icon{
                width: 50px !important;
                height: 50px !important;
                margin-bottom: 5px;
                transform: scale(1);
              }
            }
          }
          .small-sort{
            display: flex;
            flex-wrap: wrap;
            margin-top: 20px;
            div {
              display: flex;
              justify-content: center;
              flex-flow: column;
              align-items: center;
              width: 20%;
              .icon{
                width: 30px !important;
                height: 30px !important;
                margin : 10px;
              }
            }
          }
        }

      }
    }
  }
}

</style>