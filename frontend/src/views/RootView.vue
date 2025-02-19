<template>
  <el-container>
    <el-affix :offset="0">
      <el-header>
        <el-menu style="padding-left:50px;padding-right: 50px;" :default-active="currentRoute"
          mode="horizontal" @select="handleSelect" :ellipsis="false">
          <el-menu-item index="/">
            首页
          </el-menu-item>
          <el-menu-item index="/product/product-list">
            商品列表
          </el-menu-item>
          <el-menu-item index="/flashsale/list">
            秒杀活动
          </el-menu-item>
          <el-menu-item index="/cart/list">
            我的购物车
          </el-menu-item>
          <el-menu-item index="/collect/list">
            我的收藏
          </el-menu-item>
          <el-menu-item v-if="token==null" index="/login">
            登录
          </el-menu-item>
          <el-sub-menu v-if="token!=null" index="4">
            <template #title><el-avatar size="small" src="circleUrl" /></template>
            <el-menu-item index="/order/order-list">订单</el-menu-item>
            <el-menu-item index="/my-info">个人中心</el-menu-item>
            <el-menu-item @Click="logout()">退出登录</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-header>
    </el-affix>

    <el-main min-width="1280px">
      <router-view></router-view>
    </el-main>
    <el-footer> </el-footer>
  </el-container>
</template>

<script lang="ts" setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStore } from 'vuex'

// 获取路由实例
const router = useRouter()
const route = useRoute()

const store = useStore()

//存入token
const token = computed(() => store.state.token)

const currentRoute = ref("")
onMounted(() => {
  setTimeout(() => {
    currentRoute.value = route.path
  }, 200)
})


const handleSelect = (idx) => {
  currentRoute.value = idx
  router.push(currentRoute.value)
}

watch(() => route.path, (newValue, _oldValue) => {
  currentRoute.value = newValue
})

const logout = () => {
  store.commit("setToken", null)
  router.push("/login")
}

</script>

<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.el-header {
  padding: 0;
}

.el-menu--horizontal>.el-menu-item:nth-child(4) {
  margin-right: auto;
}
</style>
