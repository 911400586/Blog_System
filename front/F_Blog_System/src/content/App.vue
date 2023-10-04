

<template>
  <div>
    <NarBar img="/img/bk.png" :userinfo="userinfo"></NarBar>
    <div class="space"></div>
    <!-- 占位 -->
    <div class="container">
      <!-- 左侧个人信息
      <div class="container-left">
        <div class="card">
          <img src="/img/bk.png" class="avtar" alt="">
          <h3>小可爱</h3>
          <a href="http:www.github.com">github 地址</a>
          <div class="counter">
            <span>文章</span>
            <span>分类</span>
          </div>
          <div class="counter">
            <span>2</span>
            <span>1</span>
          </div>
        </div>
      </div> -->
      <!-- 右侧内容详情 -->
      <div class="container-right">
        <div class="blog-content">
          <!-- 博客标题 -->
          <h3 id="htitle">{{ info.title }}</h3>
          <!-- 博客时间 -->
          <div class="date">
            阅读量：
            <span id="rcount">{{ info.rcount }}</span> &nbsp;&nbsp;&nbsp;
            发布时间：
            <span id="createtime">{{ info.createtime }}</span>
            &nbsp;&nbsp;&nbsp;
          </div>
          <!-- 博客正文 -->
          <div id="editorDiv">

          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import NarBar from '../components/NavBar.vue'
import '../assets/lib/jquery.min.js'
import '../assets/lib/editor.md/css/editormd.preview.min.css'
import '../assets/lib/editor.md/editormd.js'
import '../assets/lib/editor.md/lib/marked.min.js'
import '../assets/lib/editor.md/lib/prettify.min.js'
import axios from 'axios'
export default {
  components: {
    NarBar
  },
  data() {
    return {
      info: {},
      userinfo: []
    }
  },

  mounted() {
    axios({
      method: "GET",
      // url: "http://localhost:8080/islogin"
      url: "http://49.232.144.53:8080/islogin"
    }).then(res => {
      if (res.data.code == 200) {
        //直接跳转到主页
        this.userinfo = res.data.data;
      }
    })
    //获取文章
    axios({
      method: "GET",
      // url: "http://localhost:8080/article/detail" + location.search,
      url: "http://49.232.144.53:8080/article/detail" + location.search,
    }).then(res => {
      this.info = res.data.data;
      //console.log(info);
      //初始化
      this.editormd = editormd.markdownToHTML("editorDiv", {
        markdown: res.data.data.content, // Also, you can dynamic set Markdown text
        // htmlDecode : true,  // Enable / disable HTML tag encode.
        // htmlDecode : "style,script,iframe",  // Note: If enabled, you should filter some dangerous HTML tags for website security.
        // axios({
        //   method: "POST",
        //   url: "/article/count",
        //   data: { id: id },
        // })
      });

      //浏览量增加一
      axios({
        method: "POST",
        // url: "http://localhost:8080/article/count?id=" + this.info.id
        url: "http://49.232.144.53:8080/article/count?id=" + this.info.id
      }).then(res => {
        if (res.data.code == 200) {
          this.info.rcount++;
        }
      })
    })
  }
}
</script>
<style scoped>
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
  justify-content: center;
  align-items: center;
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

/* 博客正文容器 */
.blog-content {
  padding: 30px;
}

/* 博客标题 */
.blog-content h3 {
  text-align: center;
  padding: 20px 0;
}

/* 博客日期 */
.blog-content .date {
  color: #008000;
  padding: 10px 0;
  text-align: center;
}

/* 博客内容段落 */
.blog-content p {
  text-indent: 2em;
  padding: 10px 0;
}
</style>
