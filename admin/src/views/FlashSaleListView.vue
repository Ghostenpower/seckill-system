<template>
  <el-row justify="center">
    <el-col align="middle" :span="12">
      <el-input v-model="search.username" style="max-width: 600px;width: 60%;" placeholder="Please input"
        class="input-with-select">
        <template #append>
          <el-button type="primary" :icon="Search" @click="GetUserList()" />
        </template>
      </el-input>
    </el-col>
    <el-col :span="4">
      <el-button type="primary" @click="dialogFormVisible = true">创建秒杀</el-button>
    </el-col>
  </el-row>
  <el-divider></el-divider>
  <el-row justify="end">
    <el-col :span="4">
      <el-pagination background layout="prev, pager, next" :current-page="page.page_num" @current-change="refreshList"
        :page-count="page_count" />
    </el-col>
  </el-row>
  <el-row justify="center">
    <el-col :span="20">
      <el-table height="400px" :data="tableData" style="width: 100%;">
        <el-table-column prop="product_name" label="商品" width="250" />
        <el-table-column prop="original_price" label="原价" width="120" />
        <el-table-column prop="flash_price" label="秒杀价" width="120" />
        <el-table-column prop="total_stock" label="总库存" width="120" />
        <el-table-column prop="sold_count" label="已售数量" width="120" />
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button type="danger" size="small" @click="deleteFlashSaleById(scope)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>


  <el-dialog v-model="dialogFormVisible" title="Shipping address" width="500">
    <el-form :model="flashForm">
      <el-form-item label="商品编号">
        <el-input v-model="flashForm.product_id" autocomplete="off" />
      </el-form-item>
      <el-form-item label="秒杀价格">
        <el-input v-model="flashForm.flash_price" autocomplete="off" />
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker v-model="flashForm.start_time" type="datetime" placeholder="Select date and time"
          :shortcuts="shortcuts" />
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker v-model="flashForm.end_time" type="datetime" placeholder="Select date and time"
          :shortcuts="shortcuts" />
      </el-form-item>
      <el-form-item label="库存">
        <el-input v-model="flashForm.total_stock" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="commitCreateForm()">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<script setup>
import { ref, reactive, onMounted, computed, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { Search } from '@element-plus/icons-vue'
import { getByName, initializeFlashSale, deleteFlashSale } from '../axios/flash_fale';
import { msgError, msgSuccess } from '../utils/message';

const shortcuts = [
  {
    text: '十分钟后',
    value: () => {
      const date = new Date()
      date.setMinutes(date.getMinutes() + 10)
      return date
    },
  },
  {
    text: '30分钟后',
    value: () => {
      const date = new Date()
      date.setMinutes(date.getMinutes() + 30)
      return date
    },
  },
  {
    text: '一小时后',
    value: () => {
      const date = new Date()
      date.setHours(date.getHours() + 1)
      return date
    },
  },
]

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

// #region 列表数据
const tableData = ref([{
  flash_sale_id: 0,
  product_id: 0,
  product_name: '',
  description: '',
  start_time: '',
  end_time: '',
  status: '',
  original_price: 0,
  flash_price: 0,
  total_stock: 0,
  sold_count: 0,
  flash_sale_status: 1,
  product_stock: 0,
  status: 1,
}]);
const search = ref({
  flash_sale_id: 0,
  product_id: 0,
  product_name: '',
  description: '',
  start_time: '',
  end_time: '',
  status: '',
  original_price: 0,
  flash_price: 0,
  total_stock: 0,
  sold_count: 0,
  total_stock: 0,
  flash_sale_status: 1,
  product_stock: 0,
  status: 1,
});

const page = ref({
  page_num: 1,
  page_size: 10,
  total: 0,
});
const page_count = computed(() => {
  return Math.ceil(page.value.total / page.value.page_size);
});

const GetByName = async () => {
  try {
    const res = await getByName({ ...search.value, ...page.value });
    tableData.value = res.data.items;
    page.value.total = res.data.total;
  } catch {
    msgError('获取商品列表失败')
  }
}

const refreshList = (page_num) => {
  page.value.page_num = page_num;
  GetByName();
}

const dialogFormVisible = ref(false);
const flashForm = ref({
  product_id: 0,
  start_time: new Date(),
  end_time: new Date(),
  flash_price: 1543.99,
  total_stock: 10,
  status:1,
});
const flashFormCom = computed(() => {
  return {
    product_id: flashForm.value.product_id,
    start_time: flashForm.value.start_time.getTime() / 1000,
    end_time: flashForm.value.end_time.getTime() / 1000,
    flash_price: flashForm.value.flash_price,
    total_stock: flashForm.value.total_stock,
    status:1
  }
});
watch(dialogFormVisible, (newVal, oldVal) => {
  if (newVal == true) {
    flashForm.value = {
      product_id: 1,
      start_time: new Date(),
      end_time: new Date(),
      flash_price: 1543.99,
      total_stock: 10
    }
  }
});

const commitCreateForm = async () => {
  try {
    const res = await initializeFlashSale(flashFormCom.value);
    if (res.code === 1) {
      msgSuccess('创建成功');
      GetByName();
    } else {
      msgError(res.msg);
    }
  } catch {
    msgError('创建失败')
  } finally {
    dialogFormVisible.value = false;
  }
}


const deleteFlashSaleById = async (flash) => {
  console.log(flash.row.flash_sale_id)
  try {
    const res = await deleteFlashSale({ flash_sale_id: flash.row.flash_sale_id });
    if (res.code === 1) {
      msgSuccess('删除成功');
      GetByName();
    } else {
      msgError(res.msg);
    }
  } catch {
    msgError('删除失败')
  }
}

onMounted(() => {
  GetByName()
});
</script>

<style scoped>
/* 添加组件样式 */
</style>