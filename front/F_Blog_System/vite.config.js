
// const { resolve } =  require('path')
// const { defineConfig } = require('vite')
// import vue from '@vitejs/plugin-vue'

// // https://vitejs.dev/config/
// export default defineConfig({
//   plugins: [vue()],
//  build: {
//   rollupOptions: {
//     input:{
//       main: resolve(__dirname,'index.html'),
//     }
//   }
//  }
// })
// vite.config.js
 import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import { defineConfig } from 'vite'

export default defineConfig({
  plugins: [vue()],
  build: {
    rollupOptions: {
      input: {
        main: resolve(__dirname, 'index.html'),
        blog_add: resolve(__dirname,'blog_add.html'),
        blog_center: resolve(__dirname,'blog_center.html'),
        blog_content: resolve(__dirname,'blog_content.html'),
        blog_edit: resolve(__dirname,'blog_edit.html'),
        blog_login: resolve(__dirname,'blog_login.html'),
        blog_myblog: resolve(__dirname,'blog_myblog.html'),
        blog_reg: resolve(__dirname,'blog_reg.html'),
      },
    },
  },
})
