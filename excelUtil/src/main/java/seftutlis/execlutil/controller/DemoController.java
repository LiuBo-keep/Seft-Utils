package seftutlis.execlutil.controller;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import seftutlis.execlutil.bean.Titles;
import seftutlis.execlutil.bean.User;
import seftutlis.execlutil.util.ExcelUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DemoController
 * @Description TODO
 * @Author liubo
 * @Date 2021/1/5 14:54
 */

@RestController
public class DemoController {
    @GetMapping("/get/eccl")
    public void get(HttpServletResponse httpServletResponse) throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(new User("1", "李四", "男", "13330"));
        userList.add(new User("2", "王五", "男", "13331"));
        userList.add(new User("3", "赵六", "男", "13332"));
        userList.add(new User("4", "田七", "男", "13333"));
        userList.add(new User("5", "老王", "男", "13334"));
        userList.add(new User("6", "嘻嘻", "男", "13335"));

        ExcelUtil<User> objectExcelUtil = new ExcelUtil<>();
        Workbook excel = objectExcelUtil.getExcel("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new Titles("编号", "姓名", "性别", "电话"), userList);
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        httpServletResponse.reset(); // 非常重要
        httpServletResponse.setContentType("application/x-msdownload");
        httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode("申请记录详情.xlsx", "UTF-8"));
        excel.write(outputStream);
        excel.close();
    }
}
