<template>
    <div class="login-container">
        <!-- 中间的登陆框 -->
        <div class="login-dialog">
            <h3>登陆</h3>
            <div class="row">
                <span>用户名</span>
                <input type="text" id="username" v-model.trim="username" ref="user">
            </div>
            <div class="row">
                <span>密码</span>
                <input type="password" id="password" v-model.trim="password" ref="password">
            </div>
            <div class="row">
                <button id="submit" @click="submit" :disabled="disable">提交</button>
            </div>
        </div>
    </div>
</template>
<script>
import { ElMessage } from 'element-plus'
import axios from 'axios'

export default {
    data() {
        return {
            username: "",
            password: "",
            disable: false
        }
    },
    methods: {
        submit() {
            //进行校验   
            // ElMessage.success("登录成功");
            // location.href = "myblog_list.html";
            if (this.username === "") {
                ElMessage.error("请先输入账号");
                return false;
            }
            if (this.password === "") {
                ElMessage.error("请输入密码")
            }
            //进行axios
            // axios({

            // })
            this.disable = true;
            const params = new URLSearchParams();
            params.append('username', this.username);
            params.append('password', this.password);
            axios({
                // url: "http://localhost:8080/login",
                url: "http://49.232.144.53:8080/login",
                params: params,
                method: "POST"
            }).then(res => {

                if (res.data.code == 200) {
                    ElMessage.success("登录成功!");
                    location.href = "index.html";
                }
                else {
                    ElMessage.error("登录失败!");
                    this.disable = false;
                }
            })
        }
    }
}
</script>
<style>
.login-container {
    width: 100%;
    height: calc(100% - 50px);
    display: flex;
    justify-content: center;
    align-items: center;
}

.login-dialog {
    width: 400px;
    height: 400px;
    background-color: rgba(255, 255, 255, 0.8);
    border-radius: 10px;
}

.login-dialog h3 {
    padding: 50px 0;
    text-align: center;
}

.login-dialog .row {
    height: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.login-dialog .row span {
    display: block;
    width: 100px;
    font-weight: 700;
}

.login-dialog .row input {
    width: 200px;
    height: 40px;
    line-height: 40px;
    font-size: 24px;
    border-radius: 10px;
    border: none;
    outline: none;
    text-indent: 10px;
}

.login-dialog button {
    width: 300px;
    height: 50px;
    color: white;
    background-color: black;
    border: none;
    border-radius: 10px;
}

.login-dialog button:active {
    background-color: #666;
}
</style>