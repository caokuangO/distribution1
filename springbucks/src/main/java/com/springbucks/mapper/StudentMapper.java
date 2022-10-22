package com.springbucks.mapper;

import com.springbucks.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @Author: caokuang
 * @Date: 2022/10/21 22:44
 * @Version 1.0
 */
@Mapper
public interface StudentMapper {
    /**
     * 查询用户管理信息
     */
    public Student selectStudentById(Integer id);

    /**
     * 新增用户管理
     *
     */
    public int insertStudent(Student student);

    /**
     * 修改用户管理
     *
     */
    public int updateStudent(Student student);

    /**
     * 删除用户管理
     *
     */
    public int deleteStudentById(Integer id);

    /**
     * 分页查询用户
     * @return
     */
    List<Student> selectPage();

}
