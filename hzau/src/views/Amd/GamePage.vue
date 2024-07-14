<script setup>
import { ref } from 'vue'

import { onMounted } from 'vue'
import { gameInfoService, deletegameService, editgameService } from '@/api/user'
import { ElMessage } from 'element-plus'
const gamelist = ref([])

//进入页面自动发请求获取数据
onMounted(() => {
  //调用getInfo函数
  getInfo()
})
//获取信息
const getInfo = async () => {
  const res = await gameInfoService()
  console.log('查询比赛信息接口返回：', res)
  gamelist.value = res.data
  console.log(gamelist.value)
}

//删除比赛
const handleDelete = async (row) => {
  console.log(1)
  console.log(row)
  console.log(row.RacePlace)
  console.log(row.RaceName)
  console.log(row.RaceLevel)
  console.log(row.RaceTime)
  const res = await deletegameService({
    RacePlace: row.RacePlace,
    RaceName: row.RaceName,
    RaceLevel: row.RaceLevel,
    RaceTime: row.RaceTime
  })
  console.log('删除比赛接口返回', res)
  if (res.data === 1) {
    //提示删除成功
    ElMessage.success('删除成功')
    getInfo()
  } else {
    ElMessage.success('删除失败')
  }
}

//修改比赛功能
const dialogVisible = ref(false) // 控制对话框显示
const editFormData = ref({}) // 存储编辑中的比赛信息
const handleEdit = (row) => {
  console.log('camein')
  // editFormData.value.RaceLevel = row.RaceLevel
  // editFormData.value.RaceName = row.RaceName
  // editFormData.value.RacePlace = row.RacePlace
  // editFormData.value.RaceTime = row.RacePlace
  // editFormData.value.selectedDate=''
  // editFormData.value.selectedTimeRange=[]
  editFormData.value = row
  // 复制当前行数据到editFormData
  dialogVisible.value = true
  console.log('handleEdit完成')
}
const resetForm = () => {
  // this.$refs['editForm'].resetFields()
  editFormData.value = {} // 清空编辑数据
}
const saveEdit = async () => {
  // 实现保存逻辑，如调用后端接口更新数据
  // this.updateRace(this.editFormData).then(() => {
  //   this.dialogVisible = false; // 关闭对话框
  //   this.$message.success('保存成功');
  // });
  // editFormData.value.RaceTime=`${editFormData.value.selectedDate} ${editFormData.value.selectedTimeRange[0]}-${editFormData.value.selectedTimeRange[1]}`
  // editFormData.value.RaceTime=formatDateRange(`${editFormData.value.selectedDate} ${editFormData.value.selectedTimeRange[0]}`,
  // `${editFormData.value.selectedTimeRange[1]}`
  // )
  const a = editFormData.value
  console.log('a:', a)
  const res = await editgameService({
    RaceLevel: a.RaceLevel,
    RaceName: a.RaceName,
    RacePlace: a.RacePlace,
    RaceTime: a.RaceTime
  })
  console.log('修改比赛接口返回', res)
  console.log('保存编辑:', editFormData.value)
  if (res.data === 1) {
    ElMessage.success('修改成功')
    console.log('修改成功')
    dialogVisible.value = false // 示例中直接关闭对话框
    getInfo()
  } else {
    ElMessage.error('保存失败')
  }
}
// const formatDateRange = (startDateStr, endDateStr) => {
//   const startDate = new Date(startDateStr);
//   const endDate = new Date(endDateStr);

//   const yearStart = startDate.getFullYear();
//   const monthStart = String(startDate.getMonth() + 1).padStart(2, '0');
//   const dayStart = String(startDate.getDate()).padStart(2, '0');
//   const hourStart = String(startDate.getHours()).padStart(2, '0');

//   const yearEnd = endDate.getFullYear();
//   const monthEnd = (yearStart === yearEnd && monthStart === String(endDate.getMonth() + 1).padStart(2, '0') && dayStart === String(endDate.getDate()).padStart(2, '0')) ? '' : String(endDate.getMonth() + 1).padStart(2, '0');
//   const dayEnd = (yearStart === yearEnd && monthStart === String(endDate.getMonth() + 1).padStart(2, '0') && dayStart === String(endDate.getDate()).padStart(2, '0')) ? '' : String(endDate.getDate()).padStart(2, '0');
//   const hourEnd = String(endDate.getHours()).padStart(2, '0');

//   // 确保正确使用monthEnd和dayEnd
//   const formattedStartDate = `${yearStart}.${monthStart}.${dayStart} ${hourStart}:${String(startDate.getMinutes()).padStart(2, '0')}`;
//   const formattedEndDate = monthEnd || dayEnd ? `${yearEnd}.${monthEnd || monthStart}.${dayEnd || dayStart} ${hourEnd}:${String(endDate.getMinutes()).padStart(2, '0')}` : `${hourEnd}:${String(endDate.getMinutes()).padStart(2, '0')}`;

//   // 返回格式化后的日期范围字符串
//   return formattedStartDate !== formattedEndDate ? `${formattedStartDate}-${formattedEndDate}` : formattedStartDate;
// };
</script>

<template>
  <h3>赛事信息：</h3>
  <el-table :data="gamelist" border style="width: 100%">
    <el-table-column prop="RaceName" label="比赛名称" width="180" />
    <el-table-column prop="RaceLevel" label="比赛级别" />
    <el-table-column prop="RaceTime" label="比赛时间" width="180" />
    <el-table-column prop="RacePlace" label="比赛地点" />
    <el-table-column fixed="right" min-width="120" label="操作">
      <template v-slot:default="scope">
        <el-button type="primary" size="small" @click="handleEdit(scope.row)">
          修改
        </el-button>
        <el-button type="primary" size="small" @click="handleDelete(scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
  <!-- 添加对话框组件 -->
  <el-dialog
    title="修改比赛信息"
    v-model="dialogVisible"
    width="30%"
    @close="resetForm"
  >
    <el-form ref="editForm" :model="editFormData" label-width="80px">
      <el-form-item label="比赛名称">
        <el-input v-model="editFormData.RaceName" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="比赛级别">
        <el-input v-model="editFormData.RaceLevel" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="比赛时间">
        <el-input v-model="editFormData.RaceTime"></el-input>
      </el-form-item>
      <!-- <el-form-item label="比赛时间">
        <el-date-picker
          v-model="editFormData.RaceTime"
          type="datetime"
          placeholder="选择比赛时间"
        >
        </el-date-picker>
      </el-form-item> -->
      <!-- <el-form-item label="比赛日期">
  <el-date-picker
    v-model="editFormData.selectedDate"
    type="date"
    placeholder="选择日期"
    @change="handleDateChange"
  ></el-date-picker>
</el-form-item>
<el-form-item label="比赛时间">
  <el-time-picker
  is-range
    v-model="editFormData.selectedTimeRange"
    range-separator="至"
    start-placeholder="开始时间"
    end-placeholder="结束时间"
    @change="handleTimeChange"
  ></el-time-picker>
</el-form-item> -->

      <el-form-item label="比赛地点">
        <el-input v-model="editFormData.RacePlace"></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEdit">保存</el-button>
      </span>
    </template>
  </el-dialog>
</template>
