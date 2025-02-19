<template>
  <el-row justify="center">
    <el-col align="middle" :span="12">
      <el-input v-model="inputName" clearable style="max-width: 600px;width: 60%;" placeholder="Please input"
        class="input-with-select">
        <template #append>
          <el-button type="primary" :icon="Search" @click="GetByName()" />
        </template>
      </el-input>
    </el-col>
    <el-col :span="4">
      <el-button type="primary" @click="dialogFormVisible = true">创建商品</el-button>
    </el-col>
  </el-row>
  <el-divider></el-divider>
  <el-row justify="center">
    <el-col :span="4">
      <el-pagination background layout="prev, pager, next" :current-page="page.page_num" @current-change="refreshList"
        :page-count="page_count" />
    </el-col>
  </el-row>
  <el-row justify="center">
    <el-col :span="20">
      <el-table height="400px" :data="tableData" style="width: 100%;">
        <el-table-column prop="name" label="商品名" width="150" />
        <el-table-column prop="description" label="描述" width="480" />
        <el-table-column prop="price" label="单价" width="100" />
        <el-table-column prop="stock" label="库存" width="80" />
        <el-table-column prop="status" label="状态" width="80" />
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button type="info" size="small" @click="editProduct(scope)">编辑</el-button>
            <el-button type="danger" size="small" @click="DeleteProduct(scope)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>

  <el-dialog v-model="dialogFormVisible" title="Shipping address" width="500">
    <el-form :model="createForm">
      <el-form-item label="商品名" >
        <el-input v-model="createForm.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="介绍" >
        <el-input type="textarea" v-model="createForm.description" autocomplete="off" />
      </el-form-item>
      <el-form-item label="单价" >
        <el-input-number v-model="createForm.price" :precision="2" :step="0.1" />
      </el-form-item>
      <el-form-item label="库存" >
        <el-input v-model="createForm.stock" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="CreateProduct()">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="editFormVisible" title="Shipping address" width="500">
    <el-form :model="productForm">
      <el-form-item label="name">
        <el-input v-model="productForm.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="单价">
        <el-input-number v-model="productForm.price" :precision="2" :step="0.1" />
      </el-form-item>
      <el-form-item label="库存">
        <el-input-number v-model="productForm.stock" :step="1" />
      </el-form-item>
      <el-form-item label="描述">
        <el-input type="textarea" v-model="productForm.discription" autocomplete="off" />
      </el-form-item>
      <el-form-item label="status">
        <el-select v-model="productForm.status" placeholder="Select" size="large" style="width: 240px">
          <el-option label="1" value="1" />
          <el-option label="0" value="0" />
        </el-select>
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="commitEditForm()">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { msgError, msgSuccess, msgWarning } from '@/utils/message';
import { getListData, getByName, edit_product,deleteProduct,create_product } from '@/axios/product';
import { Search } from '@element-plus/icons-vue'

const createForm = reactive({
  name: '',
  description: '',
  price: '',
  stock: '',
  status: 1,
});
const CreateProduct= async () => {
  const res = await create_product(createForm);
  if (res.code === 1) {
    msgSuccess(res.msg);
    dialogFormVisible.value = false;
    GetListData();
  } else {
    msgError(res.msg);
  }
}

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

// #region 用户列表数据
const tableData = ref([]);
const search = ref({
  product_id: 1,
  name: '',
});
const GetListData = async () => {
  const res = await getListData({ ...search.value, ...page });
  if (res.code === 1) {
    tableData.value = res.data.items;
    page.total = res.data.total;
  } else {
    msgError(res.msg);
  }
};
// #endregion

// #region 分页
const page = reactive({
  total: 0,
  page_num: 1,
  page_size: 10,
});
const page_count = computed(() => {
  return Math.ceil(page.total / page.page_size);
});
// #endregion

// #region 操作方法
const dialogFormVisible = ref(false)
const productForm = reactive({
  product_id: '',
  name: '',
  discription: '',
  price: '',
  stock: '',
  status: 0,
});
const refreshList = async (page_num) => {
  page.page_num = page_num;
  await GetListData();
}
const editFormVisible = ref(false)
const createProduct = () => {
  productForm.product_id = 0;
  productForm.name = '';
  productForm.stock = '';
  productForm.description = '';
  productForm.price = '';
  productForm.status = 1;
  productForm.description = '',
    dialogFormVisible.value = true;
}
const commitCreateForm = async () => {
  const res = await create_product({ ...productForm, ...page });
  if (res.code === 1) {
    msgSuccess(res.msg);
    dialogFormVisible.value = false;
    GetListData();
  } else {
    msgError(res.msg);
  }
}
const editProduct = async (productInfo) => {
  productForm.product_id = productInfo.row.product_id;
  productForm.name = productInfo.row.name;
  productForm.stock = productInfo.row.stock;
  productForm.description = productInfo.row.description;
  productForm.price = productInfo.row.price;
  editFormVisible.value = true;
}
const commitEditForm = async () => {
  const res = await edit_product({ ...productForm, ...page });
  if (res.code === 1) {
    msgSuccess(res.msg);
    editFormVisible.value = false;
    GetListData();
  } else {
    msgError(res.msg);
  }
}
// #endregion
const inputName = ref('')
const GetByName = async () => {
  try {
    const res = await getByName({ product_id: 0, name: inputName.value, page_num: 1, page_size: page.page_size });
    if (res.code === 1) {
      tableData.value = res.data.items;
      page.total = res.data.total;
    } else {
      msgError(res.msg);
    }
  } catch {
    msgError('获取商品列表失败')
  }
}

const DeleteProduct = async (product) => {
  try{
    const res = await deleteProduct({ product_id: product.row.product_id })
    if(res.code === 1){
      msgSuccess(res.msg)
      GetListData()
    }
  }catch{
    msgError(res.msg)
  }
}

onMounted(() => {
  GetListData()
});
</script>

<style scoped>
/* 添加组件样式 */
</style>