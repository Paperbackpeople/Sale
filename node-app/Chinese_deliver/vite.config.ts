import { fileURLToPath, URL } from 'node:url';
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import vueJsx from '@vitejs/plugin-vue-jsx';
import postCssPxToRem from 'postcss-pxtorem';

export default defineConfig({
  server: {
    host: '0.0.0.0', // 监听所有 IP 地址，允许外部访问
    port: 3000,
        proxy: {
      '/api': {
        target: 'http://www.wangzhaoyu.com', // 指向 Nginx
        changeOrigin: true,
      },
      '/images': {
        target: 'http://www.wangzhaoyu.com',
        changeOrigin: true,
      },
    },
    hmr: {
      host: 'www.wangzhaoyu.com', // 使用你生产环境的域名
      port: 3000, // WebSocket 的端口与服务器端口一致
    },
  },
  plugins: [
    vue(),
    vueJsx(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)), // 设置路径别名
    },
  },
  css: {
    postcss: {
      plugins: [
        postCssPxToRem({
          rootValue: 37.5,
          propList: ['*'], // 转换 px 为 rem
        }),
      ],
    },
  },
});
