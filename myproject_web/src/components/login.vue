<script>

export default {
  name: "Login",
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      form1:{
        username:'',
        name:'',
        password:'',
        sex:'',
        age:'',
        roleid:''
      },

      formLabelWidth: '120px',
      // 表单验证，需要在 el-form-item 元素中增加 prop 属性
      rules: {
        username: [
          {required: true, message: '账号不可为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不可为空', trigger: 'blur'}
        ]
      },
      // 对话框显示和隐藏
      dialogVisible:false,
      dialogVisible1:false,
    }
  },
  methods: {
    onSubmit(formName) {
      // 为表单绑定验证功能
      if(this.form.username === ''||this.form.password === ''){
        this.$message.info("账号和密码不可为空")
        return
      }

      var formData = new FormData();
      formData.append('username', this.form.username);
      formData.append('password', this.form.password);
      this.$axios.post(this.$httpurl + '/api/login', formData).then(res=>res.data).then(res => {
        if (res.code === 2000&&res.data.user.isValid=== 1) {
          // 使用 vue-router 路由到指定页面，该方式称之为编程式导航
          this.$message.success("登录成功");
          ws.send(this.form.username+"登录成功")
          console.log(res.data.user.name)
          this.userInfo.name=res.data.user.name
          this.userInfo.username=res.data.user.username
          this.userInfo.id=res.data.user.id
          this.userInfo.age=res.data.user.age
          this.userInfo.sex=res.data.user.sex
          sessionStorage.setItem("CurUser",JSON.stringify(res.data.user))
          localStorage.setItem("curUser",JSON.stringify(res.data.user))
          this.$store.commit("setMenu",res.data.menu)
          localStorage.setItem("curMenu",JSON.stringify(res.data.menu))
          this.$router.push("/Index-web");
        }
        else if(res.code === 4000){
          //this.dialogVisible = true;
          this.$message.warning("账号或密码不正确")
          return false;
        }
        else if(res.data.user.isValid=== 0){
          this.$message.warning("账户待审核")
        }
      })
    },
    enroll(){
      this.dialogVisible1=true
    },




    upuser(){
      this.$axios.get(this.$httpurl + '/api/byusername',
          {
            params:
                {
                  username: this.form1.username
                }
          }).then(res => res.data).then(res => res.data).then(res => {
        if (res[0].username !== null) {
          this.$message.info("账号已被使用")
        } else {
          this.$axios.post(this.$httpurl + '/api/save', this.form1).then(res => {
            if (res.data === true)
              this.$message.success("注册成功")
            Object.keys(this.form1).forEach(key => (this.form1[key] = ''));
            this.dialogVisible1 = false
          })
        }
      })

    }
  }
}

let id="用户端"
const ws = new WebSocket(`ws://localhost:8087/ws/${id}`);
ws.addEventListener("open",(Event)=>{
  //console.log(evevt);
  console.log("WebSocket连接成功")
})

ws.addEventListener("close",(event)=>{
  console.log("WebSocket连接关闭")
})

ws.addEventListener("message",(event)=>{
  console.log("登录成功")
})



</script>

<template>
  <div>
    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="账号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input @keyup.enter.native="onSubmit('loginForm')" type="password" placeholder="请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item style="">
      <el-button-group >
        <el-button type="primary" style="margin-left: 15px;" v-on:click="enroll('loginForm')">注 册</el-button>
        <el-button type="primary"  style="margin-left: 130px;"v-on:click="onSubmit('loginForm')">登 录</el-button>
      </el-button-group>
      </el-form-item>
    </el-form>

    <el-dialog>
        title="温馨提示"
        :visible.sync="dialogVisible"
        width="30%"

      <span>请输入账号和密码</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>


    <!-- Form -->
<!--    <el-button type="text" @click="dialogVisible = true">打开嵌套表单的 Dialog</el-button>-->

    <el-dialog title="注册" :visible.sync="dialogVisible1" width="30%">
      <el-form :model="form1">
        <el-form-item label="账号:" :label-width="formLabelWidth">
          <el-input style="width:300px" v-model="form1.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户名:" :label-width="formLabelWidth">
          <el-input style="width:300px" v-model="form1.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item  label="密码:" :label-width="formLabelWidth">
          <el-input type="password" style="width:300px" v-model="form1.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别:" :label-width="formLabelWidth">
          <el-input style="width:300px" v-model="form1.sex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄:" :label-width="formLabelWidth">
          <el-input style="width:300px" v-model="form1.age" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="upuser ">注 册</el-button>
      </div>
    </el-dialog>

<!--    <el-dialog-->
<!--        title="注册"-->
<!--        :visible.sync="dialogVisible1"-->
<!--        width="30%">-->
<!--      &lt;!&ndash;      :before-close="handleClose">&ndash;&gt;-->
<!--        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>-->
<!--      <span slot="footer" class="dialog-footer">-->
<!--    <el-button @click="dialogVisible = false">取 消</el-button>-->
<!--    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>-->
<!--  </span>-->
<!--    </el-dialog>-->
  </div>

</template>

<style lang="scss" scoped>
.login-box {
  border: 1px solid #DCDFE6;
  width: 350px;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}
</style>