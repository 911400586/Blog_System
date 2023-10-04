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
        </el-main>
        <!-- <el-foot>
                <button>查看详情</button>
            </el-foot> -->
    </el-container>
    <!-- <el-foot class="foot">
            
        </el-foot> -->
</template>
<script>
import { ElContainer } from 'element-plus'
import { ElText } from 'element-plus'
export default {

    data() {
        return {
            //记录当前页面
            Index: 1
        }
    },
    props: ["Blog"],
    methods: {
        detail() {
            var url = "blog_content.html?id=" + this.Blog.id;
            location.href = url;
        }
    },
    computed: {
        title() {
            var tail = "";
            if (this.Blog.state == 2) {
                tail = "(草稿)";
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
  