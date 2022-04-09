package com.atguigu.crud.controller;

import com.atguigu.crud.bean.Department;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.bean.Student;
import com.atguigu.crud.dao.DepartmentMapper;
import com.atguigu.crud.dao.StudentMapper;
import com.atguigu.crud.service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//手动加载ioc容器
public class StudentConroller {
    @Autowired
    private StudentService studentService;

    /**
     * 根据复选框批量删除
     */
    @ResponseBody
    @RequestMapping(value="/stus/{ids}",method=RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids")String ids){
        //批量删除
        if(ids.contains("-")){
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            //将所有学生id组装成集合
            for (String string : str_ids) {
                del_ids.add(Integer.parseInt(string));
            }
            studentService.deleteBatch(del_ids);
        }else{
            Integer id = Integer.parseInt(ids);
            studentService.deleteById(id);
        }
        return Msg.success();
    }

    /**
     * 删除单个学生
     * @param id
     * @return
     */
    @RequestMapping(value = "/stu/{id}",method =RequestMethod.DELETE )
    @ResponseBody
    public Msg deleteStu(@PathVariable(value = "id") Integer id){
        studentService.deleteById(id);
        return Msg.success();
    }

    /**
     * 修改学生信息
     * @param id
     * @param student
     * @return
     */
    @RequestMapping(value = "/putStu/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Msg putStu(@PathVariable(value = "id") Integer id,Student student){
        student.setStuId(id);
        studentService.update(student);
        return Msg.success();
    }

    @RequestMapping(value = "/getStuById",method = RequestMethod.GET)
    @ResponseBody
    public Msg getStuById(@RequestParam(value = "id") Integer id){
        Student student=studentService.getStudentById(id);
        return Msg.success().add("stuData",student);
    }

    @RequestMapping(value = "/emps",method = RequestMethod.GET)
    /**
     * 导入jackson包
     */
    @ResponseBody
    private Msg getStusWithJosn(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){
        PageHelper.startPage(pn,5);
//        需要分页查询
        List<Student> allStudentList = studentService.getAllStudentList();
//        封装了包装的详细信息,连续显示5页
        PageInfo pageInfo = new PageInfo(allStudentList,5);

        return Msg.success().add("pageInfo",pageInfo);
    }

    @ResponseBody
    @RequestMapping(value = "/emps",method = RequestMethod.POST)
    public Msg addStu(@Valid Student student, BindingResult result){

        if (result.hasErrors()) {

            //JSR303校验结果有错误时，将JSR303校验结果封装在map中

            Map<String, Object> map = new HashMap<String, Object>();
            //获取所有有错误的字段
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                //getFiled表示错误的字段名
                //getDefaultMessage表示JavaBean定义的错误信息
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("emsg", map);
        } else if (!studentService.checkUserName(student.getStuName())){
            Map map=new HashMap<String,Object>();
            map.put("stuName","数据库有重复名字");
            return Msg.fail().add("emsg",map);
        }else {
            //校验结果无误时
            studentService.addStudent(student);
            return Msg.success();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkuserName",method = RequestMethod.GET)
    public Msg checkUser(@RequestParam(value = "stuName") String stuName){
        if(studentService.checkUserName(stuName)){
           return Msg.success();
        }
        return Msg.fail();
    }
//    @RequestMapping(value = "/emps")
    public  String getEmps(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){
//        引入分页插件
        PageHelper.startPage(pn,5);
//        需要分页查询
        List<Student> allStudentList = studentService.getAllStudentList();
//        封装了包装的详细信息,连续显示5页
        PageInfo pageInfo = new PageInfo(allStudentList,5);
        model.addAttribute("pageInfo",pageInfo);
        return "list";
    }
}
