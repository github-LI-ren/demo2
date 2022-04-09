package com.atguigu.crud.service;

import com.atguigu.crud.bean.Student;
import com.atguigu.crud.bean.StudentExample;
import com.atguigu.crud.dao.DepartmentMapper;
import com.atguigu.crud.dao.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;


    public List<Student> getAllStudentList() {

        return studentMapper.selectByExampleWithDept(null);
    }

    public void addStudent(Student student) {
        studentMapper.insertSelective(student);
    }

    public boolean checkUserName(String studentName) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andStuNameEqualTo(studentName);
        long l = studentMapper.countByExample(studentExample);
        return l==0;
    }

    public Student getStudentById(Integer id) {
        Student student = studentMapper.selectByPrimaryKey(id);
        return student;
    }

    public void update(Student student) {
        studentMapper.updateByPrimaryKeySelective(student);
    }

    public void deleteById(Integer id) {
        studentMapper.deleteByPrimaryKey(id);
    }
    //批量删除
    public void deleteBatch(List<Integer> del_ids) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();

        //delete from tbl_stu where stu_id in(1,2,3...)
        criteria.andStuIdIn(del_ids);

        studentMapper.deleteByExample(studentExample);
    }
}
