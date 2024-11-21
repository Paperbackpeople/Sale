<script>
import Header from "@/components/Header.vue";
import { reactive, toRefs } from "vue";
import router from "@/router";
import { Dialog } from 'vant';

export default {
  components: {
    Header
  },
  setup() {
    const data = reactive({
      list: [
        {
          id: 1,
          name: 'John Snow',
          tel: '13000000000',
          address: 'Stark Family, Winterfell Castle',
          isDefault: true,
        },
        {
          id: 2,
          name: 'Tony Stark',
          tel: '1310000000',
          address: 'Stark Industries, Malibu Point',
          isDefault: false,
        },
      ]
    });

    const onEdit = (item) => {
      // 编辑地址时，可以传递当前要编辑的地址数据
      router.push({path: '/addressEdit', query: {id: item.id}});
    };

    const onAdd = () => {
      // 跳转到添加地址页面
      router.push('/addressEdit');
    };

    const onDelete = (item) => {
      // 删除前弹出确认框
      Dialog.confirm({
        title: 'Confirm',
        message: `Are you sure you want to delete this address: ${item.name}?`,
      }).then(() => {
        // 用户点击确认后的逻辑
        const index = data.list.findIndex((address) => address.id === item.id);
        if (index !== -1) {
          data.list.splice(index, 1); // 删除对应的地址
        }
      }).catch(() => {
        // 用户点击取消后的逻辑
        console.log('Delete canceled');
      });
    };

    return {
      ...toRefs(data),
      onEdit,
      onAdd,
      onDelete,
    };
  }
}
</script>

<template>
  <Header title="Address"/>
  <van-address-list
      :list="list"
      default-tag-text="Default"
      @edit="onEdit"
      @add="onAdd"
      @delete="onDelete"
  />
</template>

<style lang="less" scoped>
:deep(.van-button--danger) {
  background-color: #ffc400;
  color: #ffc400;
}

:deep(.van-switch--on) {
  background-color: #ffc400;
}

:deep(.van-radio__icon) {
  display: none;
}
</style>