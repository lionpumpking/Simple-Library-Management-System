import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component:()=>import('../components/login.vue'),
  },
  {
    path: '/Index-web',
    name: 'Index-web',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../components/Index-web.vue'),
    children:[{
      path:'/book',
      name:'book',
      meta:{
        title:'图书管理'
      },
      component:()=>import('../components/todex/bookManger.vue'),
    },
      {
      path:'/userMange',
      name:'userMange',
      meta:{
        title:'用户管理'
      },
      component:()=>import('../components/Main.vue'),
    },
{
path:'/user',
    name:'user',
    meta: {
      title: '个人管理'
},
component:()=>import('../components/Home.vue'),
}
      ],
  },


    {
    path: '/Home',
    name: 'Home',

    component: () => import('../components/Home.vue'),
  }


]

const router = new VueRouter({
  mode: 'history',
  routes
})

const VueRouterPush=VueRouter.prototype.push
VueRouter.prototype.push=function push(to) {
  return VueRouterPush.call(this, to).catch(err => err)
}
export default router
