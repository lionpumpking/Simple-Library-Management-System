import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import axios from "axios";
import store from "./store";
Vue.prototype.$axios = axios;
//Vue.prototype.$httpurl='http://36.235.136.28:21149';
Vue.prototype.$httpurl='http://172.20.36.194:8087';
Vue.use(ElementUI,{size:'small'});
Vue.use(VueRouter);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')


// Vue.prototype.$global=common
Vue.prototype.userInfo={
  name:'',
  username:'',
  id:'',
  age:'',
  sex:'',
}


