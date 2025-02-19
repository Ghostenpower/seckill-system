import { createRouter, createWebHistory } from 'vue-router'
import RootView from '../views/RootView.vue'
import AboutView from '@/views/AboutView.vue'
import OrderDetailView from '@/views/OrderDetailView.vue'
import OrderListView from '@/views/OrderListView.vue'
import ProductDetailView from '@/views/ProductDetailView.vue'
import LoginView from '@/views/LoginView.vue'
import store from '@/store'
import { msgError, msgSuccess, msgWarning } from '@/utils/message'
import ProductListView from '@/views/ProductListView.vue'
import HomeView from '@/views/HomeView.vue'
import FlashSaleListView from '@/views/FlashSaleListView.vue'
import MyView from '@/views/MyView.vue'
import UserListView from '@/views/UserListView.vue'

// 模拟检查登录状态的函数（你可以改成实际的逻辑）
function isLoggedIn() {
  // 假设从 localStorage 获取登录状态
  return store.state.token !== null;
}

// 通用的路由守卫，用来检查用户是否登录
const requireAuth = (to, from, next) => {
  const token = store.state.token;
  if (!token) {
    next({ name: 'login' });
    msgError('您还没有登录或 Token 已过期，请重新登录！(❁´◡`❁)');
  } else {
    next();
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'root',
      component: RootView,
      beforeEnter: requireAuth,
      children: [
        {
          path: '/',
          name: 'home',
          component:HomeView,
          beforeEnter: requireAuth
        },
        {
          path: '/user/list',
          name: 'user-list',
          component:UserListView,
          beforeEnter: requireAuth
        },
        {
          path: '/about',
          name: 'about',
          component: AboutView,
          beforeEnter: requireAuth
        },
        {
          path: '/order/order-detail',
          name: 'order-detail',
          component: OrderDetailView,
          beforeEnter: requireAuth
        },
        {
          path: '/order/order-list',
          name: 'order-list',
          component: OrderListView,
          beforeEnter: requireAuth
        },
        {
          path: '/product/product-detail',
          name: 'product-detail',
          component: ProductDetailView,
          beforeEnter: requireAuth
        },
        {
          path: '/product/list',
          name: 'product-list',
          component: ProductListView,
          beforeEnter: requireAuth
        },
        {
          path:"/flash/list",
          name:'flash-list',
          component:FlashSaleListView,
          beforeEnter: requireAuth
        },
        {
          path: '/my-info',
          name: 'myInfo',
          component: MyView,
          beforeEnter: requireAuth
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },

  ]
})

export default router;
