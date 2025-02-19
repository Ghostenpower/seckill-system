<template>
  <el-row justify="center">
    <el-col :span="14">
      <el-descriptions direction="vertical" border style="margin-top: 20px">
        <el-descriptions-item :rowspan="3" :width="140" label="头像" align="center">
          <el-image style="width: 100px; height: 100px"
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
        </el-descriptions-item>
        <el-descriptions-item label="用户名">{{ userInfo.username }}</el-descriptions-item>
        <el-descriptions-item label="电话">{{ userInfo.phone == null ? 'null' : userInfo.phone }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ userInfo.email == null ? 'null' : userInfo.email }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ userTime.createdAtFormatted }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{ userTime.updatedAtFormatted }}</el-descriptions-item>
        <el-descriptions-item label="余额">
          <el-tag size="small">￥{{ userInfo.balance }}</el-tag>
        </el-descriptions-item>
      </el-descriptions>
    </el-col>
  </el-row>
  <el-row style="margin-top: 5px;" justify="center">
    <el-col align="middle" :span="5">
      <el-button type="primary" @click="changeDialogFormVisible">更新信息</el-button>
    </el-col>
  </el-row>

  <el-dialog v-model="dialogFormVisible" title="UpdateInfo" width="500">
    <el-form :model="userForm">
      <el-form-item label="用户名">
        <el-input v-model="userForm.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="userForm.phone" autocomplete="off" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="userForm.email" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="commitUserForm()">
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
import { getUserInfo, updateInfo } from '@/axios/user';

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

// #region 用户信息获取
const userInfo = ref({});
const GetUserInfo = async () => {
  try {
    const res = await getUserInfo();
    if (res.code === 1) {
      userInfo.value = res.data;
    }
  } catch {
    msgError('获取用户信息失败');
  }
};
const userTime = computed(() => {
  const createdAt = userInfo.value.created_at / 1000; // 转换为毫秒
  const updatedAt = userInfo.value.updated_at / 1000; // 转换为毫秒
  return {
    createdAtFormatted: new Date(createdAt * 1000).toLocaleString(),
    updatedAtFormatted: new Date(updatedAt * 1000).toLocaleString(),
  };
});
// #endregion

const dialogFormVisible = ref(false);
const changeDialogFormVisible = () => {
  userForm.username = userInfo.value.username;
  userForm.phone = userInfo.value.phone == null ? '' : userInfo.value.phone;
  userForm.email = userInfo.value.email == null ? '' : userInfo.value.email;
  dialogFormVisible.value = true;
}

const userForm = reactive({
  username: '',
  password: '',
  phone: '',
  email: '',
});
const commitUserForm = () => {
  updateInfo(userForm).then(res => {
    if (res.code === 1) {
      msgSuccess('更新成功');
      GetUserInfo();
      dialogFormVisible.value = false;
    } else {
      msgError('更新失败');
    }
  })
}

onMounted(() => {
  GetUserInfo();
});
</script>

<style scoped>
/* 添加组件样式 */
</style>