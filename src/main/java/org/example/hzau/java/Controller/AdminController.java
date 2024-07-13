package org.example.hzau.java.Controller;
import java.util.*;

import org.apache.poi.ss.formula.atp.Switch;
import org.example.hzau.java.Entity.*;
import org.example.hzau.java.Service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminServiceImpl adminService;

    @PostMapping("/login")
    public int test(@RequestBody Admin admin) {
        System.out.println(admin);
        String AdminAccount = admin.getAdminAccount();
        String AdminPassword = admin.getAdminPassword();
        int result = adminService.AdminLogin(AdminAccount, AdminPassword);
        if (result == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    @GetMapping("/gameInfo")
    public List<Race> gameInfo() {
        List<Race> races = adminService.SelectRace();
        return races;
    }

    /*管理员删除比赛*/
    @PostMapping("/race/delete")
    public int DeleteRace(@RequestBody Race race) {
        return adminService.DeleteRace(race);
    }

    /*管理员修改比赛内容*/
    @PostMapping("/race/change")
    public int ChangeRace(@RequestBody Race race) {
        System.out.println(race);
        System.out.println(adminService.ChangeRace(race));
        return adminService.ChangeRace(race);
    }

    /*管理员展示所有志愿者信息*/
    @GetMapping("/volunteerInfo")
    public List<Volunteer> SelectVolunteer() {
        return adminService.SelectVolunteer();
    }

    /*管理员为报名者分配详细信息*/
    @PostMapping("/volunteer/change")
    public int VolunteerChange(@RequestBody Volunteer volunteer) {
        System.out.println(volunteer);
        return adminService.VolunteerChange(volunteer);
    }

    /*管理员获取志愿活动在哪两日展开*/
    @GetMapping("/volunteer/changetime")
    public List<VolunteerTime> SelectTime() {
        return adminService.SelectVolunteerTime();
    }

    /*管理员修改志愿展开的日期*/
    @PostMapping("/volunteer/changeTime")
    public int ChangeTime(@RequestBody VolunteerTime volunteerTime) {
        return adminService.ChangeTime(volunteerTime);
    }

    /*管理员查询座位信息*/
    @GetMapping("/volunteer/where")
    public List<SeatMessage> SelectMessage() {
        return adminService.SelectMessage();
    }

    @PostMapping("/volunteer/changeWhere")
    public int ChangeWhere(@RequestBody SeatMessage seatMessage) {
        return adminService.ChangeWhere(seatMessage);
    }
    /*管理员导入报名信息*/

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String filePath = "C:\\Users\\dell\\Desktop\\报名\\" + file.getOriginalFilename();
                Path path = Paths.get(filePath);
                Files.write(path, file.getBytes());

                try (InputStream inputStream = file.getInputStream();
                     Workbook workbook = new XSSFWorkbook(inputStream)) {

                    Sheet sheet = workbook.getSheetAt(0);
                    Iterator<Row> rowIterator = sheet.iterator();
                    int rowNumber = 0;

                    while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();
                        rowNumber++;
                        if (rowNumber == 1) continue;

                        System.out.println("Processing row: " + rowNumber);
                        Iterator<Cell> cellIterator = row.cellIterator();
                        int cellNumber = 0;
                        Racer racer = new Racer();

                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            cellNumber++;

                            // 添加单元格值读取逻辑
                            String cellValue;
                            if (cell.getCellType() == CellType.STRING) {
                                cellValue = cell.getStringCellValue();
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                cellValue = String.valueOf((int) cell.getNumericCellValue());
                            } else {
                                // 处理其他类型，例如日期、公式等
                                cellValue = ""; // 或者根据需要处理
                            }

                            System.out.println("Cell [" + rowNumber + ", " + cellNumber + "]: " + cellValue);

                            switch (cellNumber) {
                                case 1:
                                    racer.setRacerName(cellValue);
                                    break;
                                case 2:
                                    racer.setUserAccount(cellValue);
                                    break;
                                case 3:
                                    racer.setRacerSex(cellValue);
                                    break;
                                case 4:
                                    racer.setXueYuanName(cellValue);
                                    break;
                                case 5:
                                    racer.setRaceName(cellValue);
                                    break;
                                case 6:
                                    racer.setHaoMa(cellValue);
                                    break;
                            }
                        }

                        if (adminService.InsertRacer(racer) == 1) {
                            System.out.println("插入成功");
                        } else {
                            System.out.println("插入失败");
                        }
                    }

                    return "Data read successfully";
                }
            } catch (IOException e) {
                return "Error uploading or reading file: " + e.getMessage();
            }
        } else {
            return "Empty file";
        }
    }

    /*管理员展示还未分配号码牌的个人报名信息*/
    @PostMapping("/game/get")
    public List<Racer> SetHaoMa(@RequestBody Race race) {
        String RaceName = race.getRaceName();
        return adminService.SetHaoMa(RaceName);
    }

    /*管理员自动分配所有参赛者号码牌，按学院分配*/
    @GetMapping("/game/fenpeihaoma")
    public int SetHaoMa1() {
        String flag = null;
        List<Racer> racers = adminService.SetHaoMa1();
        System.out.println(racers);
        Racer racer;
        int i = 0;
        int sum = racers.size();
        int xinxi = 10;
        int zhike = 10;
        int dongke = 10;
        int zihuan = 10;
        int shengke = 10;
        int yuanling = 10;
        int jingguan = 10;
        int gongxue = 10;
        int shuichan = 10;
        int shike = 10;
        int huaxue = 10;
        int wenfa = 10;
        int mayuan = 10;
        int gongguan = 10;
        int waiyuan = 10;
        while (i < sum) {
            racer = racers.get(i);
            switch (racer.getXueYuanName()) {
                case "信息学院":
                    flag = "01" + xinxi;
                    xinxi++;
                    System.out.println(flag);
                    break;
                case "动科动医学院":
                    flag = "02" + dongke;
                    dongke++;
                    break;
                case "资源与环境学院":
                    flag = "03" + zhike;
                    zhike++;
                    break;
                case "植物科学技术学院":
                    flag = "04" + zhike;
                    zhike++;
                    break;
                case "生命科学与技术学院":
                    flag = "05" + shengke;
                    shengke++;
                    break;
                case "园艺林学学院":
                    flag = "06" + yuanling;
                    yuanling++;
                    break;
                case "经济管理学院":
                    flag = "07" + jingguan;
                    jingguan++;
                    break;
                case "工学院":
                    flag = "08" + gongxue;
                    gongxue++;
                    break;
                case "水产学院":
                    flag = "09" + shuichan;
                    shuichan++;
                    break;
                case "化学学院":
                    flag = "10" + huaxue;
                    huaxue++;
                    break;
                case "文法学院":
                    flag = "11" + wenfa;
                    wenfa++;
                    break;
                case "外国语学院":
                    flag = "12" + waiyuan;
                    waiyuan++;
                    break;
                case "马克思主义学院":
                    flag = "13" + mayuan;
                    mayuan++;
                    break;
                case "公共管理学院":
                    flag = "14" + gongguan;
                    gongguan++;
                    break;
                default:
                    int a;
            }
            racer.setHaoMa(flag);
            adminService.UpdateRacer(racer);
            i++;
        }
        return sum;
    }

    /*管理员录入相应比赛的成绩*/
    @PostMapping("/game/grade")
    public int UpdateGrade(@RequestBody List<Racer> racers) {
        System.out.println(racers);

        return adminService.UpdateRaceGrade(racers);

    }

    /*管理员通过输入的人数来确定晋级的人选，sql语句按成绩排序*/
    @PostMapping("/game/lastplayer")
    public List<Racer> Winner(@RequestBody Winner winner) {
        int sum = winner.getSum();
        String RaceName = winner.getRaceName();
        Set<String> raceNames = new HashSet<>(Arrays.asList(
                "50mB", "50mG", "100mB", "100mG", "200mB", "200mG",
                "400mB", "400mG", "800mG", "800mB", "1000mB", "1000mG",
                "1500mB", "1500mG", "2000mB", "2000mG", "5000mB", "5000mG",
                "20-50mB", "20-50mG", "4-100mB", "4-100mG"
        ));

        if (raceNames.contains(RaceName)) {
            List<Racer> mylist = adminService.Winner(winner);
            mylist = mylist.subList(0, sum);
            return mylist;
        } else {
            List<Racer> mylist = adminService.Winner2(winner);
            System.out.println(mylist);
            mylist = mylist.subList(0, sum);
            return mylist;

        }
    }

    /*输入决赛成绩*/
    @PostMapping("/game/lastgrade")
    public int UpdateGrade1(@RequestBody List<Racer> racers) {
        System.out.println(racers);
        return adminService.UpdateRaceGrade2(racers);
    }

    /*给所有运动员分组*/
    @PostMapping("/game/group")
    public int Group() {
        Set<String> raceNames = new HashSet<>(Arrays.asList(
                "50mB", "50mG", "100mB", "100mG", "200mB", "200mG",
                "400mB", "400mG", "800mG", "800mB", "1000mB", "1000mG",
                "1500mB", "1500mG", "2000mB", "2000mG", "5000mB", "5000mG",
                "20-50mB", "20-50mG", "4-100mB", "4-100mG"
        ));
        int ljs = 0;
        int a = 1, b = 1, c = 1, d = 1, e = 1, f = 1, g = 1, h = 1, i = 1, j = 1, k = 1, l = 1, m = 1, n = 1, o = 1, p = 1, q = 1, r = 1, s = 1, t = 1, u = 1, v = 1, w = 1, x = 1;
        int a1 = 1, b1 = 1, c1 = 1, d1 = 1, e1 = 1, f1 = 1, g1 = 1, h1 = 1, i1 = 1, j1 = 1, k1 = 1, l1 = 1, m1 = 1, n1 = 1, o1, p1 = 1, q1 = 1, r1 = 1, s1 = 1, t1 = 1, u1 = 1, v1 = 1, w1 = 1, x1 = 1;
        List<Racer> Mylist = adminService.SetHaoMa1();
        for (ljs = 0; ljs < Mylist.size(); ljs++) {
            Racer racer = Mylist.get(ljs);
            if (raceNames.contains(racer.getRaceName())) {
                switch (racer.getRaceName()) {
                    case "50mB":
                        if (a <= 8) {
                            racer.setFtrack(a++);
                            racer.setGGroup(a1);
                        } else {
                            a = 1;
                            racer.setFtrack(a++);
                            racer.setGGroup(++a1);
                        }
                        adminService.Update(racer);
                        break;
                    case "50mG":
                        if (b <= 8) {
                            racer.setFtrack(b++);
                            racer.setGGroup(b1);
                        } else {
                            b = 1;
                            racer.setFtrack(b++);
                            racer.setGGroup(++b1);
                        }
                        adminService.Update(racer);
                        break;
                    case "100mB":
                        if (c <= 8) {
                            racer.setFtrack(c++);
                            racer.setGGroup(c1);
                        } else {
                            c = 1;
                            racer.setFtrack(c++);
                            racer.setGGroup(++c1);
                        }
                        adminService.Update(racer);
                        break;
                    case "100mG":
                        if (d <= 8) {
                            racer.setFtrack(d++);
                            racer.setGGroup(d1);
                        } else {
                            d = 1;
                            racer.setGGroup(++d1);
                            racer.setFtrack(d++);
                        }
                        adminService.Update(racer);
                        break;
                }
            }
        }
        return 1;
    }

    /*晋级组跑道分配*
     */
    @PostMapping("/game/fenpeihaoma")
    public int SetHaoMa1(@RequestBody  Winner winner) {
        System.out.println(winner.Sum);
        Race race = new Race();
        race.RaceName= winner.getRaceName();
        List<Racer> racerList = adminService.SuccessGroup(race);
        System.out.println(racerList);
        Set<String> raceNames = new HashSet<>(Arrays.asList(
                "50mB", "50mG", "100mB", "100mG", "200mB", "200mG",
                "400mB", "400mG", "800mG", "800mB", "1000mB", "1000mG",
                "1500mB", "1500mG", "2000mB", "2000mG", "500"));
        int a = 1, b = 1, c = 1, d = 1, e = 1, f = 1, g = 1, h = 1, i = 1, j = 1, k = 1, l = 1, m = 1, n = 1, o = 1, p = 1, q = 1, r = 1, s = 1, t = 1, u = 1, v = 1, w = 1, x = 1;
        for (int ljs = 0; ljs < winner.Sum; ljs++) {
            Racer racer = racerList.get(ljs);
            if (raceNames.contains(racer.getRaceName())) {
                System.out.println("succcccc");
                switch (racer.getRaceName()) {
                    case "50mB":
                        racer.setStrack(a++);
                        System.out.println(racer);
                        break;
                    case "100mB":
                        racer.setStrack(b++);
                        break;
                }

            }
            adminService.UpdateRacer2(racer);
        }
        return 1;
    }
    /*管理员输入志愿地点，避免多次重复输入*/
    @PostMapping("/Volunteer/Insertplace/{VolunteerPlace}")
    public int VolunteerChange(@PathVariable String VolunteerPlace) {
        System.out.println(VolunteerPlace);
        Jedis jedis = new Jedis("localhost", 6379);
        String ListName=new String("VolunteerTime");
        jedis.rpush(ListName,VolunteerPlace);
        return 1;
    }
    @PostMapping("/Volunteer/place")
    public List<String> VolunteerSelect() {
        Jedis jedis = new Jedis("localhost", 6379);
        String ListName=new String("VolunteerTime");
        List<String> list = jedis.lrange(ListName, 2, -1);
        return list;
    }
    @PostMapping("/Volunteer/deleteplace/{place}")
    public int deleteVolunteer(@PathVariable String place){
        Jedis jedis = new Jedis("localhost", 6379);
        String ListName=new String("VolunteerTime");
        jedis.lrem(ListName,1,place);
        return 1;
    }


}