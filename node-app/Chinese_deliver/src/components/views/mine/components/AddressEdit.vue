<template>
  <div>
    <Header title="Address Edit" />
    <div>
      <van-address-edit
          :area-list="areaList"
          :address-info="addressInfo"
          :show-delete="isEdit"
          :tel-validator = "false"
          show-set-default
          @save="onSave"
          @delete="onDelete"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { Toast } from 'vant';
import { areaList as areaData } from '@vant/area-data';
import Header from '@/components/Header.vue';
import axios from 'axios';
import store from "@/stores";

// 定义地址信息的类型
interface AddressEditInfo {
  name: string;
  tel: string;
  province: string;
  city: string;
  county: string;
  addressDetail: string;
  isPrimary?: boolean; // 是否为默认地址
}

const route = useRoute();
const router = useRouter();

const isEdit = ref(false); // 标识是否为编辑模式
const addressInfo = ref<AddressEditInfo>({
  name: '',
  tel: '',
  province: '',
  city: '',
  county: '',
  addressDetail: '',
  isPrimary: false,
}); // 当前地址信息

const areaList = ref(areaData); // 地区列表

onMounted(async () => {
  const id = route.query.id; // 获取地址ID

  if (id) {
    isEdit.value = true;
    try {
      const response = await axios.get(`/api/address/${id}`, {
        params: { email: store.state.user },
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      addressInfo.value = {
        ...response.data,
        addressId: response.data.addressId, // 确保包含 addressId
        county: response.data.state,        // 如果需要
        addressDetail: response.data.addressLine,
        isDefault: response.data.isPrimary,
      }; // 设置地址信息
    } catch (error) {
      Toast.fail('Failed to load address');
    }
  }
});

// 保存地址逻辑
const onSave = async (data) => {
  Toast.loading({
    message: 'Saving...',
    duration: 0,
  });

  try {
    const cleanData = {
      addressId: addressInfo.value.addressId, // 确保使用正确的字段
      tel: data.tel,
      province: data.province,
      city: data.city,
      state: data.county,
      addressLine: data.addressDetail,
      isPrimary: data.isDefault || data.isPrimary,
    };

    const apiEndpoint = isEdit.value ? `/api/address/update` : `/api/address/save`;
    await axios.post(apiEndpoint, cleanData, {
      params: { email: store.state.user },
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });

    Toast.success('Address saved successfully');
    router.back();
  } catch (error) {
    Toast.fail('Failed to save address');
  } finally {
    Toast.clear();
  }
};
// 删除地址逻辑
const onDelete = async () => {
  if (isEdit.value) {
    Toast.loading({
      message: 'Deleting...',
      duration: 0, // 持续显示，需手动关闭
    });
    try {
      await axios.delete(`/api/address/delete`, {
        params: { email : store.state.user },
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      Toast.success('Address deleted successfully');
      router.back();
    } catch (error) {
      Toast.fail('Failed to delete address');
    } finally {
      Toast.clear(); // 清除加载提示
    }
  }
};
</script>

<style scoped>
:deep(.van-button--danger) {
  background-color: #ffc400;
  color: #ffffff;
}

:deep(.van-address-edit__fields) {
  margin-top: 20px;
}
</style>