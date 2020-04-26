import Vue from 'vue';
import App from './App';
import router from './router/router';
import api from './https';
import il8n from "./il8n";  // 引入国家化多语言
import global from "./utils/global";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.min.css';  // 引入第三方图标库(font awesome)
import './styles/main.css';
import store from './store/index';
import VueRouter from 'vue-router';
import createRoutes from './utils/createRoutes'
import { getDocumentTitle, resetTokenAndClearUser } from './utils';
import { LoadingBar } from 'view-design';

Vue.use(VueRouter);
Vue.use(ElementUI);  // 注册使用Element
Vue.use(api);  // 注册使用API模块

Vue.prototype.global = global;  // 挂载全局配置模块

Vue.config.productionTip = false;

// 登录守卫
// 是否有菜单数据
let hasMenus = false;
router.beforeEach((to, from, next) => {
  debugger;
  document.title = getDocumentTitle(to.meta.title);
  LoadingBar.start();
  if(to.path === '/') {
    hasMenus = false;
    next();
  } else {
    let token = localStorage.getItem('Authorization');
    if(token === null || token === '') {
      next('/');
    } else if (hasMenus) {
      next();
    } else {
      try {
        // 这里可以用 await 配合请求后台数据来生成路由
        // const data = await axios.get('xxx')
        // const routes = createRoutes(data)
        const routes = createRoutes(store.state.menuItems);
        // 动态添加路由
        router.addRoutes(routes);
        hasMenus = true;
        next({ path: to.path || '/' })
      } catch (error) {
        resetTokenAndClearUser();
        next(`/login?redirect=${to.path}`)
      }
    }
  }
});

router.afterEach(() => {
  LoadingBar.finish()
});

new Vue({
  el: '#app',
  il8n,
  router,
  store,
  render: h => h(App)
});
