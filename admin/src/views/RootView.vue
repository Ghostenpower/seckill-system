<template>
  <div class="common-layout">
    <el-container style="min-height: 100vh;">
      <el-header>
        <el-menu :default-active="activeIndex" mode="horizontal" @select="handleSelect">
          <el-menu-item index="/user/list">Processing Center</el-menu-item>
        </el-menu>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu style="min-height: 100%;" default-active="2" @select="handleSelect">
            <el-menu-item index="/user/list">用户列表</el-menu-item>
            <el-menu-item index="/product/list">商品列表</el-menu-item>
            <el-menu-item index="/flash/list">秒杀列表</el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStore } from 'vuex'

const activeIndex = ref('1')
const activeIndex2 = ref('1')

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
