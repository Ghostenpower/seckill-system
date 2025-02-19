// store.js
import Vuex from 'vuex';

const store = new Vuex.Store({
  state: {
    // 试图从 localStorage 恢复数据，如果没有，则使用默认值
    user: JSON.parse(localStorage.getItem('user')) || null,
    token: localStorage.getItem('token') || null,
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
      // 保存到 localStorage
      localStorage.setItem('user', JSON.stringify(user));
    },
    setToken(state, token) {
      state.token = token;
    }
  },
  actions: {
    // 可以在这里进行异步操作
    updateUser({ commit }, user) {
      commit('setUser', user);
    },
  }
});

export default store;