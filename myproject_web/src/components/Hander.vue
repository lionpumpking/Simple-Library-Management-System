<script >
export default {
  name:"Hander",
  data(){
    return {
      user:JSON.parse(localStorage.getItem("curUser"))
    }
  },
  props:{
    icon:String
  },
  created()
  {
this.$router.push('user')
  },

  methods:{
    touser(){
      console.log("to_user")
      this.$router.push({path:'/User'})
    },

    logout(){
      console.log('louout')
      this.$confirm('您确定退出登录码？','提示',{
        confirmButtonText:'确定',
        type: 'warning',
        center: true
      }).then(()=>{
        this.$message.success("退出登录成功")
        localStorage.removeItem('curUser')
        this.$router.push('/')
        sessionStorage.clear()
        localStorage.clear()
      })
          .catch(()=>{
            this.$message.info("已取消退出登录")
    })
    },

    collapse(){
      this.$emit('doCollapse')
    }
  },
  beforeMount(){
    //this.$router.push('/Home')
  },

}

</script>
<template>
  <div style="display: flex;line-height: 60px;">

    <div >
      <i :class="icon" style="font-size: 20px" @click="collapse"></i>
    </div>
    <div style="flex: 1; text-align: center;font-size: 24px;">
      <span>欢迎使用</span>
    </div>
    <span style="font-size: 15px;">{{user.name}}</span>

    <el-dropdown trigger="click" >
      <i class="el-icon-setting" style="margin-left: 8px; font-size: 15px"></i>
      <el-dropdown-menu slot="dropdown" >
        <el-dropdown-item @click.native="touser">个人资料</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>
</template>

<style scoped lang="scss">

</style>