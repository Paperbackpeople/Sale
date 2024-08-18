import { createStore } from 'vuex';
import axios from "../axios";
import { ElMessage } from 'element-plus';
import router from '../router';

const MAX_RETRIES = 3;
const RETRY_DELAY = 1000; // 1秒

const store = createStore({
    state() {
        return {
            email: '',
            user: null,
            username: null,
            sessionTimeout: null,
            contentNavList: {
                popular: [],
                recommend: [],
            },
        };
    },
    mutations: {
        setContentNavList(state, products) {
            state.contentNavList.popular = products;
            console.log(products);
            state.contentNavList.recommend = products.filter(product =>
                product.categories.some(category => category.name.includes('Recommend'))
            );
        },
        setUser(state, email) {
            state.user = email;
        },
        clearUser(state) {
            state.user = null;
            state.email = '';
            state.username = null;
        },
        setUsername(state, username) {
            state.username = username;
        },
        clearUsername(state) {
            state.username = null;
        },
        setSessionTimeout(state, timeoutId) {
            state.sessionTimeout = timeoutId;
        },
        clearSessionTimeout(state) {
            if (state.sessionTimeout) {
                clearTimeout(state.sessionTimeout);
                state.sessionTimeout = null;
            }
        },
    },
    actions: {
        async login({ commit, dispatch }, { email, code }) {
            try {
                const response = await axios.post('/api/verify_code', { email, code }, {
                    headers: { 'Content-Type': 'application/json' }
                });
                const token = response.headers['authorization'].split(' ')[1];
                localStorage.setItem('token', token);
                if (response.data === 'Verification successful, please register your username.') {
                    commit('setUser', email);
                    dispatch('startSessionTimeout');
                    await router.push('/username-input');
                } else if (response.data === 'Verification successful') {
                    commit('setUser', email);
                    dispatch('startSessionTimeout');
                    await router.push('/home');
                } else {
                    console.error('Login failed', response.data);
                    ElMessage.error('Login failed');
                }
            } catch (error) {
                console.error('Login failed', error);
                ElMessage.error('Login failed');
            }
        },
        startSessionTimeout({ commit, dispatch }) {
            commit('clearSessionTimeout');
            const timeoutId = setTimeout(() => {
                commit('clearUser');
                ElMessage.error('Session expired. Please log in again.');
                router.push('/login');
            }, 30 * 60 * 1000);
            commit('setSessionTimeout', timeoutId);
        },
        userActivity({ dispatch }) {
            dispatch('startSessionTimeout');
        },
        resetSessionTimeout({ dispatch }) {
            dispatch('startSessionTimeout');
        },
        saveUsername({ commit, state }, username) {
            commit('setUsername', username);
            axios.post('/api/register_username', {
                email: state.user,
                username: username
            }, {
                headers: { 'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem('token')}`
                }
            }).then(response => {
                if (response.data === 'Registration successful') {
                    router.push('/login');
                } else {
                    console.error('Registration failed', response.data);
                    ElMessage.error('Registration failed');
                }
            }).catch(error => {
                console.error('Registration failed', error);
                ElMessage.error('Registration failed');
            });
        },
        async fetchProducts({ dispatch, commit }) {
            try {
                await dispatch('tryFetchProducts');
            } catch (error) {
                console.error('Failed to fetch products after multiple attempts:', error);
                ElMessage.error('Failed to fetch products');
            }
        },
        async tryFetchProducts({ commit, dispatch }, retries = MAX_RETRIES) {
            try {
                const response = await axios.get('/api/products');
                if (response.data && response.data.status === 'success') {
                    commit('setContentNavList', response.data.data);
                } else {
                    console.error('Product data not found');
                }
            } catch (error) {
                if (error.response && error.response.status === 403 && retries > 0) {
                    console.warn(`403 Forbidden received. Retrying ${retries} more times...`);
                    await dispatch('delay', RETRY_DELAY);
                    return dispatch('tryFetchProducts', retries - 1);
                } else {
                    console.error('Failed to fetch products:', error);
                    throw error;
                }
            }
        },
        delay(_, ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
        }
    },
    getters: {
        getContentNavList: (state) => state.contentNavList,
    },
});

export default store;