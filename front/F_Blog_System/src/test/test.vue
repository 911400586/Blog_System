<template>
   <button @click="open4">按钮</button>
</template>
  
<script>
  import { ElMessage } from 'element-plus'
    export default{
        data(){
            success:false;
        },
        methods:{
        open4(){
        ElMessage.success('Oops, this is a error message.');
        }
    }
  }
  </script>