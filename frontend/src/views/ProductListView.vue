<template>
  <el-main>
    <el-row justify="center" style="width: 100%;">
      <el-col :span="8" align="middle">
        <el-input v-model="searchName" style="max-width: 600px" placeholder="Please input" class="input-with-select">
          <template #append>
            <el-button type="primary" :icon="Search" @click="getListByName()" />
          </template>
        </el-input>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <el-row justify="center" style="width: 100%;">
      <el-col style="display: flex; justify-content: right;">
        <el-pagination background  layout="prev, pager, next" :current-page="page.page_num" @current-change="refreshList" :page-count="page_count" />
      </el-col>
      <el-col style="margin: 10px 0 10px 0;" align="middle">
        <el-space style="display: flex; justify-content: center;" wrap>
          <ProductCard v-if="productList.length!==0" v-for="(product, index) in productList" :product="product" />
        </el-space>
      </el-col>
    </el-row>
    <el-row>
      <el-col style="display: flex; justify-content: right;">
        <el-pagination background  layout="prev, pager, next" :current-page="page.page_num" @current-change="refreshList" :page-count="page_count" />
      </el-col>
    </el-row>
  </el-main>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import ProductCard from '@/components/ProductCard.vue';
import { Search } from '@element-plus/icons-vue'
import { getByName } from '@/axios/product';

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

// #region 初始化商品数据
const productList = ref([
]);
// #endregion

// #region 搜索属性以及方法
const searchName = ref("");
const getListByName = async () => {
  try {
    const response = await getByName({ name: searchName.value, page_num: page.page_num, page_size: 20 });
    if (response.code === 1) {
      page.total = response.data.total;
      productList.value = response.data.items;
    }
  } catch {
    msgError('获取商品列表失败');
  }
}
// #endregion

// #region 分页
const page = reactive({
  page_num: 1,
  page_size: 20,
  total: 0
});
const page_count = computed(() => {
  return Math.ceil(page.total / page.page_size);
});
const refreshList= async (page_num) => {
  page.page_num = page_num;
  await getListByName();
}
// #endregion

onMounted(async () => {
  await getListByName();
});
</script>

<style scoped></style>