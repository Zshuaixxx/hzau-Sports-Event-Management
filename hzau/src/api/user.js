import request from '@/utils/request'
// import { List } from '@element-plus/icons-vue/dist/types'
//登录接口
export const userLoginService = ({ AdminPassword, AdminAccount }) => {
  return request.post('/login', { AdminPassword, AdminAccount })
}

//获取比赛信息
export const gameInfoService = () => {
  return request.get('/gameInfo')
}
//删除比赛信息
export const deletegameService = ({
  RacePlace,
  RaceName,
  RaceLevel,
  RaceTime
}) => {
  return request.post('/race/delete', {
    RacePlace,
    RaceName,
    RaceLevel,
    RaceTime
  })
}
//编辑比赛信息
export const editgameService = ({
  RacePlace,
  RaceName,
  RaceLevel,
  RaceTime
}) => {
  console.log('发送:', { RacePlace, RaceName, RaceLevel, RaceTime })
  return request.post('race/change', {
    RacePlace,
    RaceName,
    RaceLevel,
    RaceTime
  })
}

//获取志愿者信息
export const volunteerInfoService = () => {
  return request.get('/volunteerInfo')
}

//修改志愿者信息
export const volunteerEditService = ({
  UserAccount,
  UserName,
  UserMessage,
  UserPhoneNumber,
  VolunteerTime
}) => {
  return request.post('/volunteer/change', {
    UserAccount,
    UserName,
    UserMessage,
    UserPhoneNumber,
    VolunteerTime
  })
}

//获取志愿时间
export const timeInfoService = (twoday) => {
  return request.get('volunteer/changetime', twoday)
}

//修改志愿时间
export const edittimeService = (twoday) => {
  return request.post('/volunteer/changeTime', twoday)
}

//获取学院志愿地点信息
export const whereInfoService = () => {
  return request.get('/volunteer/where')
}
//修改学院志愿地点信息
export const whereEditService = ({ XueYuanName, Message }) => {
  return request.post('/volunteer/changeWhere', {
    XueYuanName,
    Message
  })
}

//获取运动员信息
export const usegetPlayerInfo = ({ RaceName }) => {
  return request.post('/game/get', {
    RaceName
  })
}

//点击分配号码牌
export const fenpeihaomaServer = () => {
  return request.get('/game/fenpeihaoma')
}

//点击一键上传运动员成绩
export const updataGradeServer = (mylist) => {
  return request.post('/game/grade', mylist, {
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

//点击上传晋级人数
export const usegetlastPlayerInfo = ({ RaceName, Sum }) => {
  return request.post('/game/lastplayer', {
    RaceName,
    Sum
  })
}

//上传决赛成绩
export const updatalastGradeServer = (mylist) => {
  return request.post('/game/lastgrade', mylist, {
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

//点击为运动员分配跑到
export const getzuhaoServer = () => {
  return request.post('/game/zuhao')
}

//点击分配决赛跑道号
export const getlasthaomaServer = ({ RaceName, Sum }) => {
  return request.post('/game/lasthaoma', {
    RaceName,
    Sum
  })
}

//获取志愿地点信息
export const voluntplaceService = () => {
  return request.post('/Volunteer/place')
}
//点击删除志愿地点
export const placedeleteService = (place) => {
  return request.post(`/Volunteer/deleteplace/${place}`)
}

//点击添加志愿地点
export const addplaceService = (VolunteerPlace) => {
  return request.post(`/Volunteer/Insertplace/${VolunteerPlace}`)
}
