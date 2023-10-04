

<template>
  <div>
    <NarBar img="/img/bk.png" :userinfo="userinfo"></NarBar>
    <div class="space"></div>
    <!-- 占位 -->
    <div class="container">
      <Login ref="input"></Login>
    </div>
  </div>
</template>
<script>
import NarBar from '../components/NavBar.vue'
import Login from '../components/Login.vue'
import axios from 'axios'
export default {
  components: {
    NarBar,
    Login
  },
  mounted() {
    axios({
      method: "GET",
      // url: "http://localhost:8080/islogin"
      url: "http://49.232.144.53:8080/islogin"
    }).then(res => {
      if (res.data.code == 200) {
        //直接跳转到主页
        location.href = "blog_list.html";
      }
    })
  },
  data() {
    return {
      userinfo: []
    }
  }
}
</script>
<style scoped>
.space {
  height: 150px;
}
</style>
