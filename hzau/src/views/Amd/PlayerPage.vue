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
import { usegetPlayerInfo } from '@/api/user'
//点击查看按钮
const getplayerinfo = async () => {
  console.log('发送的运动类别信息：', selectedSubCategory.value)
  const res = await usegetPlayerInfo({ RaceName: selectedSubCategory.value })
  console.log('返回的运动员信息：', res)
  playerList.value = res.data
}
</script>

<template>
  <div>
    <!-- 运动类别 -->
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
    <!-- 详细运动项目 -->
    <el-select v-model="selectedSubCategory" placeholder="请选择运动项目">
      <el-option
        v-for="subCategory in subCategories"
        :key="subCategory.id"
        :label="subCategory.name"
        :value="subCategory.id"
      />
    </el-select>
    <el-button type="primary" @click="getplayerinfo()">查询</el-button>
    <el-button>一键自动分配运动员号码牌</el-button>
    <el-table :data="playerList" style="width: 100%">
      <el-table-column prop="RacerName" label="姓名" width="80" />
      <el-table-column prop="XueYuanName" label="学院" />
      <el-table-column prop="RacerSex" label="性别" width="110" />
      <el-table-column prop="HaoMa" label="号码" />
      <el-table-column prop="UserAccount" label="学号" />
      <el-table-column prop="RaceName" label="项目" />
    </el-table>
  </div>
</template>
