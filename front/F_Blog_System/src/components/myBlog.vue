<template>
    <!-- 标题,时间,访问量 -->


    <el-container>

        <el-header>
            <el-header>
                <h2>{{ title }}</h2>
            </el-header>
            <el-header>
                <h4>{{ Blog.createtime }}</h4>
            </el-header>
            <el-header>访问量:{{ Blog.rcount }}</el-header>
        </el-header>
        <!-- 站位 -->
        <el-header></el-header>
        <el-header></el-header>
        <!--  -->
        <el-main>
            <el-text class="mx-1" type="primary">{{ Blog.content }}</el-text>
            <br>
            <button @click="detail">查看详情</button>
            <button @click="del">删除文章</button>
            <button @click="modify">修改文章</button>
        </el-main>
        <!-- <el-foot>
                <button>查看详情</button>
            </el-foot> -->
    </el-container>
    <!-- <el-foot class="foot">
            
        </el-foot> -->
</template>
<script>
import axios from 'axios';
import { ElContainer } from 'element-plus'
import { ElText } from 'element-plus'
import { ElMessage } from 'element-plus'
export default {

    data() {
        return {
            //记录当前页面
            Index: 1
        }
    },
    props: ["Blog", "index"],
    methods: {
        detail() {
            var url = "blog_content.html?id=" + this.Blog.id;
            location.href = url;
        },
        del() {
            if (!confirm("确认要删除该文章吗?")) {
                return false;
            }
            //传递事件给父组件
            this.$emit('delEvent', this.index);
            //axios
            axios({
                method: "POST",
                // url: "http://localhost:8080/article/del?id=" + this.Blog.id
                url: "http://49.232.144.53:8080/article/del?id=" + this.Blog.id
            }).then(res => {
                if (res.data.code == 200) {
                    ElMessage.success("删除成功!");
                }
                else {
                    ElMessage.error("删除失败!");
                }
            })
        },
        modify() {
            var url = "blog_edit.html?id=" + this.Blog.id;
            location.href = url;
        }
    },
    computed: {
        title() {
            var tail = "";
            if (this.Blog.state == 2) {
                tail = "(草稿)";
            }
            if (this.Blog.state == 3) {
                tail = "(发布日期:" + this.Blog.pubtime + ")";
            }
            return this.Blog.title + tail;
        }
    }

}
</script>
<style scoped>
.el-header,
.el-footer {
    background-color: rgba(red, green, blue, 0.3);
    color: #333;
    text-align: center;
    line-height: 60px;
    height: 30px;

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
  