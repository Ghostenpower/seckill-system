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

export const getFlashSaleLimit3 = async () => {
  return await fetchData('/flashSale/getFlashSaleLimit3');
};

export const getFlashSaleProductLimit3 = async () => {
  return await fetchData('/flashSale/getFlashSaleProductLimit3');
}

export const getByName = async (data) => {
  return await fetchData('/flashSale/getByName', data);
}

export const FlashSale = async (data) => {
  return await fetchData('/flashSale/flashSale',data);
}

export const initializeFlashSale = async (data) => {
  return await fetchData('/flashSale/initializeFlashSale',data);
}

export const deleteFlashSale = async (data) => {
  return await fetchData('/flashSale/deleteFlashSale',data);
}

