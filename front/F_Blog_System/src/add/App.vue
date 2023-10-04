

<template>
  <el-dialog v-model="dialog" title="选择时间">
    <div class="demo-datetime-picker">
      <div class="block">
        <span class="demonstration">Default</span>
        <el-date-picker @change="change" v-model="value1" type="datetime" placeholder="Select date and time" /><button
          @click="timeSub">提交</button>
      </div>

    </div>
  </el-dialog>
  <div>
    <!-- 导航栏 -->
    <NarBar img="/img/bk.png" :userinfo="userinfo"></NarBar>
    <!-- 编辑框容器 -->
    <div class="blog-edit-container" id="blog-edit-container">
      <!-- 标题编辑区 -->
      <div class="title">
        <input type="text" placeholder="在这里写下文章标题" id="add_title" v-model="title" />
        <!-- v-loading.fullscreen.lock 遮罩全部屏蔽 -->
        <button @click="pub" id="submit" :disabled="pubAble">发布文章</button>
        <button @click="save" id="submit">保存草稿</button>
        <button @click="onTime" id="submit" :disabled="TimeAble">定时发布</button>
        <!-- <el-dialog v-model="dialog" title="选择时间"> -->

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
import '../assets/lib/jquery.min.js'
import '../assets/lib/editor.md/css/editormd.min.css'
import '../assets/lib/editor.md/editormd.js'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { ElDatePicker } from 'element-plus'
import { ParDate } from './date.js'
export default {
  components: {
    NarBar
  },
  data() {
    return {
      info: {},
      userinfo: [],
      md: "# 请填写内容",
      title: "",
      pubAble: false,
      svaeAble: false,
      TimeAble: false,
      loading: false,
      ed: true,
      dialog: false,
      value1: false,
      pubtime: false,
      timesub: false,
    }
  },
  mounted() {
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
    //初始化editor.md
    this.editor = editormd("editorDiv", {
      // 这里的尺寸必须在这里设置. 设置样式会被 editormd 自动覆盖掉.
      width: "100%",
      // 高度 100% 意思是和父元素一样高. 要在父元素的基础上去掉标题编辑区的高度
      height: "800px",
      // 编辑器中的初始内容
      markdown: this.md,
      // 指定 editor.md 依赖的插件路径
      path: "../assets/lib/editor.md/lib/",
      saveHTMLToTextarea: true, //
    });
  },
  methods: {
    pub() {
      // alert(editor.getValue()); // 获取值
      // editor.setValue("#123") // 设置值
      var content = this.editor.getValue();
      var title = this.title;
      if (content == "" || title == "") {
        ElMessage.error("标题或者内容不能为空!");
        this.loading = !this.loading;
        return;
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
          ElMessage.success("恭喜你,发布文章成功!");
          this.pubAble = false;
          this.svaeAble = false;
          this.TimeAble = false;
          if (confirm("是否继续添加文章?")) {
            editor.setValue("# 请填写内容");
            this.title = "请填写标题";
          }
          else {
            location.href = "blog_myblog.html";
          }
        }
      })

    },
    save() {
      // alert(editor.getValue()); // 获取值
      // editor.setValue("#123") // 设置值
      var content = this.editor.getValue();
      var title = this.title;
      if (content == "" || title == "") {
        ElMessage.error("标题或者内容不能为空!");
        this.loading = !this.loading;
        return;
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
          state: 2
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
    onTime() {
      var content = this.editor.getValue();
      var title = this.title;
      if (content == "" || title == "") {
        ElMessage.error("标题或者内容不能为空!");
        this.loading = !this.loading;
        return;
      }
      this.dialog = true;
      this.value1 = true;

    },
    change(val) {
      // console.log(val);
      // var data = new Date(val);
      // var date = ParDate(data);
      // console.log(date);
      var data = new Date(val);
      var date = ParDate(data);
      this.pubtime = data;
      console.log(date);
    },
    timeSub() {
      //验证时间
      var now = new Date();
     
      if (this.pubtime < now) {
        alert("发布日期不合理!");
        return false;
      }
      now.setDate(now.getDate()+3);
      if(this.pubtime > now)
      {
        alert("只支持三天内发布!");
        return false;
      }
      var time = ParDate(this.pubtime);
      if (confirm("确定在" + time + "发布吗？")) {
        var content = this.editor.getValue();
        var title = this.title;
        axios({
          method: "POST",
          // url: "http://localhost:8080/article/add",
          url: "http://49.232.144.53:8080/article/add",
          data: {
            title: title,
            content: content,
            state: 3,
            pubtime: time
          }
        }).then(res => {
          if (res.data.code == 200) {
            ElMessage.success("定时发布成功!");
            setTimeout(() => {
              location.href = location.href;
            }, 600);
          }
          else {
            ElMessage.error("发布失败!");
          }
        })
      }
      else {
        //nothing todo
      }

    }
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

/* 模态框 */
.demo-datetime-picker {
  display: flex;
  width: 100%;
  padding: 0;
  flex-wrap: wrap;
}

.demo-datetime-picker .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  flex: 1;
}

.demo-datetime-picker .block:last-child {
  border-right: none;
}

.demo-datetime-picker .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}
</style>
