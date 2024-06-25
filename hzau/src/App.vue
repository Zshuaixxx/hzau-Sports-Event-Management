<script setup>
import zh from 'element-plus/es/locale/lang/zh-cn.mjs'
</script>

<template>
  <!-- 国际化处理 -->
  <el-config-provider :locale="zh">
    <div>
      <router-view></router-view>
    </div>
  </el-config-provider>
</template>

<style scoped></style>
