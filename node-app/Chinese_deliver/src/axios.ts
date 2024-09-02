import axios from 'axios';
import router from './router';

const instance = axios.create({
    baseURL: 'http://localhost:3000',
});

// 请求拦截器
instance.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    // 排除登录请求 URL
    if (token && config.url !== '/login') {
        config.headers.Authorization = `${token}`;
    }
    return config;
}, error => {
    return Promise.reject(error);
});

// 响应拦截器
instance.interceptors.response.use(response => {
    return response;
}, error => {
    if (error.response.status === 401) {
        // 如果后端返回 401 未授权错误，清除令牌并跳转到登录页面
        localStorage.removeItem('token');
        router.push('/login');
    }
    return Promise.reject(error);
});

export default instance;