<script >

import Hander from "@/components/Hander.vue";
import Main from "@/components/Main.vue";
import Aside from "@/components/Aside.vue";
import {isCollapse} from "@/assets/globalvar";


export default {
  components:{
    Aside,
    Hander,
    Main
  },
  data(){
    return {
      isCollapse:false,
      aside_width:'200px',
      icon:'el-icon-s-fold'
    }
  },
  created() {
    //this.$router.push('/user')
  },
  methods:{
    doCollapse(){
        this.isCollapse=!this.isCollapse
          if(!this.isCollapse){
            this.aside_width="200px"
            this.icon='el-icon-s-fold'
      }else{
            this.aside_width="64px"
            this.icon='el-icon-s-unfold'
          }
    }
  },

  Create() {
    const userInfo=JSON.parse(localStorage.getItem("curUser"));
  }
}

</script>

<template>
  <el-container style="height: 100%; border: 1px solid #eee;">
    <el-aside :width="aside_width" style="background-color: rgb(238, 241, 246);height: 100%;">
      <Aside :isCollapse="isCollapse"></Aside>
    </el-aside>

    <el-container type="height: 100%">
      <el-header style="text-align: right; font-size: 12px;border-bottom: #bfbbbb 1px solid;">
        <Hander @doCollapse="doCollapse" :icon="icon"></Hander>
      </el-header>

      <el-main type="height: 100%" style="overflow-y: scroll;" >
<!--        <Main></Main>-->
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped lang="scss">
.el-header {
  #background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  .el-main{
    padding: 5px;
  }
  color: #333;
}
</style>