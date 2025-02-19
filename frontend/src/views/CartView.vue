<template>
    <!-- <el-row width="80%" justify="center">
        <el-col align="middle" :span="24">
            <el-input v-model="searchName" style="max-width: 600px" placeholder="Please input"
                class="input-with-select">
                <template #append>
                    <el-button type="primary" :icon="Search" @click="GetCartByName()" />
                </template>
</el-input>
</el-col>
</el-row>
<el-divider></el-divider> -->
    <el-row width="80%" justify="center">
        <el-col :span="18">
            <el-table :data="tableData" style="width: 100%" border @selection-change="handleSelectionChange">
                <!-- 多选列 -->
                <el-table-column type="selection" width="55" />
                <!-- 其他列 -->
                <el-table-column label="商品" prop="product_name" />
                <el-table-column label="单价" sortable prop="product_price" />
                <el-table-column label="数量" prop="quantity" />
                <el-table-column label="单价(元)" prop="product_price" />
                <el-table-column label="总价格(元)" prop="total_price" />
                <el-table-column label="操作" fixed="right">
                    <template #default="scope">
                        <el-link type="danger" @click="DeleteOne(scope)">删除</el-link>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
    </el-row>
    <el-row style="margin-top: 10px;" width="80%" justify="end">
        <el-col :span="4">
            <el-button type="primary" size="large" :disabled="selectedRows.length === 0" @click="Submit">结算</el-button>
        </el-col>
        <el-col :span="4">
            <el-button type="danger" :disabled="selectedRows.length === 0" @click="DeleteSelected">删除选中商品</el-button>
        </el-col>
    </el-row>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { msgError, msgSuccess, msgWarning } from '@/utils/message';
import { getCartByName, submit, deleteSelected } from '@/axios/cart';

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion
// #region 仓库
const store = useStore();
// #endregion

// #region 搜索
const searchName = ref('');
const GetCartByName = async () => {
    const res = await getCartByName({ product_name: searchName.value });
    if (res.code === 1) {
        // 使用 map 方法遍历每个对象，并添加 `isChoose` 键值对
        const updatedItems = res.data.map(item => ({
            ...item, // 保留原有的属性
            isChoose: false, // 新增的多选键值对，默认为 false
            total_price: parseFloat((item.product_price * item.product_num).toFixed(2)),
            quantity: item.product_num
        }));
        // 使用 Object.assign 将修改后的数据赋值给 tableData
        tableData.value = updatedItems;
    } else {
        msgError(res.msg);
    }
};
// #endregion

// #region 分页
const page = reactive({
    page_num: 1,
    page_size: 10,
    total: 0
});
// #endregion

// #region 表格数据
const tableData = ref([]);

// #endregion

// #region 多选
const selectedRows = ref([]); // 存储选中的行

// 处理选择变化
const handleSelectionChange = (val) => {
    selectedRows.value = val;
};
// #endregion

const Submit = async () => {
    try {
        const res = await submit(selectedRows.value)
        if (res.code === 1) {
            msgSuccess(res.msg)
            GetCartByName()
        } else {
            msgError(res.msg)
            GetCartByName()
        }
    } catch {
        msgError('提交失败')
    }
}

const DeleteSelected = async () => {
    try {
        const res = await deleteSelected(selectedRows.value)
        if (res.code === 1) {
            msgSuccess(res.msg)
            setTimeout(() => {
                GetCartByName()
            }, 500)
        } else {
            msgError(res.msg)
        }
    } catch {
        msgError('删除失败')
    }
}

const DeleteOne = async (scope) => {
    try {
        const res = await deleteSelected([scope.row])
        if (res.code === 1) {
            msgSuccess(res.msg)
            setTimeout(() => {
                GetCartByName()
            }, 500)
        } else {
            msgError(res.msg)
        }
    } catch {
        msgError('删除失败')
    }
}

onMounted(() => {
    GetCartByName();
});
</script>

<style scoped>
/* 添加组件样式 */
</style>