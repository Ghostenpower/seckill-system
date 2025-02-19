<template>
    <el-card style="min-width: 280px; width: 100%;min-height: 200px;" shadow="hover">
        <el-row justify="center">
            <el-col align="middle">
                <div class="demo-image__preview">
                    <el-image l style="width: 150px; height: 150px"
                        src="https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg"
                        :preview-src-list="['https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg']"
                        fit="cover" />
                </div>
            </el-col>
        </el-row>
        <el-row>
            <el-col align="middle">
                <div class="product-info">
                    <el-tooltip :content="product.name" placement="top">
                        <el-text style="max-width: 200px;" class="mx-1" size="large" truncated>商品：{{ product.name
                            }}</el-text>
                    </el-tooltip>
                    <br />
                    <el-text class="mx-1">价格：￥{{ product.price }}</el-text>
                    <el-row justify="center">
                        <el-col align="middle" :span="6">
                        </el-col>
                        <el-col align="middle" :span="12">
                            <el-input-number size="small" v-model="num" :min="1" :max="product.stock" />
                        </el-col>
                        <el-col align="middle" :span="6">
                            <el-text size="small" type="info">剩余{{ product.stock }}</el-text>
                        </el-col>
                    </el-row>
                </div>
            </el-col>
        </el-row>
        <el-divider />
        <el-row justify="center">
            <el-col align="middle" :span="24">
                <el-button type="primary" size="default" @click="buy">立即购买</el-button>
            </el-col>
        </el-row>
    </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { msgError, msgSuccess, msgWarning } from '@/utils/message';
import { getListData, getByName, buyProduct } from '@/axios/product';

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

// #region 获取商品数据
const props = defineProps({
    product: {
        type: Object,
        required: true
    }
});
const product_id=ref(props.product.product_id);
// #endregion

// #region 购买属性和方法
// 购买数量
const num = ref(1);
const buy = async () => {
    try {
        const response = await buyProduct({
            product_id: product_id.value,
            num: num.value
        });
        if (response.code === 1) {
            msgSuccess('购买成功');
        }
    } catch {
        msgError('购买失败');
    }
}
// #endregion

onMounted(() => {

});
</script>

<style scoped>
.demo-image__error .image-slot {
    font-size: 30px;
}

.demo-image__error .image-slot .el-icon {
    font-size: 30px;
}

.demo-image__error .el-image {
    width: 100%;
    height: 200px;
}
</style>