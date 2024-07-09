<script setup>
import { UploadFilled } from '@element-plus/icons-vue'
// import { ref } from 'vue'
const handleSuccess = (response, file, fileList) => {
  console.log('file', file)
  console.log('fileList', fileList)
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

  return true // 返回 true 表示允许上传，返回 false 则阻止上传
}
</script>

<template>
  <div>赛事报名</div>
  <el-upload
    class="upload-demo"
    drag
    action="http://localhost:80/admin/upload"
    multiple
    :show-file-list="true"
    :on-success="handleSuccess"
    :on-error="handleError"
    :before-upload="beforeUpload"
  >
    <el-icon class="el-icon--upload"><upload-filled /></el-icon>
    <div class="el-upload__text">拖拽文件到此处 <em>点击上传</em></div>
    <template #tip>
      <div class="el-upload__tip">
        jpg/png files with a size less than 500kb
      </div>
    </template>
  </el-upload>
</template>
