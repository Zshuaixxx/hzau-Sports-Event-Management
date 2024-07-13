<script setup>
import { ref } from 'vue'
import { onMounted } from 'vue'
import { volunteerInfoService, volunteerEditService } from '@/api/user'
import { ElMessage } from 'element-plus'

//志愿者数据
const volunteerlist = ref([])
//志愿地点信息
const voluntplace = ref([])
const voluntplacetemp = ref(
  voluntplace.value.map((item) => ({ locationInfo: item }))
)
//进入页面自动发请求获取数据
onMounted(() => {
  //调用getInfo函数
  getInfo()
  getVoluntPlace()
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

//获取志愿地点信息
import { voluntplaceService } from '@/api/user'
const getVoluntPlace = async () => {
  const res = await voluntplaceService()
  console.log('志愿地点接口返回', res)
  voluntplace.value = res.data
}
//点击删除按钮
import { placedeleteService } from '@/api/user'
const handleDelete = async (row) => {
  console.log('点击删除', row)
  const res = await placedeleteService(row.locationInfo)
  console.log('删除志愿地点信息接口返回', res)
  if (res.data === 1) {
    ElMessage.success('删除成功')
    getVoluntPlace()
  } else {
    ElMessage.error('删除失败')
  }
}
//点击添加地点信息
const newplace = ref()
const dialogVisible = ref(false) // 控制对话框显示
import { addplaceService } from '@/api/user'
const addVoluntPlace = () => {
  dialogVisible.value = true
}
const saveEdit = async () => {
  console.log('点击保存新的志愿地点信息', newplace.value)
  const res = await addplaceService(newplace.value)
  console.log('添加志愿地点信息接口返回', res)
  if (res.data === 1) {
    ElMessage.success('添加成功')
    dialogVisible.value = false
    getVoluntPlace()
  } else {
    ElMessage.error('添加失败')
  }
}
</script>

<template>
  <el-table :data="voluntplacetemp" border style="width: 100%">
    <el-table-column prop="locationInfo" label="志愿地点信息" />
    <el-table-column fixed="right" min-width="120" label="操作">
      <template v-slot:default="scope">
        <el-button type="primary" size="small" @click="handleDelete(scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
  <el-button type="primary" size="small" @click="addVoluntPlace()"
    >添加志愿地点</el-button
  >
  <el-table :data="volunteerlist" border style="width: 100%">
    <el-table-column prop="UserAccount" label="账号" width="180" />
    <el-table-column prop="UserName" label="姓名" />
    <!-- <el-table-column prop="UserMessage" label="志愿信息" width="180">
      <template v-slot:default="scope">
        <el-input v-model="scope.row.UserMessage" />
      </template>
    </el-table-column> -->
    <el-table-column label="志愿信息" width="180">
      <!-- 使用下拉框选择志愿地点 -->
      <template v-slot:default="scope">
        <el-select v-model="scope.row.UserMessage" placeholder="选择志愿地点">
          <el-option
            v-for="item in voluntplace.value"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
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
  <!-- 添加对话框组件 -->
  <el-dialog
    title="添加志愿地点信息"
    v-model="dialogVisible"
    width="30%"
    @close="resetForm"
  >
    <el-input
      v-model="newplace"
      placeholder="请输入新的志愿地点信息"
    ></el-input>
    <template v-slot:footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEdit">保存</el-button>
      </span>
    </template>
  </el-dialog>
</template>
