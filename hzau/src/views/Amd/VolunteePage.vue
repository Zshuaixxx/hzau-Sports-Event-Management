<script setup>
import { ref } from 'vue'
import { onMounted } from 'vue'
import { volunteerInfoService, volunteerEditService } from '@/api/user'
import { ElMessage } from 'element-plus'

//志愿者数据
const volunteerlist = ref([])

//进入页面自动发请求获取数据
onMounted(() => {
  //调用getInfo函数
  getInfo()
})
//获取信息
const getInfo = async () => {
  const res = await volunteerInfoService()
  console.log('查询志愿信息接口返回：', res)
  volunteerlist.value = res.data
  console.log('返回到志愿者信息：', volunteerlist.value)
}
//点击确认修改按钮
const handleEdit = async (row) => {
  console.log('传给后台的修改数据：', row)
  const res = await volunteerEditService({
    UserAccount: row.UserAccount,
    UserName: row.UserName,
    UserMessage: row.UserMessage,
    UserPhoneNumber: row.UserPhoneNumber,
    VolunteerTime: row.VolunteerTime
  })
  console.log('后台返回', res)
  if (res.data === 1) {
    ElMessage.success('修改成功')
    getInfo()
  } else {
    ElMessage.error('修改失败')
  }
}
</script>

<template>
  <el-table :data="volunteerlist" border style="width: 100%">
    <el-table-column prop="UserAccount" label="账号" width="180" />
    <el-table-column prop="UserName" label="姓名" />
    <el-table-column prop="UserMessage" label="志愿信息" width="180">
      <!-- 输入志愿地点 -->
      <template v-slot:default="scope">
        <el-input v-model="scope.row.UserMessage" />
      </template>
    </el-table-column>
    <el-table-column prop="UserPhoneNumber" label="联系电话" />
    <el-table-column prop="VolunteerTime" label="志愿时间" />
    <el-table-column fixed="right" min-width="120" label="操作">
      <template v-slot:default="scope">
        <el-button type="primary" size="small" @click="handleEdit(scope.row)">
          确认修改
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>
