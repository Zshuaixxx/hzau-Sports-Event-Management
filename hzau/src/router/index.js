import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // 一级路由
    // {
    //   path: '/',
    //   redirect: '/home'
    // },
    {
      path: '/',
      component: () => import('@/views/login/LoginPage.vue')
    },
    {
      path: '/home',
      component: () => import('@/views/Amd/HomePage.vue'),
      redirect: '/home/game',
      children: [
        {
          path: '/home/game',
          component: () => import('@/views/Amd/GamePage.vue')
        },
        {
          path: '/home/voluntee',
          component: () => import('@/views/Amd/VolunteePage.vue')
        },
        {
          path: '/home/account',
          component: () => import('@/views/Amd/AccountPage.vue')
        },
        {
          path: '/home/time',
          component: () => import('@/views/Amd/TimePage.vue')
        },
        {
          path: '/home/where',
          component: () => import('@/views/Amd/WherePage.vue')
        },
        {
          path: '/home/sign',
          component: () => import('@/views/Amd/SignPage.vue')
        }
      ]
    }
  ]
})
import { useUserStore } from '@/stores'
router.beforeEach((to) => {
  // 如果没有token, 且访问的是非登录页，拦截到登录，其他情况正常放行
  const useStore = useUserStore()
  if (!useStore.token && to.path !== '/') return
  //实际上线前打开
  // '/'
})

export default router
