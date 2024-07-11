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
