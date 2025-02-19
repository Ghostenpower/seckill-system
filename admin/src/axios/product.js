import axiosInstance from './index';


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

export const getListData = async (data) => {
  return await fetchData('/product/get',data);
};

export const getByName = async (data) => {
  return await fetchData('/product/getByName',data);
};

export const buyProduct = async (data) => {
  return await fetchData('/product/buy',data);
};

export const edit_product = async (data) => {
  return await fetchData('/product/edit',data);
};

export const deleteProduct = async (data) => {
  return await fetchData('/product/delete',data);
};

export const create_product = async (data) => {
  return await fetchData('/product/create',data);
};