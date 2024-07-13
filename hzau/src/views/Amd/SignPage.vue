<script setup>
import { UploadFilled } from '@element-plus/icons-vue'
// import { ref } from 'vue'
const handleSuccess = (response, file, fileList) => {
  console.log('file', file)
  console.log('fileList', fileList)
  ElMessage.success('上传成功')
  console.log('上传成功:', response)
}

const handleError = (error, file, fileList) => {
  console.log('file', file)
  console.log('fileList', fileList)
  console.error('上传失败:', error)
}

const beforeUpload = (file) => {
  // 这里可以添加一些文件上传前的验证逻辑，比如检查文件大小、类型等
  console.log('file', file)
  const isLt2M = file.size / 1024 / 1024 < 50
  if (!isLt2M) {
    console.log('上传文件大小不能超过 50MB!')
    ElMessage.error('上传文件大小不能超过 50MB!')
  }
  const isExcelFile = /\.(xls|xlsx)$/.test(file.name)
  console.log('是否为Excel文件:', isExcelFile)
  if (!isExcelFile) {
    ElMessage.error('请上传 Excel 报名文件！')
    return false
  }
  // return true;
  return isLt2M && isExcelFile // 返回 true 表示允许上传，返回 false 则阻止上传
}

//点击分配号码牌
import { fenpeihaomaServer } from '@/api/user'
const gethaoma = async () => {
  const res = await fenpeihaomaServer()
  console.log('分配号码牌结果：', res)
  // if (res.data === 1) {
  //   ElMessage.success('分配号码牌成功')
  // } else {
  //   ElMessage.error('分配号码牌失败')
  // }
  ElMessage.success('成功分配' + res.data + '名运动员号码牌')
}
//点击一键为所有运动员分配组号和跑道号
import { getzuhaoServer } from '@/api/user'
const getzuhao = async () => {
  const res = await getzuhaoServer()
  console.log('返回的运动员组号信息：', res)
  if (res.data === 1) {
    ElMessage.success('分配组号成功，可前往成绩录入界面查询')
  } else {
    ElMessage.error('分配失败')
  }
}
</script>

<template>
  <h3>赛事报名:上传报名文件到下方即可</h3>
  <el-upload
    class="upload-demo"
    drag
    action="http://localhost:80/admin/upload"
    multiple
    accept=".xls .xlsx"
    :show-file-list="true"
    :on-success="handleSuccess"
    :on-error="handleError"
    :before-upload="beforeUpload"
  >
    <el-icon class="el-icon--upload"><upload-filled /></el-icon>
    <div class="el-upload__text">拖拽文件到此处 <em>点击上传</em></div>
    <template #tip>
      <div class="el-upload__tip">文件大小不能超过 50MB</div>
    </template>
  </el-upload>
  <el-button type="primary" @click="gethaoma()"
    >点击一键自动分配运动员号码牌</el-button
  >
  <el-button type="primary" @click="getzuhao()"
    >点击一键为所有运动员分配组号和跑道号</el-button
  >
</template>
