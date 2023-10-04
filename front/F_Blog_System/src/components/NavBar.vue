<template>
    <div class="nav">
        <img :src="img" alt="" class="img">
        <span class="title">博客系统</span>
        <!-- 用来占据中间位置 -->
        <span class="spacer"></span>
        <a href="index.html">主页</a>
        <template v-if="userinfo.length == 0">
            <a href="blog_login.html">登录</a>
            <a href="blog_reg.html">注册</a>
        </template>
        <template v-else>
            <a href="blog_myblog.html">我的相关博客</a>
            <a href="blog_add.html">写博客</a>
            <a href="blog_center.html">个人中心</a>
            <a href="#" @click="logout">注销</a>
        </template>
    </div>
</template>
<script>
import axios from 'axios';
import { ElMessage } from 'element-plus';
export default {
    props: {
        img: {
            type: String,
            require: true,
        },
        userinfo: []
    },
    methods: {
        logout() {
            if (confirm("确定要退出吗?")) {
                axios({
                    method: "GET",
                    // url: "http://localhost:8080/logout"
                    url: "http://49.232.144.53:8080/logout"
                }).then(res => {
                    if (res.data.code == 200) {
                        ElMessage.success("恭喜你退出成功!");
                        location.href = "index.html";
                    }
                    else {
                        ElMessage.error("退出失败!");
                    }
                })
            }
            else {
                //nothing
            }
        }
    }
}
</script>
<style>
/* 上方导航栏 */
.nav {
    width: 100%;
    height: 50px;
    background-color: rgba(51, 51, 51, 0.4);
    color: #fff;

    display: flex;
    justify-content: left;
    align-items: center;
}

/* 导航栏中的图标 */
.nav img {
    width: 40px;
    height: 40px;
    margin-left: 30px;
    margin-right: 10px;
    border-radius: 50%;
}

/* 导航栏中的占位器 */
.nav .spacer {
    width: 70%;
}

/* 导航栏中的按钮 */
.nav a {
    color: #fff;
    text-decoration: none;
    padding: 0 10px;
}
</style>