

<template>
  <div>
    <NarBar img="/img/bk.png" :userinfo="userinfo"></NarBar>

    <div class="container">
      <!-- 左侧个人信息 -->
      <div class="container-left">
        <div class="card">
          <img :src="img" class="avtar" alt="">
          <h3>{{ Username }}</h3>
          <a href="http:www.github.com">github 地址</a>
          <div class="counter">
            <span>文章</span>
          </div>
          <div class="counter">
            <span>{{count}}</span>
          </div>
        </div>
      </div>
      <!-- 右侧内容详情 -->
      <div class="container-right">
        <!-- <el-upload class="avatar-uploader" action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
          :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
          <template v-slot:tip>
            <h>图片上传</h>
          </template>
          <img v-if="imageUrl" :src="imageUrl" class="avatar" />

          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload> -->
        <el-container>
          <el-aside>
            <el-upload :limit="1" :with-credentials="true" class="avatar-uploader" :action="actionUrl"
              :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload" name="img">
              <template v-slot:tip>
                <h>图片上传</h>
              </template>
              <img v-if="imageUrl" :src="imageUrl" class="avatar" />

              <el-icon v-else class="avatar-uploader-icon">
                <Plus />
              </el-icon>
            </el-upload>
          </el-aside>

        </el-container>
        <el-container>
          <el-aside>
             请输入要更改的名字:<input type="text" v-model.trim="name"><button @click="ModifyName" :disabled="disable">确认</button>
          </el-aside>

        </el-container>
      </div>
    </div>
  </div>
</template>
<script>
import NarBar from '../components/NavBar.vue'
import axios from 'axios'
import { ElMessage, ElUpload } from 'element-plus'
import { ElContainer } from 'element-plus'
export default {
  components: {
    NarBar
  },
  data() {
    return {
      info: {},
      userinfo: [],
      imageUrl: "/img/imgUpLoad.png",
      count: 0,
      name:'',
      disable:false,
      // actionUrl: "http://localhost:8080/userImg"
      actionUrl: "http://49.232.144.53:8080/userImg"
    }
  },
  computed: {
    img() {
      if (Object.keys(this.userinfo).length === 0 || this.userinfo.photo == "") {
        return "img/bk.png";
      }
      else {
        // return "http://localhost:8080/getimg?img=" + this.userinfo.photo;
        return "http://49.232.144.53:8080/getimg?img=" + this.userinfo.photo;
      }
    },
    Username()
    {
      if (Object.keys(this.userinfo).length === 0 || this.userinfo.name == "") {
        return "小可爱";
      }
      else {
        // return "http://localhost:8080/getimg?img=" + this.userinfo.photo;
        return this.userinfo.name;
      }
    }
  },
  mounted() {
    axios({
      method: "GET",
      // url: "http://localhost:8080/islogin"
      url: "http://49.232.144.53:8080/islogin"  
    }).then(res => {
      if (res.data.code == 200) {

        this.userinfo = res.data.data;
      }
    })
    //获取文章数量
    axios({
      method: "GET",
      // url: "http://localhost:8080/islogin"
      url: "http://49.232.144.53:8080/article/countnumById"  
    }).then(res => {
      if (res.data.code == 200) {

        this.count = res.data.data;
      }
    })
  },
  methods: {
    beforeAvatarUpload(rawFile) {
      console.log(rawFile.type)
      if (rawFile.type !== 'image/jpeg') {
        ElMessage.error('图片格式必须为jpeg!')
        return false
      } else if (rawFile.size / 1024 / 1024 > 2) {
        ElMessage.error('照片大小不能超过2M')
        return false
      }
      return true
    },
    handleAvatarSuccess() {
      ElMessage.success("文件上传成功!");
      setTimeout(() => {
        location.href = location.href;
      }, 1500);
    },
    ModifyName(){
      if(this.name=="")
      {
          ElMessage.error("修改的名字不合法");
          return false;
      }
      this.disable = true;
      axios({
      method: "GET",
      // url: "http://localhost:8080/islogin"
      url: "http://49.232.144.53:8080/name?name="+this.name  
    }).then(res => {
      if (res.data.code == 200) {

        ElMessage.success("修改成功!");
        setTimeout(() => {
          location.href = location.href;
        }, 1500);
        this.disable = false;
          return true;
      }
      else{
        ElMessage.error("修改失败!");
        this.disable = false;
          return false;
      }
    })
    }
  }
}
</script>
<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
<style >
/* 页面内容容器, 版心 */
.container {
  /* 使用 calc 计算高度 */
  height: calc(100% - 50px);
  /* 设置版心宽度 */
  width: 1000px;
  /* 水平居中 */
  margin: 0 auto;
  /* 使用弹性布局 */
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 左侧部分, 用来放置用户信息 */
.container-left {
  height: 100%;
  width: 200px;
}

/* 右侧部分, 用来放置正文 */
.container-right {
  height: 100%;
  /* 和左侧部分中间留出 5px 间隙 */
  width: 795px;
  /* 如果内容溢出就自动加上滚动条 */
  overflow: auto;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
}

/* 展示用户信息的卡片 */
.card {
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
  padding: 30px;
}

/* 用户头像 */
.card img {
  width: 140px;
  height: 140px;
  border-radius: 50%;
}

/* 用户名 */
.card h3 {
  text-align: center;
  padding: 10px;
}

/* 用户 github 链接 */
.card a {
  display: block;
  text-align: center;
  color: #999;
  text-decoration: none;
  padding: 10px;
}

/* 展示文章数目的面板 */
.card .counter {
  padding: 5px;
  display: flex;
  justify-content: space-around;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
