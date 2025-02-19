<template>
    <el-main width="80%">
        <el-row>
            <el-col :span="16">
                <h2>秒杀活动</h2>
            </el-col>
            <el-col :span="8" align="right">
                <el-link type="primary" @click="router.push('/flashSale/list')">查看更多></el-link>
            </el-col>
        </el-row>
        <el-row justify="center">
            <el-col :span="16">
                <el-carousel v-loading="isLoading" min-height="400px">
                    <el-carousel-item v-for="item in seckillList" :key="item">
                        <el-row>
                            <el-col align="middle">
                                <FlashSaleCard :flashSale="item" />
                            </el-col>
                        </el-row>
                    </el-carousel-item>
                </el-carousel>
            </el-col>
        </el-row>
        <el-divider />
        <el-row>
            <el-col :span="16">
                <h2>热门商品</h2>
            </el-col>
            <el-col :span="8" align="right">
                <el-link type="primary" @click="router.push('/product/product-list')">查看更多></el-link>
            </el-col>
        </el-row>
        <el-row justify="space-around" v-loading="isLoading" v-for="(rowItems, rowIndex) in rows" :key="rowIndex">
            <el-col style="margin: 5px 0 5px 0;" :span="5" v-for="(item, colIndex) in rowItems" :key="colIndex">
                <ProductCard :product="item" />
            </el-col>
        </el-row>
    </el-main>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import ProductCard from '@/components/ProductCard.vue';
import { getListData } from '@/axios/product'
import { msgError, msgSuccess, msgWarning } from '@/utils/message';
import { getFlashSaleLimit3, getFlashSaleProductLimit3 } from '@/axios/flash_fale';
import FlashSaleCard from '@/components/FlashSaleCard.vue';
import { getPopularProduct } from '@/axios/product';

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

// #region 加载动画
const isLoading = ref(false);
// #endregion

// #region 商品数据属性以及获取方法
const productList = ref([]);
const getProductList = async () => {
    try {
        const response = await getPopularProduct(page);
        if (response.code === 1) {
            productList.value = response.data.items;
            page.page_num = response.data.page_num;
        }

    } catch {
        msgError('获取商品列表失败');
    }
};
// #endregion

// #region 秒杀活动数据属性以及获取方法，首页仅获取3条数据
const seckillList = ref([]);
const GetFlashSaleProductLimit3 = async () => {
    try {
        const response = await getFlashSaleProductLimit3();
        if (response.code === 1)
            seckillList.value = response.data;
    } catch {
        msgError('获取秒杀活动列表失败');
    }
};
// #endregion

// #region 分页数据
const page = reactive({
    page_size: 20,
    page_num: 1
});
// #endregion

// #region 列表行数,每行五个，向上取整
const rows = computed(() => {
    const page_size = page.page_size;
    const rows = [];
    for (let i = 0; i < productList.value.length; i += page_size) {
        rows.push(productList.value.slice(i, i + page_size));
    }
    return rows;
});
// #endregion

onMounted(async () => {
    isLoading.value == true;
    await getProductList();
    await GetFlashSaleProductLimit3();
    isLoading.value == false;
});
</script>

<style scoped>
.demonstration {
    color: var(--el-text-color-secondary);
}

.el-carousel__item h3 {
    color: #475669;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
    text-align: center;
}
</style>