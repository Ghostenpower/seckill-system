<template>
  <el-row justify="center">
    <el-col :span="14">
      <el-descriptions direction="vertical" border style="margin-top: 20px">
        <el-descriptions-item :rowspan="3" :width="140" label="图片" align="center">
          <el-image style="width: 100px; height: 100px"
            src="https://tse1-mm.cn.bing.net/th/id/OIP-C.FHyccU3YbWl1-IqWQDExdwHaEK?rs=1&pid=ImgDetMain" />
        </el-descriptions-item>
        <el-descriptions-item label="商品">{{ product.name }}</el-descriptions-item>
        <el-descriptions-item label="单价">{{ product.price }}</el-descriptions-item>
        <el-descriptions-item label="库存">{{ product.stock }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ product.status }}</el-descriptions-item>
        <el-descriptions-item label="描述">{{ product.description }}</el-descriptions-item>
      </el-descriptions>
    </el-col>
  </el-row>
  <el-row style="margin-top: 10px;" justify="center">
    <el-col align="middle">
      <el-input-number v-model="num" :min="1" :max="product.stock+1" label="数量"></el-input-number>
    </el-col>
  </el-row>
  <el-row style="margin-top: 10px;" justify="center">
    <el-col align="middle">
      <el-button type="primary" @click="buy">立即购买</el-button>
      <el-button type="primary" @click="AddToCart">加入购物车</el-button>
      <el-button v-if="!product.isCollect" type="primary" @click="AddCollect">收藏</el-button>
      <el-button v-if="product.isCollect" type="danger" @click="DeleteCollect">取消收藏</el-button>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { getListData, getByName, buyProduct } from '@/axios/product';
import { addToCart } from '@/axios/cart';
import { addCollect, deleteCollect } from '@/axios/product';
import { msgSuccess, msgWarning, msgError } from '@/utils/message';

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

const product = reactive({
  product_id: '',
  name: '',
  price: '',
  description: '',
  stock: '',
  status: '',
})

// #region 购买属性和方法
// 购买数量
const num = ref(1);
const buy = async () => {
  try {
    const response = await buyProduct({
      product_id: product.product_id,
      num: num.value
    });
    if (response.code === 1) {
      msgSuccess('购买成功');
    } else {
      msgWarning(response.msg);
    }
  } catch {
    msgError('购买失败');
  }
}
// #endregion

const AddCollect = async () => {
  try {
    const response = await addCollect({ product_id: product.product_id })
    if (response.code === 1) {
      msgSuccess('收藏成功');
    } else {
      msgWarning(response.msg);
    }
  } catch {
    msgError('收藏失败');
  }
}

const DeleteCollect = async () => {
  try {
    const response = await deleteCollect({ product_id: product.product_id })
    if (response.code === 1) {
      msgSuccess('取消收藏成功');
    } else {
      msgWarning(response.msg);
    }
  } catch {
    msgError('取消收藏失败');
  }
}

// #region 加入购物车
const AddToCart = async () => {
    try {
        const response = await addToCart([{ product_id: product.product_id, product_num: num.value }])
        if (response.code === 1) {
            msgSuccess('加入购物车成功');
            //刷新
            router.push({ path: '/cart/list' });
        }
    } catch {
        msgError('加入购物车失败');
    } finally {

    }
}
// #endregion

onMounted(() => {
  product.product_id = route.query.product_id
  product.name = route.query.name
  product.price = route.query.price
  product.description = route.query.description
  product.stock = route.query.stock
  product.status = route.query.status
});
</script>

<style scoped>
/* 添加组件样式 */
</style>