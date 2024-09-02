import { createRouter, createWebHistory } from 'vue-router'
import EmailLogin from '../components/EmailLogin.vue'
import UsernameInput from '../components/UsernameInput.vue'
import Home from '../components/views/home/index.vue'
import Order from '../components/views/order/index.vue'
import Mine from '../components/views/mine/index.vue'
import Cart from '../components/views/cart/index.vue'
import Detail from '../components/views/detail/index.vue'
import CreateOrder from '../components/views/createOrder/index.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect : '/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: EmailLogin
    },
    {
      path: '/username-input',
      name: 'UsernameInput',
      component: UsernameInput
    },
    {
      path: '/home',
        name: 'Home',
        component: Home
    },
    {
      path: '/order',
        name: 'Order',
        component: Order
    },
    {
      path: '/mine',
        name: 'Mine',
        component: Mine
    },
    {
      path: '/cart',
        name: 'Cart',
        component : Cart
    },
    {
      path: '/detail',
      name: 'Detail',
      component : Detail

    },
    {
      path:'/createOrder',
      name:'CreateOrder',
      component:CreateOrder
    }
  ]
});

export default router