import './assets/base.css'
import 'amfe-flexible'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import store from './stores'
import axios from "axios";
import { Button,Icon , Tab,Tabs,ActionBar, ActionBarIcon, ActionBarButton, Stepper,Checkbox,
    CheckboxGroup,SubmitBar,ContactCard,Card, AddressList, AddressEdit } from 'vant';
import { Locale } from 'vant';
// 引入英文语言包
import enUS from 'vant/es/locale/lang/en-US';

Locale.use('en-US', enUS);

import 'vant/lib/index.css';
axios.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);
const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)
app.use(store).use(Button).use(Icon).use(Tab).use(Tabs).use(ActionBar).use(ActionBarIcon).use(ActionBarButton)
    .use(Checkbox).use(Stepper).use(CheckboxGroup).use(SubmitBar).use(ContactCard).use(Card).use(AddressList).use(AddressEdit)
app.mount('#app')
