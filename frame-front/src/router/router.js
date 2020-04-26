import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home.vue'
import Login from '@/views/Login.vue'
import NotFound from '@/views/404.vue'
//import SysIntroduce from '@/views/introduce/SysIntroduce.vue'

Vue.use(Router);

const commonRoutes = [
  {
    path: '/',
    name: 'login',
    meta: { title: '登录' },
    component: () => import('../views/Login.vue'),
  },
  {
    path: '/404',
    name: '404',
    meta: { title: '404' },
    component: () => import('../views/404.vue'),
  },
  { path: '/', redirect: '/home' },
]

// 本地所有的页面 需要配合后台返回的数据生成页面
export const asyncRoutes = {
  home: {
    path: 'SysIntroduce',
    name: 'SysIntroduce',
    meta: { title: '主页' },
    component: () => import('../views/introduce/SysIntroduce.vue'),
  }
};

const createRouter = () => new Router({
  routes: commonRoutes,
});

const router = createRouter();

export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher
}

export default router
