package com.atguigu.crud.test;


import com.atguigu.crud.bean.Department;
import com.atguigu.crud.bean.Student;
import com.atguigu.crud.bean.StudentExample;
import com.atguigu.crud.dao.DepartmentMapper;
import com.atguigu.crud.dao.StudentMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MapperTest {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private SqlSession sqlSession;
    @Test
    public void testM(){
        System.out.println(departmentMapper);
//        departmentMapper.insertSelective(new Department(null,"研究部"));
//        departmentMapper.insertSelective(new Department(null,"测试部"));
//        studentMapper.insertSelective(new Student(null,"白曜溥","M","yaopu@163.com",3,null));
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        for (int i=0;i<100;i++){
            String uuid = UUID.randomUUID().toString().substring(0, 5);
            mapper.insertSelective(new Student(null,uuid,"M","yaopu"+i+"@163.com",3,null));
        }
    }
    @Test
    public void test(){
        System.out.println(departmentMapper.selectByExample(null));

    }
}
