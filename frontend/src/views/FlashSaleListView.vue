<template>
  <el-row justify="center" style="width: 100%;">
    <el-col :span="8" align="middle">
      <el-input v-model="searchName" style="max-width: 600px" placeholder="Please input" class="input-with-select">
        <template #append>
          <el-button type="primary" :icon="Search" @click="getListByName()" />
        </template>
      </el-input>
    </el-col>
  </el-row>
  <el-divider />
  <el-row justify="center" style="margin: 5px;">
    <el-col style="display: flex; justify-content: right;">
      <el-pagination background layout="prev, pager, next" :current-page="page.page_num" @current-change="refreshList"
        :page-count="page_count" />
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="24">
      <FlashSaleDetailCard v-for="item in flashSaleViewList" :flashSaleView="item" />
    </el-col>
  </el-row>
  <el-row justify="center" style="margin: 5px;">
    <el-col style="display: flex; justify-content: right;">
      <el-pagination background layout="prev, pager, next" :current-page="page.page_num" @current-change="refreshList"
        :page-count="page_count" />
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { msgError, msgSuccess, msgWarning } from '@/utils/message';
import FlashSaleDetailCard from '@/components/FlashSaleDetailCard.vue';
import { getByName } from '@/axios/flash_fale';
import { Search } from '@element-plus/icons-vue'

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

// #region 分页
const page = reactive({
  page_size: 10,
  page_num: 1,
  total: 0,
});
const page_count = computed(() => {
  return Math.ceil(page.total / page.page_size);
});
const refreshList = (page_num) => {
  page.page_num = page_num;
  getListByName();
}
// #endregion

// #region 秒杀列表
const flashSaleViewList = ref([])
const getList = async () => {
  isLoading.value = true;
  try {
    const res = await getListData()
    if (res.code === 1) {
      flashSaleViewList.value = res.data.items;
      page.total = res.data.total;
    }
  } catch {
    msgError('获取秒杀列表失败')
  } finally {
    isLoading.value = false;
  }
}
// #endregion

// #region 加载
const isLoading = ref(false)
// #endregion

// #region 搜索属性和方法
const searchName = ref('')
const getListByName = async () => {
  isLoading.value = true;
  try {
    const res = await getByName({ product_name: searchName.value, page_num: page.page_num, page_size: page.page_size })
    if (res.code === 1) {
      page.total = res.data.total;
      flashSaleViewList.value = res.data.items;
    }
  } catch {
    msgError('获取商品列表失败');
  } finally {
    isLoading.value = false;
  }
}
// #endregion

onMounted(() => {
  getListByName();
});
</script>

<style scoped>
/* 添加组件样式 */
</style>