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
      <el-button type="primary" @click="">更新信息</el-button>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { msgError, msgSuccess, msgWarning } from '@/utils/message';
import { getUserInfo } from '@/axios/user';

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

onMounted(() => {
  GetUserInfo();
});
</script>

<style scoped>
/* 添加组件样式 */
</style>