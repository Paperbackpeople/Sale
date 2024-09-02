import { createStore } from 'vuex';
import axios from "../axios";
import { ElMessage } from 'element-plus';
import router from '../router';
import createPersistedState from 'vuex-persistedstate';
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
            currentItem : null,
            cartList: [],
            checkedList: {
                items: [],
                totalPrice: 0,
            },
            tokenInfo: {
                token: null,
                tokenUsed: false,
            }

        };
    },
    plugins: [createPersistedState()],
    mutations: {
        CLEAR_CART_LIST(state) {
            state.cartList = [];
            state.checkedList = [];
        },
        setCheckedList(state, checkedList) {
            state.checkedList = checkedList;
        },
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
        setCurrentItem(state, item) {
            state.currentItem = item;
        },
        addItemToCart(state, item) {
            const existingItem = state.cartList.find(i => i.productId === item.productId);
            if (existingItem) {
                existingItem.quantity = item.quantity; // 更新数量
            } else {
                state.cartList.push(item);
            }
            },
        setTokenInfo(state, token) {
            state.tokenInfo = {
                token: token,
                tokenUsed: false,
            };
        },
        markTokenAsUsed(state) {
            if (state.tokenInfo) {
                state.tokenInfo.tokenUsed = true;
            }
        },
        clearTokenInfo(state) {
            state.tokenInfo = {
                token: null,
                tokenUsed: false,
            };
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
        },
        setCurrentItem({ commit }, item) {
            commit('setCurrentItem', item);
        },
        updateCheckedList({ commit }, item) {
            commit('setCheckedList', item);
        },
        clearCartList({ commit }) {
            commit('CLEAR_CART_LIST');
        },
    },
    getters: {
        getContentNavList: (state) => state.contentNavList,
        getCurrentItem: (state) => state.currentItem,
    },
});

export default store;