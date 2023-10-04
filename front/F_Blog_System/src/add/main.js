import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import axios from 'axios'
// import '../assets/lib/jquery.min.js'
// import '../assets/lib/editor.md/css/editormd.min.css'
// import '../assets/lib/editor.md/editormd.js'
axios.defaults.withCredentials = true

const app = createApp(App)
app.use(ElementPlus,axios)
app.mount('#app')