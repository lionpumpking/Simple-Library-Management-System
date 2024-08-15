<script >
export default {
  name: "Main",
  data() {

    return {
      user: JSON.parse(sessionStorage.getItem("CurUser1")),
      user9: JSON.parse(localStorage.getItem("curUser")),
      tableData: [],
      name: '',
      dialogVisible: false,
      dialogVisible1 :false,
      flag: 0,
      flag1:true,
      flag2:true,
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
        bookname:'',
        book_author:'',
        number:'',
      },
      pageNum: 1,
      pageSize: 5,
      total: 0,
      dialogVisible2:false
    }
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.flag2=false;
      if (this.flag1===true){
        this.loadGet()
      }
      else this.loadGetbyname()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.flag2=false;
      if (this.flag1===true){
        this.loadGet()
      }
      else this.loadGetbyname()
    },


    loadGet() {
      this.flag1=true;
      if (this.flag2===true){
        this.pageNum=1;
      }
      this.flag2=true;
      this.$axios.get(this.$httpurl + '/api/listbookP',{
            params: {
              pagesize:this.pageSize,
              pagenum:this.pageNum
            }
          }
      ).then(res => res.data).then(res => {
       // console.log(res)
        this.tableData = res.records
        this.total = res.total
        this.pageNum = res.current
        this.pageSize = res.size

      })
    },

    add() {
      this.dialogVisible = true
    },

    loadGetbyname() {
      this.flag1=false;
      if (this.flag2===true){
        this.pageNum=1;
      }
      this.flag2=true;
      if (this.name === '') {
        this.$message.error("请输入需要搜索的书名")
        return
      }
      this.$axios.get(this.$httpurl + '/api/search', {
        params: {
          pagenum:this.pageNum,
          pagesize:this.pageSize,
          bookname: this.name
        }}).then(res=>res.data.data).then(res => {
        console.log(res)
        if (res.total===0) this.$message.error("书籍不存在")
        else if (res.records[0].bookname !== null) {
          this.tableData = res.records
          this.total = res.total
          this.pageNum = res.current
          this.pageSize = res.size
        }
      })
    },

    set(index,row){
      //console.log(index,row);
      this.form.roleid=row.roleid
      this.form.isValid=row.isValid
      this.dialogVisible1 = true
      sessionStorage.setItem("CurUser1",JSON.stringify(row))

    },

    deletebook(){
      //this.user=JSON.parse(this.user)
      //this.user=JSON.parse(this.user)
      //console.log(this.user)
      this.user= JSON.parse(sessionStorage.getItem("CurUser1"))
      this.$axios.get(this.$httpurl+'/api/deletebook',{
        params:{id:this.user.id}
      }).then(res=>res.data).then(res=>{
      //    console.log(res)
        if(res.msg=== 'false'){
          this.$message.info("该书未全部归还，无法删除")
        }else this.$message.success("删除成功")
        this.dialogVisible1=false;
        this.flag2=false
        this.loadGet()
      })
    },

    save() {
      this.$axios.post(this.$httpurl + '/api/add',
          {

                  bookname:this.book.bookname,
                  bookAuthor:this.book.book_author,
                  number:this.book.number,
                  restnumber:this.book.number,

          }).then(res => res.data).then(res => res.data).then(res => {
        //console.log(res)
        if(res===true){
          this.$message.success("成功增加书的数量")
        }
        else this.$message.success("成功增加新书")
        this.dialogVisible = false
        this.loadGet()
      })
    },

    borrow(row){
     // console.log(row)
      var formData = new FormData();
      formData.append('Bookname',row.bookname);
       formData.append('BookAuthor', row.bookAuthor);
      formData.append('username', this.user9.username);
      this.$axios.post(this.$httpurl+'/api/borrow',formData
      ).then(res => {
       //   console.log(res)
        this.$message.success("借书成功")
      })
    },

  },



  beforeMount() {
    this.loadGet()
    if(this.user9.roleid!==0){
      this.dialogVisible2=true
    }
  }

}

</script>

<template >
  <div>
    <div>
      <div>
        <el-input v-model="name" spellcheck="false" placeholder="请输入名字(模糊查询)" suffix-icon="el-icon-search"
                  @keyup.enter.native="loadGetbyname" style="width:50%;"
        ></el-input>
        <el-button type="primary" @click.native="loadGetbyname" style="margin-left: 5px">查询</el-button>
        <el-button type="success" @click.native="loadGet" style="margin-left: 5px">重置</el-button>
        <el-button type="danger" v-show="dialogVisible2" @click.native="add" style="margin-left: 5px">新增</el-button>
      </div>
      <el-table :data="tableData"
                :header-cell-style="{background: '#f2f5fc',color:'#555'}"
      >
        <el-table-column prop="id" label="ID" width="60">
<!--          <template slot-scope="scope">-->
<!--            <i class="el-icon-time"></i>-->
<!--            <span style="margin-left: 10px">{{ scope.row.date }}</span>-->
<!--          </template>-->
        </el-table-column>
        <el-table-column prop="bookname" label="书名" width="100">
        </el-table-column>
        <el-table-column prop="bookAuthor" label="作者" width="100">
        </el-table-column>
        <el-table-column prop="number" label="总数" width="100">
        </el-table-column>
        <el-table-column prop="restnumber" label="剩余数量" width="100">
        </el-table-column>
        <el-table-column prop="operate" label="操作" >
          <template slot-scope="scope">
            <!--          <el-button type="success">编辑</el-button>-->
            <el-button type="danger"  @click="borrow(scope.row)">借 阅</el-button>
            <el-button type="danger" v-show="dialogVisible2"  @click="set(scope.$index,scope.row)">删 除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog
          title="提示"
          :visible.sync="dialogVisible"
          width="30%">
        <el-form ref="form" :model="book" label-width="80px">
          <el-form-item label="书 名:">
            <el-col :span="20">
              <el-input v-model="book.bookname"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="作 者:">
            <el-col :span="20">
              <el-input v-model="book.book_author"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="数 量:">
            <el-col :span="20">
              <el-input @keyup.enter.native="save" v-model="book.number"></el-input>
            </el-col>
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
        <span>是否确定删除该书</span>
<!--        <el-form ref="form" :model="form" label-width="80px">-->

<!--          <el-form-item label="审核">-->
<!--            <el-radio-group v-model="form.isValid">-->
<!--              <el-radio :label="1">审核通过</el-radio>-->
<!--              <el-radio :label="0">待审核</el-radio>-->
<!--            </el-radio-group>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="身份">-->
<!--            <el-radio-group v-model="form.roleid">-->
<!--              <el-radio :label="1">管理员</el-radio>-->
<!--              <el-radio :label="0">普通用户</el-radio>-->
<!--            </el-radio-group>-->
<!--          </el-form-item>-->
<!--        </el-form>-->
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible1 = false">取 消</el-button>
    <el-button type="primary"@click="deletebook">确 定</el-button>
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