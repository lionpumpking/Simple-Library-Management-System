import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    menu:[]
  },
  getters: {
    getMenu(state){
      return state.menu
    }
  },
  mutations: {
    setMenu(state,menuList){
      state.menu = menuList
    },
    updateName(state, name){
      state.name = name
    }
  },
  actions: {
    addNasync(context){
      setTimeout(()=>{
        context.commit('add')
      },1000)
    }
  },
  modules: {
  }
})
