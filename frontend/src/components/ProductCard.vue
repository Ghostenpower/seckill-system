<template>
    <el-card style="min-width: 280px; width: 100%;min-height: 200px;" shadow="hover">
        <el-row justify="center">
            <el-col align="middle">
                <div class="demo-image__preview">
                    <el-image l style="width: 150px; height: 150px" :src="image" :preview-src-list="[image]"
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
                            <el-input-number size="small" v-model="num" :min="1" :max="product.stock===0?1:product.stock" />
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
                <el-button type="primary" size="default" @click="AddToCart">加入购物车</el-button>

            </el-col>
        </el-row>
        <el-row style="margin-top: 5px;" justify="center">
            <el-col align="middle" :span="24">

                <el-button v-if="!product.isCollect" type="info" size="default" @click="toDetial">查看详情</el-button>
                <el-button v-if="!product.isCollect" type="info" size="default" @click="AddCollect">收藏</el-button>
                <el-button v-if="product.isCollect" type="danger" size="default" @click="DeleteCollect">取消收藏</el-button>
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
import { getRandomImage } from '@/utils/imageUtils';
import { addToCart } from '@/axios/cart';
import { addCollect, deleteCollect } from '@/axios/product';

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

const toDetial = () => {
    router.push({
        path: '/product/detail',
        query: { 
            product_id: product_id.value,
            name: props.product.name,
            price: props.product.price,
            stock: props.product.stock,
            description: props.product.description,
            status: props.product.status,
            isCollect: props.product.isCollect
         }
    });
}

// #region 获取商品数据
const props = defineProps({
    product: {
        type: Object,
        required: true
    }
});
const product_id = ref(props.product.product_id);
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
        } else {
            msgWarning(response.msg);
        }
    } catch {
        msgError('购买失败');
    }
}
// #endregion

// #region 加入购物车
const AddToCart = async () => {
    try {
        const response = await addToCart([{ product_id: product_id.value, product_num: num.value }])
        if (response.code === 1) {
            msgSuccess('加入购物车成功');
            //刷新
            router.push({ path: '/cart/list' });
        }
    } catch {
        msgError('加入购物车失败');
    } finally {

    }
}
// #endregion

const AddCollect = async () => {
    try {
        const response = await addCollect({ product_id: product_id.value })
        if (response.code === 1) {
            msgSuccess('收藏成功');
        } else {
            msgWarning(response.msg);
        }
    } catch {
        msgError('收藏失败');
    }
}

const DeleteCollect = async () => {
    try {
        const response = await deleteCollect({ product_id: product_id.value })
        if (response.code === 1) {
            msgSuccess('取消收藏成功');
        } else {
            msgWarning(response.msg);
        }
    } catch {
        msgError('取消收藏失败');
    }
}

const image = ref(getRandomImage());
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