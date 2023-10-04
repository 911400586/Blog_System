<template>
  <div class="common-layout">
    <NavBar img="/img/bk.png" :userinfo="userinfo"></NavBar>
    <el-container>
      <el-header>
        <el-container>

        </el-container>
      </el-header>

      <template v-if="items.length">
        <el-scrollbar height="650px">
          <el-container v-for="(item, index) in items">
            <el-aside width="200px"></el-aside>
            <el-container>

              <el-main style="height: 350px;">

                <MyBlog :Blog="item" :index="index" @delEvent="delEvent"></MyBlog>

              </el-main>
              <el-footer class="el-space">
              </el-footer>
            </el-container>
            <el-aside width="200px"></el-aside>
          </el-container>

        </el-scrollbar>
      </template>

      <template v-else>
        <el-main>
          <h1>暂无内容</h1>
        </el-main>
      </template>
      <div class="pagination">
        <el-pagination small background layout="prev, pager, next" :total="MaxNum" :page-size="pagesize" class="mt-4"
          @current-change="handleCurrentChange" style="text-align: right;" />
      </div>
    </el-container>

  </div>
</template>
<script>
import { ElContainer } from 'element-plus'
import { ElScrollbar } from 'element-plus'
import NavBar from '../components/NavBar.vue';
import axios from 'axios';
import { ElPagination } from 'element-plus';
import MyBlog from '../components/myBlog.vue';
export default {
  components: {
    NavBar,
    MyBlog
  },
  data() {
    return {
      items: [],
      pagesize: 2,
      currentPage: 1,
      MaxNum: 0,
      userinfo: []
    }
  },
  beforeMount() {
    axios({
      method: "GET",
      // url: "http://localhost:8080/article/countnumById"
      url: "http://49.232.144.53:8080/article/countnumById"
    }).then(res => {

      if (res.data.code == 200) {
        this.MaxNum = res.data.data;
      }
      else {
        this.MaxNum = 0;
      }
    })
    //获取用户信息
    axios({
      method: "GET",
      // url: "http://localhost:8080/islogin"
      url: "http://49.232.144.53:8080/islogin"
    }).then(res => {
      if (res.data.code == 200) {
        this.userinfo = res.data.data;
      }
      else {
        location.href = "blog_login.html";
      }
    })
  },
  mounted() {

    const params = new URLSearchParams();
    params.append('pageSize', this.pagesize);
    params.append('index', this.currentPage);
    axios({
      method: "GET",
      // url: "http://localhost:8080/article/ByUidGetArticls",
      url: "http://49.232.144.53:8080/article/ByUidGetArticls",
      params: params
    }).then(res => {
      if (res.data.code == 200) {
        this.items = res.data.data;
      }
      else {
        this.items = [];
      }
    })
  },
  methods: {
    handleCurrentChange(val) {
      //console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.disable = true;
      const params = new URLSearchParams();
      params.append('pageSize', this.pagesize);
      params.append('index', this.currentPage);
      axios({
        method: "GET",
        // url: "http://localhost:8080/article/ByUidGetArticls",
        url: "http://49.232.144.53:8080/article/ByUidGetArticls",
        params: params
      }).then(res => {
        if (res.data.code == 200) {
          this.items = res.data.data;
        }
        else {
          this.items = [];
        }
      })
    },
    delEvent(index) {
      //console.log("delEvent");
      this.items.splice(index, 1);
      location.href = location.href;
    }
  },
}
</script>
<style scoped>
.el-pagination {
  justify-content: center;
}

.el-header,
.el-footer {
  background-color: rgba(red, green, blue, 0.0);
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-space {
  height: 5px;
}

.el-aside {
  background-color: #D3DCE6;
  opacity: 0.0;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  opacity: 0.8;
  color: #333;
  text-align: center;
  line-height: 100px;
  height: 250px;
}

body>.el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
