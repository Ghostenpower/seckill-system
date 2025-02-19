<template>
    <el-card v-loading="isLoading" style="margin-bottom: 10px;">
        <el-row type="flex" align="middle" v-if="!isLoading">
            <el-col :span="4" align="middle">
                <el-image style="width: 150px; height: 150px;" :src="image" />
            </el-col>
            <el-col :span="8">
                <el-descriptions direction="vertical" :column="1" title="秒杀信息">
                    <el-descriptions-item label="秒杀商品">{{ flashSaleView.product_name }}</el-descriptions-item>
                    <el-descriptions-item label="商品介绍">
                        <el-tooltip :content="flashSaleView.description" placement="bottom">
                            <el-text style="max-width: 400px;" truncated>
                                {{ flashSaleView.description }}
                            </el-text>
                        </el-tooltip>
                    </el-descriptions-item>
                </el-descriptions>
            </el-col>
            <el-col :span="6">
                <el-descriptions direction="vertical" :column="1" title="">
                    <el-descriptions-item label="秒杀价格">
                        <el-text type="danger" style="font-size: 28px;" size="large">
                            ￥{{ flashSaleView.flash_price }}
                        </el-text>
                        <el-text tag="del">
                            ￥{{ flashSaleView.original_price }}
                        </el-text>
                    </el-descriptions-item>
                </el-descriptions>
            </el-col>
            <el-col :span="6">
                <el-space direction="vertical" alignment="center">
                    <el-text :type="flashTime.isEnding.value == true ? 'danger' : 'info'" size="large">
                        {{ flashTime.msg.value }}
                        {{ flashTime.msg.value=="活动已结束"?"":flashTime.time.value }}
                    </el-text>
                    <el-button type="primary" size="large"
                        :disabled="flashTime.msg.value == '活动已结束' || flashTime.msg.value == '距离活动开始还有：'"
                        @click="setFlashSale()">立即秒杀</el-button>
                    <el-text>
                        <el-tag type="danger" size="small">库存：{{ flashSaleView.total_stock - flashSaleView.sold_count
                            }}</el-tag>
                    </el-text>
                </el-space>
            </el-col>
        </el-row>
        <el-skeleton v-if="isLoading" :rows="5" animated />
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

// #region 秒杀详情
const props = defineProps({
    flashSaleView: {
        type: Object,
        required: true
    }
});
// #endregion

// #region 加载
const isLoading = ref(true);
// #endregion

// #region 秒杀数据
const flashSaleView = computed(() => {
    return props.flashSaleView
});
// #endregion

// #region 秒杀时间
const flashTime = computed(() => {
    const now = Math.floor(nowTime.value.getTime() / 1000);
    const msg = ref('');
    const time = ref('');
    const isEnding = ref(false);
    let timeDiff = 0; // 这里直接使用普通变量，而不是 ref
    // 计算活动开始时间与当前时间的差
    if (flashSaleView.value.start_time > now) {
        msg.value = '距离活动开始还有：';
        timeDiff = flashSaleView.value.start_time - now
    } else if (flashSaleView.value.end_time > now) {
        msg.value = '距离活动结束还有：';
        timeDiff = flashSaleView.value.end_time - now
        if (timeDiff < 600) {
            isEnding.value = true
        }
    } else {
        msg.value = '活动已结束';
        timeDiff = 0
    }
    time.value = `${Math.floor(timeDiff / 3600)}时${Math.floor((timeDiff % 3600) / 60)}分${Math.floor(timeDiff % 60)}秒`
    return {
        msg,
        time,
        isEnding,
        timeDiff
    }

})
// #endregion

// #region 获取时间
const nowTime = ref(new Date())

const timer = ref(null)
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
        const res = await FlashSale({ flash_sale_id: flashSaleView.value.flash_sale_id })
        if (res.code === 1) {
            msgSuccess('秒杀成功')
            flashSaleView.value.sold_count += 1
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
    timer.value = setInterval(() => {
        nowTime.value = new Date();
    }, 1000);
    isLoading.value = false
});
</script>

<style scoped>
/* 添加组件样式 */
</style>