<script setup>
import { ref } from 'vue'

// 假设这是从服务器获取的数据
const categories = [
  {
    id: 1,
    name: '短跑',
    subCategories: [
      { id: 11, name: '男子50米比赛', gameid: '50mB' },
      { id: 12, name: '男子100米比赛', gameid: '100mB' },
      { id: 13, name: '男子200米比赛', gameid: '200mB' },
      { id: 14, name: '男子400米比赛', gameid: '400mB' },
      { id: 15, name: '女子50米比赛', gameid: '50mG' },
      { id: 16, name: '女子100米比赛', gameid: '100mG' },
      { id: 17, name: '女子200米比赛', gameid: '200mG' },
      { id: 18, name: '女子400米比赛', gameid: '400mG' }
    ]
  },
  {
    id: 2,
    name: '长跑',
    subCategories: [
      { id: 21, name: '男子800米比赛', gameid: '800mB' },
      { id: 22, name: '男子1000米比赛', gameid: '1000mB' },
      { id: 23, name: '男子1500米比赛', gameid: '1500mB' },
      { id: 24, name: '男子5000米比赛', gameid: '5000mB' },
      { id: 25, name: '女子800米比赛', gameid: '800mG' },
      { id: 26, name: '女子1000米比赛', gameid: '1000mG' },
      { id: 27, name: '女子1500米比赛', gameid: '1500mG' },
      { id: 28, name: '女子5000米比赛', gameid: '5000mG' }
    ]
  },
  {
    id: 3,
    name: '接力',
    subCategories: [
      { id: 31, name: '男子20x50米比赛', gameid: '20-50B' },
      { id: 32, name: '女子20x50米比赛', gameid: '20-50G' },
      { id: 33, name: '男子40x100米比赛', gameid: '40-100B' },
      { id: 34, name: '女子40x100米比赛', gameid: '40-100G' }
    ]
  },
  {
    id: 4,
    name: '跳远',
    subCategories: [
      { id: 41, name: '男子立定跳远', gameid: 'tiaoyuanB' },
      { id: 42, name: '女子立定跳远', gameid: 'tiaoyuanG' },
      { id: 43, name: '男子三级连跳', gameid: 'threetyB' },
      { id: 44, name: '女子三级连跳', gameid: 'threetyG' }
    ]
  },
  {
    id: 5,
    name: '跳高',
    subCategories: [
      { id: 51, name: '男子助跑跳高', gameid: 'tiaogaoB' },
      { id: 52, name: '女子助跑跳高', gameid: 'tiaogaoG' }
    ]
  },
  {
    id: 6,
    name: '标枪',
    subCategories: [
      { id: 61, name: '男子标枪比赛', gameid: 'biaoqiangB' },
      { id: 62, name: '女子标枪比赛', gameid: 'biaoqiangG' }
    ]
  },
  {
    id: 7,
    name: '铅球',
    subCategories: [
      { id: 71, name: '男子铅球比赛', gameid: 'qianqiuB' },
      { id: 72, name: '女子铅球比赛', gameid: 'qianqiuG' }
    ]
  }
]
const selectedCategory = ref(null)
const selectedSubCategory = ref(null)
const subCategories = ref([])

const updateSubCategories = (categoryId) => {
  subCategories.value =
    categories.find((category) => category.id === categoryId)?.subCategories ||
    []
}

// 初始化时更新子类别列表
updateSubCategories(selectedCategory.value)

//运动员信息
const playerList = ref([])
import { usegetlastPlayerInfo } from '@/api/user'
//点击查看按钮
const getplayerinfo = async () => {
  console.log(manygolast.value)
  console.log(typeof manygolast.value)
  if (manygolast.value == 0) {
    ElMessage.error('请确认晋级人数输入正确：')
  }
  if (typeof manygolast.value !== 'number') {
    ElMessage.error('晋级人数请输入数字')
    return
  }
  console.log('决赛页面发送的运动类别信息：', {
    RaceName: selectedSubCategory.value,
    Sum: manygolast.value
  })
  const res = await usegetlastPlayerInfo({
    RaceName: selectedSubCategory.value,
    Sum: manygolast.value
  })
  console.log('决赛页面返回的运动员信息：', res)
  playerList.value = res.data
}
//晋级决赛的人数
const manygolast = ref(0)
//点击一键上传所有运动员决赛成绩
import { updatalastGradeServer } from '@/api/user'
import { ElMessage } from 'element-plus'
const updataGrade = async () => {
  // const res = await updataGradeServer({
  //   RacerName: playerList.value.RacerName,
  //   XueYuanName: playerList.value.XueYuanName,
  //   RacerSex: playerList.value.RacerSex,
  //   HaoMa: playerList.value.HaoMa,
  //   UserAccount: playerList.value.UserAccount,
  //   RaceName: playerList.value.RaceName,
  //   Grade: playerList.value.Grade
  // })
  const playersData = playerList.value.map((player) => ({
    RacerName: player.RacerName,
    XueYuanName: player.XueYuanName,
    RacerSex: player.RacerSex,
    HaoMa: player.HaoMa,
    UserAccount: player.UserAccount,
    RaceName: player.RaceName,
    Grade: player.Grade,
    LGrade: player.LGrade
  }))
  console.log('成绩：', playersData)
  const res = await updatalastGradeServer(playersData)
  console.log('上传决赛成绩接口返回:', res)
  if (res.data === 0) {
    ElMessage.error
    ;('成绩录入失败')
  } else {
    ElMessage.success('成绩录入' + res.data + '位运动员决赛成绩')
  }
}
//点击一键分配决赛跑道号
import { getlasthaomaServer } from '@/api/user'
const getlasthaoma = async () => {
  console.log('分配决赛跑道号接口发送参数', {
    RaceName: selectedSubCategory.value,
    Sum: manygolast.value
  })
  const res = await getlasthaomaServer({
    RaceName: selectedSubCategory.value,
    Sum: manygolast.value
  })
  console.log('分配决赛跑道号结果：', res)
  if (res.data === 1) {
    getplayerinfo()
  } else {
    ElMessage.error('分配决赛跑道号失败')
  }
}
</script>

<template>
  <div>
    <h4>决赛成绩录入：</h4>
    <!-- 运动类别 -->
    <div class="leibie">
      <span>运动类别: </span>
      <el-select
        v-model="selectedCategory"
        placeholder="请选择运动类别"
        @change="updateSubCategories"
      >
        <el-option
          v-for="category in categories"
          :key="category.id"
          :label="category.name"
          :value="category.id"
        />
      </el-select>
    </div>
    <!-- 详细运动项目 -->
    <div class="xiangmu">
      <span>运动项目: </span>
      <el-select v-model="selectedSubCategory" placeholder="请选择运动项目">
        <el-option
          v-for="subCategory in subCategories"
          :key="subCategory.id"
          :label="subCategory.name"
          :value="subCategory.gameid"
        />
      </el-select>
    </div>
    <div class="renshu">
      <span>请输入该项目晋级决赛人数:</span
      ><el-input v-model="manygolast" type="number" />
    </div>
    <el-button type="primary" @click="getplayerinfo()" class="chaxun"
      >查询</el-button
    >
    <el-button type="primary" @click="updataGrade()"
      >一键上传决赛运动员成绩</el-button
    >
    <span class="tishi"
      >提示您录入时，所有成绩距离以米为单位，时间以秒为单位</span
    >
    <el-table :data="playerList" style="width: 100%">
      <el-table-column prop="RacerName" label="姓名" width="80" />
      <el-table-column prop="XueYuanName" label="学院" />
      <el-table-column prop="RacerSex" label="性别" width="110" />
      <el-table-column prop="HaoMa" label="号码" />
      <el-table-column prop="UserAccount" label="学号" />
      <el-table-column prop="Grade" label="初赛成绩" />
      <el-table-column prop="Strack" label="跑道号" />

      <!-- <el-table-column prop="RaceName" label="项目" /> -->
      <el-table-column prop="LGrade" label="决赛成绩">
        <template v-slot:default="scope">
          <el-input v-model="scope.row.LGrade" />
        </template>
      </el-table-column>
    </el-table>

    <div class="paodaohao_box">
      <el-button type="primary" @click="getlasthaoma()" class="paodaohao"
        >一键分配决赛跑道号</el-button
      >
    </div>
  </div>
</template>

<style>
.tishi {
  color: red;
}
.leibie,
.xiangmu {
  display: flex;
  white-space: nowrap;
  align-items: center;
  line-height: 32px;
  margin-bottom: 10px;
}
.renshu {
  display: flex;
  white-space: nowrap;
  align-items: center;
  margin-bottom: 15px;
}
.chaxun {
  margin-left: 140px;
  margin-right: 40px;
}
.paodaohao_box {
  display: flex;
  justify-content: end;
}
.paodaohao {
  margin-right: 40px;
  margin-top: 20px;
}
</style>
