
<script>
import Main from "@/components/Main.vue";
import Aside from "@/components/Aside.vue";
import Hander from "@/components/Hander.vue";

export default {
  components: {Hander, Aside, Main},
  data() {
    return {
      user: JSON.parse(localStorage.getItem("curUser")),
      book: [],
      dataForm: {
        username: '',
        name: '',
        age: '',
        sex: ''
      },
      dataForm1: {
        username: '',
        name: '',
        age: '',
        sex: ''
      },
      icon: 'el-icon-back',
      show2:false,
      show1:true,
    }
  },
  beforeMount(){
    this.dataForm.name=this.userInfo.name
    this.dataForm.username=this.userInfo.username
    this.dataForm.age=this.userInfo.age
    this.dataForm.sex=this.userInfo.sex
    this.dataForm1.name=this.userInfo.name
    this.dataForm1.username=this.userInfo.username
    this.dataForm1.age=this.userInfo.age
    this.dataForm1.sex=this.userInfo.sex
   // console.log(this.book[0])
    //console.log(this.dataForm)
    this.$axios.get(this.$httpurl + '/api/userbook',).then(res=>res.data).then(res=>{
      //console.log(res)
      for(var i=0;i<res.length;i++){
        if(res[i].id == this.user.id){
          this.book=(res[i].book)
           console.log(this.book)
        }
      }
    })
  },
    created() {
         this.userInfo=JSON.parse(localStorage.getItem("curUser"));
    },

  methods:{
      loadInfo(obj){
        obj.dataForm.name=obj.userInfo.name
        obj.dataForm.username=obj.userInfo.username
        obj.dataForm.age=obj.userInfo.age
        obj.dataForm.sex=obj.userInfo.sex
        obj.dataForm1.name=obj.userInfo.name
        obj.dataForm1.username=obj.userInfo.username
        obj.dataForm1.age=obj.userInfo.age
        obj.dataForm1.sex=obj.userInfo.sex
        //console.log(obj.userInfo.age)
      },
    valid(){
        this.show1=!this.show1;
            this.show2=!this.show2;
    },

    suff(obj){
      obj.$axios.get(obj.$httpurl+'/api/byusername',{
        params:{username:obj.dataForm1.username}
      }).then(res=>res.data.data).then(res=>{
        localStorage.setItem("curUser",JSON.stringify(res[0]))
        obj.userInfo=JSON.parse(localStorage.getItem("curUser"))
        obj.$options.methods.loadInfo(obj)

      })
    },

      mod(){
            this.$axios.post(this.$httpurl+'/api/mod',{
                id:this.user.id,
                username:this.dataForm1.username,
                name:this.dataForm1.name,
                sex:this.dataForm1.sex,
                age:this.dataForm1.age,
              }).then(res => {
              this.$options.methods.suff(this)
              sessionStorage.setItem("CurUser",JSON.stringify(localStorage.getItem("curUser")))
              this.$message.success("修改成功")
              this.$options.methods.loadInfo(this)

            })
      },

    returnBook(row){

      var formData = new FormData();
      console.log( this.user.username);
      formData.append('username', this.user.username);
      formData.append('bookname', row.book);
      formData.append('book_author', row.author);
        this.$axios.post(this.$httpurl+'/api/returnBook',formData).then(res=>{
          this.$message.success("还书成功")
          this.$router.go(0)
        })
    },

    },
}
</script>

<template>
  <el-container style="height: 100%; border: 1px solid #eee;">
<!--      <el-header style="text-align: right; font-size: 12px;border-bottom: #bfbbbb 1px solid;">-->
<!--        <div style="display: flex;line-height: 60px;">-->

<!--          <div >-->
<!--            <i :class="icon" style="font-size: 20px" @click="back"></i>-->
<!--          </div>-->
<!--          <div style="flex: 1; text-align: center;font-size: 24px;">-->
<!--            <span>欢迎使用</span>-->
<!--          </div>-->
<!--          <span style="font-size: 15px;">{{this.user.name}}</span>-->

<!--          <el-dropdown>-->
<!--            <i class="el-icon-setting" style="margin-left: 8px; font-size: 15px"></i>-->
<!--            <el-dropdown-menu slot="dropdown">-->
<!--              <el-dropdown-item @click.native="touser">个人资料</el-dropdown-item>-->
<!--              <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>-->
<!--            </el-dropdown-menu>-->
<!--          </el-dropdown>-->

<!--        </div>-->
<!--      </el-header>-->

      <el-main type="height: 100%" style="overflow-y: scroll;" >
        <div>
          <el-row :gutter="20" style="margin-top:10px;">
            <el-col :span="5">
              <div class="grid-content bg-purple">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>个人中心</span>
                  </div>
                  <div class="register-info">
                    <span class="relation-item">姓名：{{this.user.name}}</span>
                  </div>
                  <div class="register-info" style="margin-top: 10px">
                    <span class="relation-item" >用户权限：{{this.user.roleid ===  2 ?'超级管理员' :this.user.roleid ===  1 ?'管理员' : '普通用户' }}</span>
                  </div>
                  <div class="register-info" style="margin-top: 10px">
                    <span class="relation-item">账户状态：{{this.user.isValid === 1 ? '审核通过' : '待审核'}}</span>
                  </div>
                  <el-divider></el-divider>
                  <div class="personal-relation">
                    <div class="relation-item">账号:  <div style="float: right; padding-right:20px;">{{this.dataForm.username}}</div></div>
                  </div>
                  <div class="personal-relation">
                    <div class="relation-item">年龄:  <div style="float: right; padding-right:20px;">{{this.dataForm.age}}</div></div>
                  </div>
                  <div class="personal-relation">
                    <div class="relation-item">性别:  <div style="float: right; padding-right:20px;">{{this.dataForm.sex=== 1 ? '男' : '女'}}</div></div>
                  </div>
                </el-card>
              </div>
            </el-col>
            <el-col :span="19">
              <div class="grid-content bg-purple">

                <el-card  v-show="show1" class="box-card">
                  <div slot="header" class="clearfix">
                    <span>借阅的书籍</span>
                  </div>
                  <div style="margin-left: 100px">
                    <el-table
                        :data="this.book"
                        empty-text="恭喜您，所有书都已归还"
                        style="width: 50%">
                      <el-table-column prop="book" label="书名" width="180">
<!--                        <template slot-scope="scope">-->
<!--                          </template>-->
                      </el-table-column>
                      <el-table-column prop="author" label="作者">
                      </el-table-column>
                      <el-table-column prop="operate" label="操作" >
                        <template slot-scope="scope">
                          <!--          <el-button type="success">编辑</el-button>-->
                          <el-button @click="returnBook(scope.row)" type="danger" >还 书</el-button>
                        </template>
                      </el-table-column>
                    </el-table>

                    <el-button style="margin-left: 75%" @click="valid" size="mini" type="primary">修改资料</el-button>
                  </div>
                </el-card>


                <el-card v-show="show2" class="box-card">
                  <div slot="header" class="clearfix">
                    <span>修改基本资料</span>
                  </div>
                  <div>
                    <el-form label-width="80px" v-model="dataForm1" size="small" label-position="right">
                      <el-form-item label="*姓名" prop="nickName">
                        <el-input   auto-complete="off" v-model="dataForm1.name"></el-input>
                      </el-form-item>
                      <el-form-item label="*账号" prop="phone">
                        <el-input  auto-complete="off" v-model="dataForm1.username"></el-input>
                      </el-form-item>
                      <el-form-item label="年龄" prop="homeUrl">
                        <el-input   maxlength="18" v-model="dataForm1.age"></el-input>
                      </el-form-item>
                      <el-form-item label="性别" prop="homeUrl">
                        <el-radio-group v-model="dataForm1.sex">
                          <el-radio-button label="1">男</el-radio-button>
                          <el-radio-button label="0">女</el-radio-button>
                        </el-radio-group>
                      </el-form-item>
                    </el-form>
                    <div style="margin-top: -10px">  <span style="font-size: 10px;margin-left: 3%;">提示：带*号的为必填项</span></div>
                    <div slot="footer" class="dialog-footer">
                      <el-button style="margin-left: 80%" @click="valid" size="mini" type="primary">返 回</el-button>
                      <el-button style="margin-left: 5%" @click="mod" size="mini" type="primary">修 改</el-button>
                      <!--                      <el-button @click="back" size="mini" type="warning" >关 闭</el-button>-->
                    </div>
                  </div>
                </el-card>
              </div>
            </el-col>

          </el-row>
        </div>
      </el-main>
    </el-container>

</template>


<style lang="scss" scoped>
//卡片样式
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
}
//文本样式区
.name-role {
  font-size: 16px;
  padding: 5px;
  text-align:center;
}
.sender{
  text-align:center;
}
.registe-info{
  text-align: center;
  padding-top:10px;
}
.personal-relation {
  font-size: 16px;
  padding: 0 5px 15px;
  margin-right: 1px;
  width: 100%
}

.relation-item {
  padding: 12px;

}
.dialog-footer{
  padding-top:10px ;
  padding-left: 10%;
}
//布局样式区
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
