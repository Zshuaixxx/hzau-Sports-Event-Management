<script setup>
import { useUserStore } from '@/stores'
const userStore = useUserStore()
console.log(userStore.taken)
</script>
<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px" class="el-aside">
        <div class="el-aside__logo"></div>
        <!-- 个人信息 -->
        <span class="showtaken">个人信息：{{ userStore.taken }}</span>
        <el-menu
          active-text-color="#ffd04b"
          background-color="#000000"
          :default-active="$route.path"
          text-color="#fff"
          router
        >
          <el-menu-item index="/home/game">
            <span>赛事管理</span>
          </el-menu-item>
          <el-sub-menu index="2">
            <template #title>
              <span>报名管理</span>
            </template>
            <el-menu-item index="/home/sign">报名文件上传</el-menu-item>
            <el-menu-item index="/home/player">初赛成绩录入</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="1">
            <template #title>
              <span>志愿管理</span>
            </template>
            <el-menu-item index="/home/voluntee">人员管理</el-menu-item>
            <el-menu-item index="/home/time">时间管理</el-menu-item>
            <el-menu-item index="/home/where">地点管理</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/home/account">
            <span>账号管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <!-- 主体内容  对应二级路由出口 -->
        <router-view></router-view> </el-main
      >>
    </el-container>
  </div>
</template>

<style>
.el-aside {
  height: 100vh;
  background-color: #000000;
}
.el-aside__logo {
  width: 100%;
  height: 100px;
  /* 背景图片 */
  background-image: url('@/assets/aside-logo.png');
  /* 背景图片位置 */
  background-position: center;
  /* 背景图片大小 */
  background-size: contain;
  /* 背景图片平铺 */
  background-repeat: no-repeat;
  /* margin-top: 100px; */
}
.showtaken {
  color: white;
  margin-bottom: 100px;
}
</style>
