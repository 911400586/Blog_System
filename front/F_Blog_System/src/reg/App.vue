
<template>
  <div>
    <NarBar img="/img/bk.png" :userinfo="userinfo"></NarBar>
    <div class="space"></div>
    <!-- 占位 -->
    <div class="container">
      <Reg></Reg>
    </div>
  </div>
</template>
<script>
import NarBar from '../components/NavBar.vue'
import Reg from '../components/Reg.vue'
import axios from 'axios'
export default {
  components: {
    NarBar,
    Reg
  },
  data() {
    return {
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
  },

}
</script>
<style scoped>
.space {
  height: 200px;
}
</style>
  