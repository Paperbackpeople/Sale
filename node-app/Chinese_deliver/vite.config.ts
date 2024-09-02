import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import postCssPxToRem from 'postcss-pxtorem'



export default defineConfig({
  server: {
    host: '0.0.0.0',
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://nginx:80',
        changeOrigin: true,
        // rewrite: (path) => path.replace(/^\/api/, '')
      },
        '/images': {
            target: 'http://nginx:80',
            changeOrigin: true,
        }
    }
  },
  plugins: [
    vue(),
    vueJsx(),

  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
    css: {
        postcss: {
        plugins: [
            postCssPxToRem({
            rootValue: 37.5,
            propList: ['*']
            })
        ]
        }
    }

})
