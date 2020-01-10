package com.cnlive.oicc.controller;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.oicc.bean.ResultBean;
import com.cnlive.oicc.entity.TEnrol;
import com.cnlive.oicc.service.EnrolService;
import com.cnlive.oicc.service.MemberService;
import com.cnlive.oicc.utils.CommonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping
public class EnrolController {
    private static Log logger = LogFactory.getLog(EnrolController.class);
    @Autowired
    EnrolService enrolService;
    @Autowired
    MemberService memberService;

    /**
     * 获取用户报名信息
     */
    @RequestMapping("getEnrol")
    public String getEnrol(HttpServletRequest request, HttpServletResponse response, int pageNo, String query) {
        request.getSession().setAttribute("query", null);
        request.getSession().setAttribute("query", query);
        request.setAttribute("enrolPage", enrolService.getEnrolByPage(pageNo, query));
        return "enrol";
    }

    /**
     * 提交报名信息,个人报名
     */
    @RequestMapping("enrol")
    @ResponseBody
    public ResultBean enrol(HttpServletRequest request, HttpServletResponse response, String tel, String username, int age, String company, int sex, String IdNumber) {
        Cookie[] cookies = request.getCookies();
        String userId = CommonUtils.getCookieValue("cnliveUserId", cookies);
        userId = memberService.getUserIDByEncryptID(userId);
        if (StringUtils.isEmpty(userId)) {
            return new ResultBean(-1, "用户id为空", null);
        } else {
            TEnrol tEnrol = enrolService.getOneByTel(tel);
            if (tEnrol != null) {
                return new ResultBean(-1, "该手机号用户已报名", null);
            }
            tEnrol.setMemberId(Integer.parseInt(userId));
            tEnrol.setUsername(username);
            tEnrol.setAge(age);
            tEnrol.setCompany(company);
            tEnrol.setSex(sex);
            tEnrol.setTel(tel);
            tEnrol.setIdNumber(IdNumber);
            tEnrol.setInsertDate(CommonUtils.getCurrentTimestamp());
            boolean success = enrolService.save(tEnrol);
            if (success) {
               return new ResultBean(0, "内容创建成功", null);
            } else {
                return new ResultBean(-1, "内容保存失败", null);
            }
        }
    }

    /**
     * 获取用户提交个人报名信息
     */
    @RequestMapping("getOneEnrol")
    @ResponseBody
    public ResultBean getOneEnrol(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String userId = CommonUtils.getCookieValue("cnliveUserId", cookies);
        userId = memberService.getUserIDByEncryptID(userId);
        if (!StringUtils.isEmpty(userId)) {
            TEnrol enrol = enrolService.getOneByUserId(userId);
            if (enrol != null) {
                return new ResultBean(0, "获取数据成功", enrol);
            } else {
                return new ResultBean(-1, "未提交报名单", null);
            }
        } else {
            return (new ResultBean(-1, "未登录", null));
        }

    }


    /**
     * 检测是否已上传团体报名表
     */
    @RequestMapping("isUpTeam")
    public ResultBean isUpTeam(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String userId = CommonUtils.getCookieValue("cnliveUserId", cookies);
        userId = memberService.getUserIDByEncryptID(userId);
        if (!StringUtils.isEmpty(userId)) {
            boolean isUp = enrolService.isUpTeam(userId);
            return (new ResultBean(0, "获取数据成功", isUp));
        } else {
            return (new ResultBean(-1, "未登录", null));
        }
    }

    /*
     * 下载团体报名表模板
     */
    @RequestMapping("downTeam")
    public void downTeam() {
//        String storageUrl = this.getClass().getResource("/").getPath();
//        File file = new File(storageUrl + File.separator + "cos run 团体报名表.xlsx");
//        renderFile(file);
    }


    /**
     * 团体报名表提交
     */
    /*
    @RequestMapping("enrolTeam")
    public ResultBean enrolTeam(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String userId = CommonUtils.getCookieValue("cnliveUserId", cookies);
        if (StringUtils.isEmpty(userId)) {
            return (new ResultBean(-1, "请先登录", null));
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String time = sdf.format(new Date().getTime());
            String[] strings = time.split("-");
            String path = Constants.FILE_PATH + File.separator + strings[0] + File.separator
                    + strings[1] + strings[2] + File.separator + userId;

            UploadFile upfile = getFile("uploadFile", path, 5 * 1024 * 1024 * 1024);
            if (upfile == null) {
                return (new ResultBean(-1, "请选择相应的Excel文件", null));
            }
            String fileName = upfile.getFileName();
            if (fileName == null || !fileName.endsWith(".xlsx")) {
                return (new ResultBean(-1, "文件格式不正确", null));
            }
            List<List<String>> excelList = readExcel(upfile.getFile());
            String company = excelList.get(1).get(1);
            String linkman = excelList.get(2).get(1);
            String company_tel = excelList.get(2).get(3).replaceAll(" ", "");
            boolean isAgain = false;
            String number = "";
            for (int i = 4; i < excelList.size(); i++) {
                List<String> list = excelList.get(i);
                String tel = list.get(4);
                if (StringUtils.isEmpty(tel)) {
                    break;
                }
                TEnrol tEnrol = enrolService.getOneByTel(tel);
                if (tEnrol != null) {
                    isAgain = true;
                    number = list.get(0);
                    break;
                }

            }
            //手机号唯一，有重复不插入
            if (isAgain) {
                return (new ResultBean(-1, "序号（" + number + "）手机用户已报名，请勿重复报名！", null));
            } else {
                for (int i = 4; i < excelList.size(); i++) {
                    List<String> list = excelList.get(i);
                    String tel = list.get(4);
                    if (StringUtils.isEmpty(tel)) {
                        break;
                    }
                    String username = list.get(1);
                    int age = Integer.parseInt(list.get(3).replaceAll(" ", ""));
                    String sexStr = list.get(2);
                    int sex = 0;
                    if ("女".equals(sexStr.replaceAll(" ", ""))) sex = 1;
                    TEnrol tEnrol = new TEnrol();
                    tEnrol.setMemberId(Integer.parseInt(userId) );
                    tEnrol.setUsername( username);
                    tEnrol.setAge(age);
                    tEnrol.setCompany( company);
                    tEnrol.setSex( sex);
                    tEnrol.setTel( tel);
                    tEnrol.setInsertDate( CommonUtils.getCurrentTimestamp());
                    tEnrol.setCompanyLinkman( linkman);
                    tEnrol.setCompanyTel(company_tel);
                    tEnrol.setIsTeam(1);
                    enrolService.save(tEnrol);
                }
                return (new ResultBean(0, "报名表单上传成功", null));
            }
        } catch (Exception e) {
            logger.error("团体报名表上传出错，userid==" + userId);
            return (new ResultBean(-1, "Excel文件内容存在错误，请仔细检查后再进行上传！", null));
            e.printStackTrace();
        }

    }*/


    /**
     * 读取excel
     *
     * @param file
     * @return
     */
    public static List<List<String>> readExcel(File file) {
        List<List<String>> list = new ArrayList<List<String>>();
        try {
            InputStream inputStream = new FileInputStream(file);
            String fileName = file.getName();
            Workbook wb = null;

            if (fileName.endsWith(".xlsx")) {
                wb = new XSSFWorkbook(inputStream);//Excel 2007
            } else {
                wb = (Workbook) new HSSFWorkbook(inputStream);//Excel 2003
            }
            Sheet sheet = wb.getSheetAt(0);//第一个工作表  ，第二个则为1，以此类推...
            int firstRowIndex = sheet.getFirstRowNum();
            int lastRowIndex = sheet.getLastRowNum();
            for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                Row row = sheet.getRow(rIndex);
                if (row != null) {
                    int firstCellIndex = row.getFirstCellNum();
                    // int lastCellIndex = row.getLastCellNum();
                    //此处参数cIndex决定可以取到excel的列数。
                    List<String> list2 = new ArrayList<String>();
                    for (int cIndex = firstCellIndex; cIndex < 5; cIndex++) {
                        Cell cell = row.getCell(cIndex);
                        String value = "";
                        if (cell != null) {
                            if (CellType.NUMERIC == cell.getCellTypeEnum()) {
                                DecimalFormat df = new DecimalFormat("0");
                                value = df.format(cell.getNumericCellValue());
                            } else {
                                value = cell.toString();
                            }
                            list2.add(value);
                        }
                    }
                    list.add(list2);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
