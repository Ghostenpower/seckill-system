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

export const getCartByName = async (data) => {
  return await fetchData('/cart/getCartByName', data);
};

export const submit = async (data) => {
  return await fetchData('/cart/submit', data);
};

export const addToCart = async (data) => {
  return await fetchData('/cart/add', data);
};

export const deleteSelected = async (data) => {
  return await fetchData('/cart/deleteCartFromList', data);
};
