<template>
    <el-card style="width: 70%;height: 250px;" shadow="hover">
        <el-row>
            <el-col :span="10" align="middle">
                <div class="demo-image__preview">
                    <el-image l style="width: 150px; height: 150px" :src="image" fit="cover" />
                </div>
            </el-col>
            <el-col :span="14" align="left">
                <div class="card-title"></div>
                <el-text class="mx-1" style="font-size: 24px;">{{ flashSale.product_name }}</el-text>
                <br>
                <!-- 价格 -->
                <el-text class="mx-1" style="font-size: 24px;" type="danger">￥{{ flashSale.flash_price }}</el-text>
                <el-text tag="del">￥{{ flashSale.original_price }}</el-text>
                <br>
                <el-text size="large" class="mx-1">秒杀总数量：{{ flashSale.total_stock }}</el-text>
                <br>
                <el-text size="large" class="mx-1">剩余数量：{{ flashSale.total_stock - flashSale.sold_count }}</el-text>
                <br />
                <el-text class="mx-1">{{ flashSaleTime.msg }} {{ flashSaleTime.msg == "活动已结束" ? "" : flashSaleTime.time
                    }}</el-text>
                <el-row justify="center">
                    <el-col :span="12">
                        <el-button type="primary" @click="setFlashSale()">秒杀</el-button>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
    </el-card>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { msgError, msgSuccess, msgWarning } from '@/utils/message';
import { FlashSale } from '@/axios/flash_fale';
import { getRandomImage } from '@/utils/imageUtils';

const image = ref(getRandomImage());

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

// #region flashSale初始值
const flashSale = ref({
    flash_sale_id: 1,
    product_id: 1,
    product_name: '',
    start_time: [1, 1, 1, 1, 1, 1],
    end_time: [1, 1, 1, 1, 1, 1],
    total_stock: 0,
    sold_count: 0,
    original_price: 0,
    flash_price: 0,
    flash_sale_status: 0,
    product_stock: 0,
    status: 0
});

// #endregion

// #region 从父组件获取数据
const props = defineProps({
    flashSale: {
        type: Object,
        required: true
    }
});
// #endregion

// #region 最新时间
// 用于存储当前时间
const currentTime = ref(new Date());
// 设置定时器
let timer = null;
// #endregion

// #region 活动时间与当前时间相关计算
// 提取一个函数来格式化剩余时间
const flashSaleTime = computed(() => {
    // 获取当前时间（单位为秒）
    const now = Math.floor(currentTime.value.getTime() / 1000);
    const msg = ref('');
    const time = ref('');
    const status = ref('');
    let timeDiff = 0; // 这里直接使用普通变量，而不是 ref

    // 计算活动开始时间与当前时间的差
    if (flashSale.value.start_time > now) {
        msg.value = '距离活动开始还有：';
        timeDiff = flashSale.value.start_time - now;
        status.value = '活动未开始';
    }
    // 计算活动结束时间与当前时间的差
    else if (flashSale.value.end_time > now) {
        msg.value = '距离活动结束还有：';
        timeDiff = flashSale.value.end_time - now;
        status.value = '活动进行中';
    }
    // 活动已结束
    else {
        msg.value = '活动已结束';
        timeDiff = 0;
        status.value = '活动已结束';
    }

    // 计算剩余时间（单位为秒）
    const hours = Math.floor(timeDiff / 3600); // 计算小时
    const minutes = Math.floor((timeDiff % 3600) / 60); // 计算分钟
    const seconds = timeDiff % 60; // 计算秒数

    // 格式化时间
    time.value = `${hours}小时 ${minutes}分钟 ${seconds}秒`;

    return {
        msg: msg.value,
        time: time.value,
        status: status.value,
        now: now
    };
});

// #endregion

// #region 秒杀
const isGetting = ref(false)
const setFlashSale = async () => {
    if (isGetting.value) {
        msgWarning('请勿频繁点击')
        return
    } else {
        isGetting.value = true
    }
    try {
        const res = await FlashSale({ flash_sale_id: flashSale.value.flash_sale_id })
        if (res.code === 1) {
            msgSuccess('秒杀成功')
        } else {
            msgWarning(res.msg)
        }
    } catch {
        msgError('秒杀失败')
    } finally {
        isGetting.value = false
    }
}
// #endregion

onMounted(() => {
    timer = setInterval(() => {
        currentTime.value = new Date();
    }, 1000);
});
flashSale.value = props.flashSale;
</script>

<style scoped>
/* 添加组件样式 */
</style>