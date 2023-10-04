<template>
    <div class="login-container">
        <!-- 中间的注册框 -->
        <div class="login-dialog">
            <h3>注册</h3>
            <div class="row">
                <span>用户名</span>
                <input type="text" id="username" v-model.trim="username" ref="username">
            </div>
            <div class="row">
                <span>密码</span>
                <input type="password" id="password1" v-model.trim="password" ref="password">
            </div>
            <div class="row">
                <span>确认密码</span>
                <input type="password" id="password2" v-model.trim="password2" ref="password2">
            </div>
            <div class="row">
                <span>邮箱</span>
                <input type="text" id="email" v-model.trim="email" ref="email">
            </div>
            <div class="row">
                <span>验证码</span>
                <input type="text" id="code" v-model.trim="code" ref="code"><button @click="verifyCode"
                    style="width: 70px;">发送验证码</button>
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
            password2: "",
            code: "",
            email: "",
            isStart: false,
            disable: false
        }
    },

    methods: {
        verifyCode() {
            if (this.isStart) {
                ElMessage.error("验证码已经发送,60秒后再试!");
                return false;
            }
            if (this.email == "") {
                ElMessage.error("请输入邮箱地址!");
                return;
            }
            const params = new URLSearchParams();
            params.append('email', this.email);
            this.isStart = true;
            axios({
                method: "POST",
                params: params,
                // url: "http://localhost:8080/email"
                url: "http://49.232.144.53:8080/email"
            }).then(res => {
                if (res.data.code == 200) {
                    //发送验证码成功!
                    this.isStart = true;
                    ElMessage.success("验证码发送成功,请注意查收!");
                    setTimeout(() => {
                        this.isStart = false;
                    }, 7000)
                }
                else {
                    ElMessage.error("验证码发送失败!");
                    this.isStart = false;
                }
            });
        },
        submit(e) {

            if (this.username === "") {
                ElMessage.error("请先输入账号");
                this.$refs.username.focus();
                return false;
            }
            if (this.password === "") {
                ElMessage.error("请输入密码");
                this.$refs.password.focus();
                return false;
            }
            if (this.password2 === "") {
                ElMessage.error("请再次入密码");
                this.$refs.password2.focus();
                return false;
            }
            if (this.email === "") {
                ElMessage.error("请输入邮箱");
                this.$refs.email.focus();
                return false;
            }
            if (this.code === "" || this.code.length != 6) {
                ElMessage.error("请输入正确的验证码")
                this.$refs.code.focus();
                return false;
            }
            //进行axios
            // axios({

            // })
            this.disable = true;
            const params = new URLSearchParams();
            params.append('username', this.username);
            params.append('password', this.password);
            params.append('email', this.email);
            params.append("code", this.code);
            axios({
                // url: "http://localhost:8080/reg",
                url: "http://49.232.144.53:8080/reg",
                params: params,
                method: "POST"
            }).then(res => {

                if (res.data.code == 200) {
                    ElMessage.success("注册成功!");
                    setTimeout(() => {
                        location.href = "blog_login.html";
                    }, 700);
                }
                else if (res.data.code == -2) {
                    ElMessage.error("该用户已注册!");
                    this.disable = false;
                }
                else {
                    ElMessage.error("验证码错误");
                }


            }).catch(err => {
                ElMessage.error("异常错误!");
                this.disable = true;
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
    display: flex;
    flex-direction: column;
}

.login-dialog h3 {

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