/**
 * 全局常量、方法封装
 * 通过原型挂载到Vue属性
 * 通过 this.global 调用
 * **/
// 后台管理系统服务器地址
export const baseUrl = 'http://localhost:8080';
// 系统数据备份还原服务器地址
export const backupBaseUrl = 'http://localhost:8002';

export default {
  baseUrl, backupBaseUrl
}
