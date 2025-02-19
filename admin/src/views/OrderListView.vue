<template>
  <!-- 添加你的模板内容 -->
  <el-row justify="center">
    <el-col :span="20">
      <el-table :data="tableData" style="width: 100%" border >
          <el-table-column label="秒杀活动编号"  prop="flash_sale_id" />
          <el-table-column label="商品编号" sortable prop="product_id" />
          <el-table-column label="数量" prop="quantity" />
          <el-table-column label="总价格(元)" prop="total_price" />
          <el-table-column label="订单状态" prop="status" />
          <el-table-column label="创建时间" prop="created_at" />
          <el-table-column label="更新时间" prop="updated_at" />
          <el-table-column label="操作" fixed="right">
            <template #default="scope">
              <el-button type="text" @click="pay(scope)">支付</el-button>
              <el-button type="text" @click="cancel(scope)">取消</el-button>
            </template>
          </el-table-column>>
        </el-table>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { getListData } from '@/axios/orders';
import { msgError } from '@/utils/message';

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

// #region  分页器
const page = reactive({
  page_num: 1,
  page_size: 30,
  total: 0
});
const totalPage = computed(() => {
  return Math.ceil(page.total / page.page_size);
});
// #endregion

// #region 表格数据
const tableData = ref([])
const getTableData = async () => {
  try {
    const response = await getListData({ page_num: page.page_num, page_size: page.page_size })
    tableData.value = response.data.items
  } catch (error) {
    msgError('获取订单列表失败')
  } finally {

  }
}
// #endregion

onMounted(async () => {
  await getTableData()
});
</script>

<style scoped>
/* 添加组件样式 */
</style>