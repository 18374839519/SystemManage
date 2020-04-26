/*请求接口汇总，聚合所有模块api*/
import axios from "axios";
import qs from 'qs';
import config from "./config";
import router from "../router/router";

let url = config.baseUrl;
//axios.defaults.headers.common["token"] = localStorage.getItem('Authorization'); // 请求头携带token

// 添加请求拦截器，在请求头中加token
axios.interceptors.request.use(
  config => {
    if (localStorage.getItem('Authorization')) {
      config.headers.Authorization = localStorage.getItem('Authorization');
    }

    return config;
  },
  error => {
    return Promise.reject(error);
  });

// http response 拦截器
axios.interceptors.response.use(
  response => {

    return response;
  },
  error => {

    if (error.response) {
      switch (error.response.status) {
        case 401:
          this.$store.commit('del_token');
          router.replace({
            path: '/',
            query: {redirect: router.currentRoute.fullPath}//登录成功后跳入浏览的当前页面
          })
      }
    }
    return Promise.reject(error.response.data)
  });

export default {
  /***************************** 用户登录 ******************************/
  // 获取登录验证码
  getCaptcha: function () {return axios.get(url + "/captcha.jpg")},
  // 登录
  login: function (params) {return axios.post(url + "/login", qs.stringify(params))},

  /***************************** 菜单 ******************************/
  // 查询所有菜单
  getMenus: function () {return axios.get(url + "/menu/getAllMenu")},
  // 添加目录/菜单
  addMenu: function (params) {return axios.post(url + "/menu/insertMenu", qs.stringify(params))},
  // 校验名称是否存在
  checkMenuName: function (params) {return axios.get(url + "/menu/checkMenuName", {params: params})},
  // 删除目录/菜单
  deleteMenu: function (params) {return axios.post(url + "/menu/deleteMenu", qs.stringify(params))},
  // 根据菜单/目录id查询
  selectById: function (params) {return axios.get(url + "/menu/selectById", {params: params})},
  // 根据id跟新
  updateById: function (params) {return axios.post(url + "/menu/updateById", qs.stringify(params))},
}
