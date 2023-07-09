import 'bootstrap/dist/css/bootstrap.css'
import * as Vue  from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueCardFormat from 'vue-credit-card-validation';
import VuePaycard from "vue-paycard";

axios.defaults.baseURL = 'http://localhost:8080'

Vue.createApp({
    render: () => Vue.h(App),
})
.use(VueCardFormat)
.use(router)
.use(VuePaycard)
.mount('#app')

