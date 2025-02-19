<template>
  <router-view></router-view>
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
