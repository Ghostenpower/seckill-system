<template>
    <!-- <el-row justify="center" style="width: 100%;">
        <el-col :span="8" align="middle">
            <el-input v-model="searchName" style="max-width: 600px" placeholder="Please input"
                class="input-with-select">
                <template #append>
                    <el-button type="primary" :icon="Search" @click="getListByName()" />
                </template>
</el-input>
</el-col>
</el-row>
<el-divider /> -->
    <el-row justify="center" style="margin: 5px;">
        <el-col :span="8" align="middle">
            <h3>我的收藏</h3>
        </el-col>
    </el-row>
    <el-row width="60%" justify="center">
        <el-col :span="24">
            <el-space style="display: flex; justify-content: center;" wrap>
                <ProductCard style="max-width: 300px;" v-for="item in collectList" :product="item" />
            </el-space>
        </el-col>
    </el-row>

    <el-empty v-if="collectList.length === 0" description="暂无收藏" />
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { msgError, msgSuccess, msgWarning } from '@/utils/message';
import ProductCard from '@/components/ProductCard.vue';
import { getCollectByName } from '@/axios/product'
import { Search } from '@element-plus/icons-vue'

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

const searchName = ref('');
const collectList = ref([]);
const GetCollectByName = async () => {
    const res = await getCollectByName({ product_name: searchName.value });
    if (res.code === 1) {
        const updatedItems = res.data.map(item => ({
            ...item, // 保留原有的属性
            isCollect: true
        }));
        collectList.value = updatedItems;
    } else {
        msgError(res.msg);
    }
}

onMounted(() => {
    GetCollectByName()
});
</script>

<style scoped>
/* 添加组件样式 */
</style>