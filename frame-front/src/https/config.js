/*axios默认配置，包含基础路径信息等*/
import {baseUrl} from "../utils/global";
import Cookies from 'js-cookie';

export default {
  method: 'get',
  baseUrl: baseUrl,  //基础url前缀
  headers: {'Content-Type': 'application/json;charset=UTF-8', "token": Cookies.get("token")},  // 请求头信息
  data: {},  // 参数
  timeout: 10000, // 超时时间
  withCredentials: true,  // 是否携带凭证
  responseType: 'json'  // 返回数据类型
}
