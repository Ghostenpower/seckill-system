// src/utils/message.js

import { ElMessage } from 'element-plus';

const msgError = (msg, type = 'error') => {
  ElMessage({
    message: msg || 'Oops, this is an error message.',
    type: type,
    plain: true,
  });
};

// 其他类型的消息封装，可以根据需要添加更多函数
const msgSuccess = (msg) => {
  ElMessage({
    message: msg || 'Operation successful!',
    type: 'success',
    plain: true,
  });
};

const msgWarning = (msg) => {
  ElMessage({
    message: msg || 'This is a warning message.',
    type: 'warning',
    plain: true,
  });
};

export { msgError, msgSuccess, msgWarning };
