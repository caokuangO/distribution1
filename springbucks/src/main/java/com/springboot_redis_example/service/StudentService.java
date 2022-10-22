package com.springboot_redis_example.service;

import com.springboot_redis_example.entity.Student;
import com.springboot_redis_example.util.PageRequest;
import com.springboot_redis_example.util.PageResult;


/**
 * @Author: caokuang
 * @Date: 2022/10/21 22:44
 * @Version 1.0
 */
public interface StudentService {

    /*
     *查询数据
     */
    public Student selectStudentById(Integer id);
    //写入数据
    public int insertStudent(Student student);
    //更新数据
    public int updateStudent(Student student);
    //删除数据
    public int deleteStudent(Integer id);
    /**
     * 分页查询接口
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
     * @param pageRequest 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    PageResult findPage(PageRequest pageRequest);
}
