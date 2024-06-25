<script setup>
import { ref } from 'vue'
import { onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { whereInfoService, whereEditService } from '@/api/user'
//地点数据
const placeData = ref([])
//进入页面自动发请求获取数据
onMounted(() => {
  //调用getInfo函数
  getwhereInfo()
})
//获取地点数据
const getwhereInfo = async () => {
  const res = await whereInfoService()
  console.log('查询地点信息接口返回：', res)
  placeData.value = res.data
  console.log('返回的地点信息：', placeData.value)
}
//点击确认修改按钮
const handleEdit = async (row) => {
  console.log('传给后台的修改数据：', row)
  const res = await whereEditService({
    XueYuanName: row.XueYuanName,
    Message: row.Message
  })
  console.log('后台返回', res)
  if (res.data === 1) {
    ElMessage.success('修改成功')
    getwhereInfo()
  } else {
    ElMessage.error('修改失败')
  }
}
</script>
<template>
  <el-table :data="placeData" border style="width: 100%">
    <el-table-column prop="XueYuanName" label="学院名称" width="180" />
    <el-table-column prop="Message" label="志愿地点信息" width="180">
      <!-- 输入志愿地点 -->
      <template v-slot:default="scope">
        <el-input v-model="scope.row.Message" />
      </template>
    </el-table-column>
    <el-table-column fixed="right" min-width="120" label="操作">
      <template v-slot:default="scope">
        <el-button type="primary" size="small" @click="handleEdit(scope.row)">
          确认修改
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>
