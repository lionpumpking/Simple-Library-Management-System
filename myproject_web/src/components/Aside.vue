<script >

export default {
  data(){
    return{
      //isCollapse:false
    }

  },
  computed:{
"menu":{
  get(){
    return this.$store.state.menu
  }
},
  },
  beforeMount(){
    this.load();
  },
  methods:{
    load(){
      this.$store.commit("setMenu",JSON.parse(localStorage.getItem("curMenu")) )
    }
  },
  props:{
    isCollapse:Boolean
  }
}
</script>

<template style="position: fixed; top: 0; left: 0; right: 0; bottom: 0;">
  <el-menu
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      style="height: 100vh;
      position: fixed;"
      default-active="/user"
      :collapse="isCollapse"
      :collapse-transition="false"
      router>
    <el-menu-item index="/user" style="width:200px;height:0px">
      <span slot="title">首页</span>
    </el-menu-item>
      <el-menu-item index="/user">
        <i class="el-icon-s-home"></i>
        <span slot="title">首页</span>
      </el-menu-item>

    <el-menu-item :index="'/'+item.menuclick" v-for="(item,i) in menu" :key="i">
      <i :class="item.menuicon"></i>
      <span slot="title">{{item.menuname}}</span>
    </el-menu-item>

<!--    <el-menu-item index="/userMange">-->
<!--      <i class="el-icon-s-home"></i>-->
<!--      <span slot="title">导航二</span>-->
<!--    </el-menu-item>-->
  </el-menu>
</template>

<style scoped lang="scss">
</style>