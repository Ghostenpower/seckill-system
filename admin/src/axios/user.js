import axiosInstance from './index';
import store from '@/store';

// 公共的 API 请求封装函数
const fetchData = async (url, data = null) => {
  try {
    const response = await axiosInstance.post(url, data);
    return response;  // 返回数据
  } catch (error) {
    // 统一错误处理
    throw new Error(`请求失败：${error.message}`);
  }
};

export const getLogin = async (user) => {
  return await fetchData('/user/login', user);
};

export const getRegister = async (user) => {
  return await fetchData('/user/register', user);
};

export const verifyLogin = async () => {
  return await fetchData('/user/verifyLogin');
};

export const getUserInfo = async () => {
  return await fetchData('/user/getUserInfo');
};

export const getUserList = async (data) => {
  return await fetchData('/user/getUserList',data);
};

export const edit_user = async (data) => {
  return await fetchData('/user/editUser',data);
};

export const adminLogin = async (data) => {
  return await fetchData('/user/adminLogin',data);
};

export const deleteUserById = async (data) => {
  return await fetchData('/user/deleteUserById',data);
};