/*模拟数据*/
import Mock from 'mockjs'
Mock.mock('http://localhost:8080/user', {
  'name': '@name', // 随机生成姓名
  'email': '@email', // 随机生成邮箱
  'age|1-10': 5  // 年龄在1-10岁之间
});

Mock.mock('http://localhost:8080/getCaptcha', {
  "captcha": "sj8i"
});
