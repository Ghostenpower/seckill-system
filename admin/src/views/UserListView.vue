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
      <!-- <el-button type="primary" @click="dialogFormVisible = true">创建用户</el-button> -->
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
        <el-table-column prop="user_id" label="编号" width="100" />
        <el-table-column prop="username" label="用户名" width="150" />
        <el-table-column prop="phone" label="电话" width="150" />
        <el-table-column prop="email" label="电子邮箱" width="200"/>
        <el-table-column prop="balance" label="余额(元)" width="100"/>
        <el-table-column prop="status" label="状态" width="100"/>
        <el-table-column fixed="right" label="操作" >
          <template #default="scope">
            <el-button type="info" size="small" @click="editUser(scope)">编辑</el-button>
            <el-button type="danger" size="small" @click="DeleteUserById(scope)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>


  <el-dialog v-model="dialogFormVisible" title="Shipping address" width="500">
    <el-form :model="userForm">
      <el-form-item label="username" :label-width="formLabelWidth">
        <el-input v-model="userForm.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="password" :label-width="formLabelWidth">
        <el-input v-model="userForm.password" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="editFormVisible" title="Shipping address" width="500">
    <el-form :model="userForm">
      <el-form-item label="username">
        <el-input v-model="userForm.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="password">
        <el-input v-model="userForm.password" autocomplete="off" />
      </el-form-item>
      <el-form-item label="email">
        <el-input v-model="userForm.email" autocomplete="off" />
      </el-form-item>
      <el-form-item label="phone">
        <el-input v-model="userForm.phone" autocomplete="off" />
      </el-form-item>
      <el-form-item label="status">
        <el-input v-model="userForm.status" autocomplete="off" />
      </el-form-item>
      <el-form-item label="balance">
        <el-input-number v-model="userForm.balance" :precision="2" :step="0.1" />
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
import { getUserList, edit_user,deleteUserById } from '@/axios/user';
import { Search } from '@element-plus/icons-vue'

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
  user_id: '',
  username: '',
  phone: '',
  email: '',
  status: '',
});
const GetUserList = async () => {
  const res = await getUserList({ ...search.value, ...page });
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

// #region 用户操作方法
//添加用户
const dialogFormVisible = ref(false)
const userForm = reactive({
  user_id: '',
  username: '',
  password: '',
  email: '',
  phone: '',
  status: 0,
  balance: 0,
});
const refreshList = async (page_num) => {
  page.page_num = page_num;
  await GetUserList();
}
const editFormVisible = ref(false)
const editUser = async (userInfo) => {
  userForm.user_id = userInfo.row.user_id;
  userForm.username = userInfo.row.username;
  userForm.password = ''
  userForm.phone = userInfo.row.phone == null ? '' : userInfo.row.phone;
  userForm.email = userInfo.row.email == null ? '' : userInfo.row.email;
  userForm.status = userInfo.row.status;
  userForm.balance = userInfo.row.balance;
  editFormVisible.value = true;
}
const commitEditForm = async () => {
  const res = await edit_user({ ...userForm, ...page });
  if (res.code === 1) {
    msgSuccess(res.msg);
    editFormVisible.value = false;
    GetUserList();
  } else {
    msgError(res.msg);
  }
}
const deleteUser = async (user_id) => {

}
// #endregion

const DeleteUserById=async (userInfo)=>{
  const res = await deleteUserById({user_id:userInfo.row.user_id})
  if (res.code === 1) {
    msgSuccess(res.msg);
    GetUserList();
  } else {
    msgError(res.msg);
  }
}

onMounted(() => {
  GetUserList();
});
</script>

<style scoped>
/* 添加组件样式 */
</style>