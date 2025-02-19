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

export const getOneStockInfo = async (stockInfo) => {
  return await fetchData('/stock/getOneStockInfo', stockInfo);
};

export const getStockInfoByIndustry = async (stockInfo) => {
  return await fetchData('/stock/getStockInfoByIndustry', stockInfo);
};

// 获取股票日线数据
export const getStockDailyData = async (stockInfo) => {
  return await fetchData('/stock/getStockDailyData', stockInfo);
};

// 获取指数日线数据
export const getNewIndexData = async (stockInfo) => {
  return await fetchData('/stock/getNewIndexData', stockInfo);
};

// 获取板块信息
export const getSectorInfo = async () => {
  return await fetchData('/stock/getSectorInfo');
};

// 获取所有指数数据
export const getAllIndexData = async () => {
  return await fetchData('/stock/getAllIndexData');
};

// 获取所有股票信息
export const getAllStockInfo = async () => {
  return await fetchData('/stock/getAllStockInfo');
};

// 获取任务列表
export const listTasks = async () => {
  return await fetchData('/flask/list_tasks');
};

// 删除任务
export const deleteTask = async (id) => {
  return await fetchData('/flask/delete_task', { id });
};

// 添加任务
export const addTask = async (task) => {
  return await fetchData('/flask/add_task', task);
};

// 获取股票预测数据
export const getLinearPredictionByTsCode = async (stockInfo) => {
  return await fetchData('/stock/getLinearPredictionByTsCode', stockInfo);
};
