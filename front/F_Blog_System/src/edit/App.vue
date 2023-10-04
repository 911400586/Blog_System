

<template>
  <div>
    <!-- 导航栏 -->
    <NarBar img="/img/bk.png" :userinfo="userinfo"></NarBar>
    <!-- 编辑框容器 -->
    <div class="blog-edit-container" id="blog-edit-container">
      <!-- 标题编辑区 -->
      <div class="title">
        <input type="text" placeholder="在这里写下文章标题" id="add_title" v-model="title" />
        <!-- v-loading.fullscreen.lock 遮罩全部屏蔽 -->
        <template v-if="state != 3">
          <button @click="modify" id="submit" :disabled="saveAble">更新文章</button>
        </template>
        <template v-if="state == 2">
          <button @click="pub" id="submit" :disabled="pubAble">发布文章</button>
        </template>
        <!-- <template v-if="state == 3">
          <button @click="onTime" id="submit" :disabled="TimeAble">定时发布</button>
        </template>
        <el-dialog v-model="dialog">
          12
          12
          21
        </el-dialog> -->
      </div>
      <!-- 创建编辑器标签 -->
      <div id="editorDiv">
        <textarea id="editor-markdown" style="display: none"></textarea>
      </div>
    </div>
  </div>
</template>
<script>
import NarBar from '../components/NavBar.vue'
import '../assets/lib/editor.md/css/editormd.min.css'
import '../assets/lib/jquery.min.js'
import '../assets/lib/editor.md/editormd.js'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { ElDialog } from 'element-plus'
export default {
  components: {
    NarBar
  },
  data() {
    return {
      info: {},
      userinfo: [],
      md: "# 请填写内容",
      id: "",
      title: "",
      content: "",
      state: "",
      pubAble: false,
      saveAble: false,
      TimeAble: false,
      loading: false,
      article: {},
      isget: false,
      dialog: false
    }
  },
  beforeMount() {
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
    //获取文章内容
    axios({
      method: "GET",
      // url: "http://localhost:8080/article/getArticleById" + location.search
      url: "http://49.232.144.53:8080/article/getArticleById" + location.search
    }).then(res => {
      if (res.data.code == 200) {
        this.isget = true;
        this.title = res.data.data.title;
        this.content = res.data.data.content;
        this.state = res.data.data.state;
        this.id = res.data.data.id;
        this.EdiotrInit(this.content);
      }
      else {
        ElMessage.error("获取文章标题内容失败!");
        this.EdiotrInit(this.content);
      }
    })
  },
  mounted() {

  },
  methods: {
    EdiotrInit(md) {
      this.editor = editormd("editorDiv", {
        // 这里的尺寸必须在这里设置. 设置样式会被 editormd 自动覆盖掉.
        width: "100%",
        // 高度 100% 意思是和父元素一样高. 要在父元素的基础上去掉标题编辑区的高度
        height: "600px",
        // 编辑器中的初始内容
        markdown: md,
        // 指定 editor.md 依赖的插件路径
        path: "../assets/lib/editor.md/lib/",
        saveHTMLToTextarea: true, //
      });
    },
    //更新文章
    modify(state) {
      if (!this.isget) {
        ElMessage.error("操作失败!");
        return false;
      }
      // alert(editor.getValue()); // 获取值
      // editor.setValue("#123") // 设置值
      var content = this.editor.getValue();
      var title = this.title;
      if (content == "" || title == "") {
        ElMessage.error("标题或者内容不能为空!");
        this.loading = !this.loading;
      }
      this.pubAble = true;
      this.saveAble = true;
      this.TimeAble = true;
      if (Number.isInteger(state)) {
        this.state = state;
      }
        //49.232.144.53
      axios({
        method: "POST",
        // url: "http://localhost:8080/article/update" + location.search,
        url: "http://49.232.144.53:8080/article/update" + location.search,
        data: {
          id: this.id,
          title: title,
          content: content,
          state: this.state
        }
      }).then(res => {
        if (res.data.code == 200) {
          ElMessage.success("恭喜你,更新文章成功!");
          this.pubAble = false;
          this.saveAble = false;
          this.TimeAble = false;
          if (confirm("是否继续更新文章?")) {
            //nothing
          }
          else {
            location.href = "blog_myblog.html";
          }
        }
      })

    },
    pub() {
      if (!this.isget) {
        ElMessage.error("操作失败!");
        return false;
      }
      if (this.state == 2) {
        this.modify(1);
        return true;
      }
      // alert(editor.getValue()); // 获取值
      // editor.setValue("#123") // 设置值
      var content = this.editor.getValue();
      var title = this.title;
      if (content == "" || title == "") {
        ElMessage.error("标题或者内容不能为空!");
        this.loading = !this.loading;
      }
      this.pubAble = true;
      this.svaeAble = true;
      this.TimeAble = true;
      axios({
        method: "POST",
        // url: "http://localhost:8080/article/add",
        url: "http://49.232.144.53:8080/article/add",
        data: {
          title: title,
          content: content,
          state: 1
        }
      }).then(res => {
        if (res.data.code == 200) {
          ElMessage.success("恭喜你,保存草稿成功!");
          this.pubAble = false;
          this.svaeAble = false;
          this.TimeAble = false;
          if (confirm("是否继续添加草稿?")) {
            editor.setValue("# 请填写内容");
            this.title = "请填写标题";
          }
          else {
            location.href = "blog_myblog.html";
          }
        }
      })
    },
    // onTime() {
    // 
    // }
  }
}
</script>
<style>
.blog-edit-container {
  width: 1000px;
  height: calc(100% - 50px);
  margin: 0 auto;
}

.blog-edit-container .title {
  width: 100%;
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.blog-edit-container .title input {
  height: 40px;
  width: 890px;
  text-indent: 10px;
  border-radius: 10px;
  outline: none;
  border: none;
  background-color: rgba(255, 255, 255, 0.8);
}

.blog-edit-container .title button {
  height: 40px;
  width: 100px;

  color: white;
  background-color: orange;
  border: none;
  outline: none;
  border-radius: 10px;
}

.blog-edit-container .title button:active {
  background-color: aquamarine;
}

#editor {
  border-radius: 10px;
  /* 针对 #editor 用 bgc 属性无法设置半透明了. 里面包含的内容带了背景色 */
  /* background-color:rgba(255, 255, 255, 0.8); */
  /* 可以使用 opacity 属性实现 */
  opacity: 80%;
}


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
</style>
