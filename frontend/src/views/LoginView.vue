<template>
  <div class="login">
    <div id="tsparticles" class="login__particles"></div>

    <div class="loginPart">
      <h2>用户登录</h2>
      <el-form label-width="100px" style="transform: translate(-30px)">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="username" placeholder="请输入用户名" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="password" placeholder="请输入密码" show-password clearable></el-input>
        </el-form-item>
        <el-button class="btn" type="primary" @click="login" auto-insert-space @keyup.enter="login">登录</el-button>
        <div style="text-align: right; transform: translate(0, 30px)">
          <!-- <el-link type="danger" @click="changeUrl('/forget')" style="margin-right: 140px">
            忘记密码？
          </el-link> -->
          <el-link type="primary" @click="router.push('/register')">没有账号？去注册</el-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { getLogin } from '../axios/user';
import { msgError, msgSuccess, msgWarning } from '@/utils/message';

// #region 路由
const router = useRouter();
const route = useRoute();
// #endregion

// #region 仓库
const store = useStore();
// #endregion

// #region 登录属性和方法
const username = ref('');
const password = ref('');
const login = () => {
  try {
    getLogin({ username: username.value, password: password.value }).then(response => {

      if (response.code === 1) {
        store.commit('setUser', { user_id: response.data.user_id, username: response.data.username });
        store.commit('setToken', response.data.token);
        localStorage.setItem('user', JSON.stringify({ user_id: response.data.user_id, username: response.data.username }));
        localStorage.setItem('token', response.data.token);
        router.push('/');
        msgSuccess('登录成功')
      } else {
        msgError(response.msg)
      }
    });
  } catch (error) {
    alert('登录失败');
  }
};
// #endregion

// #region 参数获取
const isRedirect = route.query.isRedirect;
// #endregion

onMounted(() => {

});
</script>


<style lang="scss" scoped>
.login {
  height: 100%;
  width: 100%;
  overflow: hidden;
}

.login__particles {
  height: 100%;
  width: 100%;
  background-size: cover;
  background-repeat: no-repeat;
  background-image:
    linear-gradient(to right, rgba(255, 255, 255, 0) 50%, rgb(33, 58, 78) 100%),
    url("@/assets/images/loginbg.png");
  opacity: 0.9;
  position: fixed;
  pointer-events: none;
}

.loginPart {
  position: absolute;
  /*定位方式绝对定位absolute*/
  top: 50%;
  left: 80%;
  /*顶和高同时设置50%实现的是同时水平垂直居中效果*/
  transform: translate(-50%, -50%);
  /*实现块元素百分比下居中*/
  width: 450px;
  padding: 50px;
  background: rgba(0, 0, 0, 0.3);
  /*背景颜色为黑色，透明度为0.8*/
  box-sizing: border-box;
  /*box-sizing设置盒子模型的解析模式为怪异盒模型，
    将border和padding划归到width范围内*/
  box-shadow: 0px 15px 25px rgba(0, 0, 0, 0.5);
  /*边框阴影  水平阴影0 垂直阴影15px 模糊25px 颜色黑色透明度0.5*/
  border-radius: 15px;
  /*边框圆角，四个角均为15px*/
}

h2 {
  margin: 0 0 30px;
  padding: 0;
  color: #fff;
  text-align: center;
  /*文字居中*/
}

.btn {
  transform: translate(170px);
  width: 80px;
  height: 40px;
  font-size: 15px;
}
</style>