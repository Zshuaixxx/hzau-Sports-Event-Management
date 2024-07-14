<script setup>
import { ref } from 'vue'
import { edittimeService, timeInfoService } from '@/api/user'
import { ElMessage } from 'element-plus'
import { onMounted } from 'vue'

const twoday = ref([{}, {}])

//进入页面自动发请求获取数据
onMounted(() => {
  console.log('自动获取')
  //调用getInfo函数
  getInfo()
})
//获取信息
const getInfo = async () => {
  const res = await timeInfoService()
  console.log('查询时间信息接口返回：', res)
  twoday.value = res.data
  console.log('twoday:', twoday.value)
}
//修改请求
const handleSubmit = async (row) => {
  console.log('传递给后台的日期信息：', row)
  const res = await edittimeService(row)
  console.log('后台返回', res)
  if (res.data === 1) {
    ElMessage.success('设置成功')
  } else {
    ElMessage.error('设置失败')
  }
}
</script>
<template>
  <h4>学生可选择的志愿时间：</h4>
  <!-- 提供可选择的时间 -->
  <el-table :data="twoday" border style="width: 100%">
    <el-table-column prop="text" label="志愿时间" width="250">
      <template v-slot:default="scope">
        <el-input v-model="scope.row.text" />
      </template>
    </el-table-column>
    <el-table-column prop="value" label="值" v-if="false" />
    <el-table-column fixed="right" min-width="120" label="操作">
      <template v-slot:default="scope">
        <el-button type="primary" size="small" @click="handleSubmit(scope.row)">
          确认修改
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>
