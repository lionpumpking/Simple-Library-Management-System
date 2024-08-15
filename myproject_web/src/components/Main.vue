<script >
export default {
  name: "Main",
  data() {

    return {
      user: JSON.parse(sessionStorage.getItem("CurUser1")),
      user9: JSON.parse(localStorage.getItem("curUser")),
      tableData: [],
      name: '',
      flag1:true,
      flag3:true,
      dialogVisible: false,
      dialogVisible1 :false,
      flag: 0,
      user2:'',
      user1:'',
      borrowbook:'',
      borrowbookauthor:'',
      form: {
        username: '',
        name: '',
        password: '',
        age: '',
        sex: '1',
        roleid: '',
        isValid:''
      },
      book:{
        id:'1',
        book:[],
        author:[],
      },
      booklist:[{
        id:'',
        book:[],
      }],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      dialogVisible2:false,
      dialogVisible3:false,

    }
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.flag3=false;
      if (this.flag1===true){
        this.loadGet()
      }
      else this.loadGetbyname()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.flag3=false;
      if (this.flag1===true){
        this.loadGet()
      }
      else this.loadGetbyname()
    },


    loadGet() {
      this.flag1=true;
      if (this.flag3===true){
        this.pageNum=1;
      }
      this.flag3=true;
      this.$axios.get(this.$httpurl + '/api/listP1',{
            params: {
              pagesize:this.pageSize,
              pagenum:this.pageNum
            }
          }
            ).then(res => res.data).then(res => {
        this.tableData = res.records
        this.total = res.total
        this.pageNum = res.current
        this.pageSize = res.size
      })

      this.$axios.get(this.$httpurl + '/api/userbook',).then(res=>res.data).then(res=>{
        this.booklist=res
        //console.log(this.booklist.length)
      })
    },

    add() {
      this.dialogVisible = true
    },
    loadGetbyname() {
      this.flag1=false;
      if (this.flag3===true){
        this.pageNum=1;
      }
      this.flag3=true;
      if (this.name === '') {
        this.$message.error("请输入需要搜索的用户名")
        return
      }
      this.$axios.get(this.$httpurl + '/api/byname', {
        params: {
          name: this.name,
          pagenum:this.pageNum,
          pagesize:this.pageSize,
        }
      }).then(res => res.data.data).then(res => {
        console.log(res)
        if (res.records[0].username !== null) {
          this.tableData = res.records
           this.total = res.total
          this.pageNum = res.current
          this.pageSize = res.size
        } else this.$message.error("用户不存在")
      })
    },

    set(index,row){
        //console.log(index,row);
        this.form.roleid=row.roleid
        this.form.isValid=row.isValid
        this.dialogVisible1 = true
        sessionStorage.setItem("CurUser1",JSON.stringify(row))
        // this.$axios.get(this.$httpurl+'/byusername',{
        //   params:{username:row.username}
        // }).then(res=>res.data.data).then(res=>{
        //   //console.log(res[0])
        //   this.$axios.post(this.$httpurl+'/mod',res[0]).then(res=>{
        //
        //
        //   })
        // })
    },

    modroleandvalid(){
      //this.user=JSON.parse(this.user)
      //this.user=JSON.parse(this.user)
      //console.log(this.user)
      this.user1=JSON.parse(sessionStorage.getItem("CurUser"));
      this.user2=JSON.parse(sessionStorage.getItem("CurUser1"));
      // console.log(this.form.username)
      if(this.user2.username==="000") this.$message.warning("无权限进行此操作")
      if(this.user9.roleid===1&&this.user2.roleid===1) this.$message.warning("无权限进行此操作")
      if(this.user1.roleid===2||(this.user2.roleid===1&&this.form.isValid===0))  {}
      this.$axios.get(this.$httpurl+'/api/byusername',{
        params:{username:this.user2.username},
      }).then(res=>res.data.data).then(res=>res[0]).then(res=>{
        res.isValid=this.form.isValid
        if(this.user1.roleid===2){
        res.roleid=this.form.roleid}
        //console.log(res)
        this.$axios.post(this.$httpurl+'/api/mod1',res).then(res=>{
        console.log(res.data)
          this.dialogVisible1=false
          this.$axios.get(this.$httpurl + '/api/listP1',{
                params: {
                  pagesize:this.pageSize,
                  pagenum:this.pageNum
                }
              }
          ).then(res => res.data).then(res => {
            this.tableData = res.records
            this.total = res.total
            this.pageNum = res.current
            this.pageSize = res.size

          })
        })
      })
    },

    loadbook(){
      console.log(9)
    },


    save() {
      this.$axios.get(this.$httpurl + '/api/byusername',
          {
            params:
                {
                  username: this.form.username
                }
          }).then(res => res.data).then(res => res.data).then(res => {
        console.log(res)
        if (res[0].username !== null) {
          this.$message.info("账号已被使用")
        } else {
          this.$axios.post(this.$httpurl + '/api/save', this.form).then(res => {
            console.log(res)
            if (res.data === true)
              this.$message.success("注册成功")
            this.dialogVisible = false
          })
        }
      })
    },
  },
    beforeMount() {
     // console.log('userInfo:'+this.$global.userInfo)
      this.loadGet()
      if(this.user9.roleid===2){
        this.dialogVisible3=true
      }

    }

}

</script>

<template >
  <div>
  <div>
          <div>
            <el-input v-model="name" spellcheck="false" placeholder="请输入名字" suffix-icon="el-icon-search"
                     @keyup.enter.native="loadGetbyname" style="width:50%;"
            ></el-input>
            <el-button type="primary" @click.native="loadGetbyname" style="margin-left: 5px">查询</el-button>
            <el-button type="success" @click.native="loadGet" style="margin-left: 5px">重置</el-button>
            <el-button type="danger"  @click.native="add" style="margin-left: 5px">新增</el-button>
          </div>
      <el-table :data="tableData"
      :header-cell-style="{background: '#f2f5fc',color:'#555'}"
                @expend-change="loadbook"
      >

        <el-table-column type="expand"   width="60" style="margin-left: 50px">

                    <template   slot-scope="scope">
<!--                      <span style="margin-left: 10px">{{ scope.row.borrowbook}}</span>-->
                      <template>
                        <div style="margin-left: 100px">
                        <el-table
                            :data="booklist[scope.$index].book"
                            style="width: 50%">
                          <el-table-column prop="book" label="书名" width="180">
                          </el-table-column>
                          <el-table-column prop="author" label="作者">
                          </el-table-column>
                        </el-table>
                        </div>
                      </template>
                    </template>
        </el-table-column>
        <el-table-column prop="id" label="ID" width="60">
        </el-table-column>
        <el-table-column prop="username" label="账号" width="100">
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="100">
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="100">
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="100">
          <template slot-scope="scope">
            <el-tag
                :type="scope.row.sex === 1 ? 'primary' : 'success'"
                disable-transitions>{{scope.row.sex === 1 ? '男' : '女'}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="roleid" label="角色" width="120">
          <template slot-scope="scope">
            <el-tag
                :type="scope.row.roleid ===  2 ?'danger' :scope.row.roleid ===  1 ?'primary' : 'success'"
                disable-transitions>{{scope.row.roleid ===  2 ?'超级管理员' :scope.row.roleid ===  1 ?'管理员' : '普通用户' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="isValid" label="有效" width="100">
          <template slot-scope="scope">
            <el-tag
                disable-transitions>{{scope.row.isValid === 1 ? '审核通过' : '待审核'}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="operate" label="操作" >
          <template slot-scope="scope">
<!--          <el-button type="success">编辑</el-button>-->
          <el-button type="danger"  @click="set(scope.$index,scope.row)">设置</el-button>
          </template>
        </el-table-column>
      </el-table>

    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="账号">
          <el-col :span="20">
          <el-input v-model="form.username"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="名字">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码">
          <el-col :span="20">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄">
          <el-col :span="20">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary"@click="save">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
        title="设置"
        :visible.sync="dialogVisible1"
        width="30%" >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="审核">
          <el-radio-group  v-model="form.isValid">
            <el-radio :label="1">审核通过</el-radio>
            <el-radio :label="0">待审核</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-show="dialogVisible3" label="身份">
          <el-radio-group  v-model="form.roleid">
            <el-radio :label="1">管理员</el-radio>
            <el-radio :label="0">普通用户</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible1 = false">取 消</el-button>
    <el-button type="primary"@click="modroleandvalid">确 定</el-button>
  </span>
    </el-dialog>
  </div>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
    </template>

<style scoped lang="scss">

</style>