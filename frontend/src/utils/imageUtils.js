// imageUtils.js

// 定义一个包含图片 URL 的数组
const imageUrls = [
  'https://tse1-mm.cn.bing.net/th/id/OIP-C.FHyccU3YbWl1-IqWQDExdwHaEK?rs=1&pid=ImgDetMain',
  'https://tse2-mm.cn.bing.net/th/id/OIP-C.TyzyTbE-7M7MihQxeZdCBwHaEK?rs=1&pid=ImgDetMain',
  'https://tse1-mm.cn.bing.net/th/id/OIP-C.wFM5OBAZ-eMi6aYQD4a0zwHaF7?w=191&h=185&c=7&r=0&o=5&dpr=1.1&pid=1.7',
  'https://tse3-mm.cn.bing.net/th/id/OIP-C.9R3J7pAdC4mfb1odIGglgQHaFj?w=212&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7',
  'https://tse1-mm.cn.bing.net/th/id/OIP-C.qh2GC-6p6Pn6ScJid_xs3gHaEK?w=295&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7',
  'https://tse2-mm.cn.bing.net/th/id/OIP-C.vxJ3G8u6j-UcR7HtFIylUwHaFs?w=212&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7',
  'https://tse2-mm.cn.bing.net/th/id/OIP-C.MwJmFJoE-wypVeOLo2PSzAAAAA?w=192&h=192&c=7&r=0&o=5&dpr=1.1&pid=1.7',
  'https://tse4-mm.cn.bing.net/th/id/OIP-C.EsqORhEcMBlbuu3hJ7k8EgHaFj?w=288&h=216&c=7&r=0&o=5&dpr=1.1&pid=1.7',
  'https://tse3-mm.cn.bing.net/th/id/OIP-C.YxR2s2V__rXXt5YRwf6oDAHaFx?w=241&h=188&c=7&r=0&o=5&dpr=1.1&pid=1.7',
  'https://tse4-mm.cn.bing.net/th/id/OIP-C.hgyIiIr5-muzN3IqRUE2wgHaFP?w=253&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7',
  'https://tse1-mm.cn.bing.net/th/id/OIP-C.WJpVcZ8eJYnd4JGr7tazWgHaEK?w=306&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7',
  'https://tse1-mm.cn.bing.net/th/id/OIP-C.LzGY3G0fHy2O8POTKphvzgHaDL?w=309&h=149&c=7&r=0&o=5&dpr=1.1&pid=1.7',
  'https://tse3-mm.cn.bing.net/th/id/OIP-C.9R6ET3VIovdQ90f_-Z5JFgHaEK?w=275&h=187&c=7&r=0&o=5&dpr=1.1&pid=1.7',
  
];

// 随机获取一张图片的方法
function getRandomImage() {
  const timestamp = new Date().getTime();  // 获取当前时间戳（毫秒）
  const randomIndex = timestamp % imageUrls.length;  // 取余确保索引在数组长度范围内
  return imageUrls[randomIndex];
}

// 导出方法
export { getRandomImage };