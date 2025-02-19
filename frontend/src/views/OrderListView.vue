<template>
  <!-- 添加你的模板内容 -->
  <el-row justify="center">
    <el-col :span="20">
      <el-table :data="tableData" style="width: 100%" border>
        <el-table-column label="秒杀活动编号" sortable prop="flash_sale_id" />
        <el-table-column label="商品编号" sortable prop="product_id" />
        <el-table-column label="数量" sortable prop="quantity" />
        <el-table-column label="总价格(元)" sortable prop="total_price" />
        <el-table-column label="订单状态" sortable prop="status" />
        <el-table-column label="创建时间" sortable prop="created_at" />
        <el-table-column label="更新时间" sortable prop="updated_at" />
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
import { msgSuccess, msgWarning } from '@/utils/message';
import { payOrder, cancelOrder } from '@/axios/orders';

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
    if (response.code === 1) {
      const updateData = response.data.items.map((item) => {
        return {
          ...item, // 保留原始项
          created_at: new Date(item.created_at).toLocaleString(), // 转换为时间字符串
          updated_at: new Date(item.updated_at).toLocaleString(),  // 转换为时间字符串
          status: item.status === 1 ? "待支付" : item.status === 2 ? "已支付" : item.status === 3 ? "已取消" : "未知状态"
        };
      });
      tableData.value = updateData
    }
  } catch (error) {
    msgError('获取订单列表失败')
  } finally {

  }
}
// #endregion

const pay = async (scope) => {
  try {
    const res = await payOrder({ order_id: scope.row.order_id })
    if (res.code === 1) {
      msgSuccess('支付成功')
      getTableData()
    } else {
    
    }
  } catch {
    msgError('支付失败')
  }
}

const cancel = async (scope) => {
  try {
    const res = await cancelOrder({ order_id: scope.row.order_id })
    if (res.code === 1) {
      msgSuccess('取消成功')
      getTableData()
    } else {
      msgWarning(res.msg)
    }
  } catch {
    msgError('取消失败')
  }
}

onMounted(async () => {
  await getTableData()
});
</script>

<style scoped>
/* 添加组件样式 */
</style>