package com.springboot_redis_example.controller;

import com.springboot_redis_example.entity.Student;
import com.springboot_redis_example.service.StudentService;
import com.springboot_redis_example.util.PageRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: caokuang
 * @Date: 2022/10/21 22:44
 * @Version 1.0
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;
    @ApiOperation("根据id获取一名学生")// 为每个handler添加方法功能描述
    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable("id") Integer id){
        return studentService.selectStudentById(id);
    }

    @ApiOperation("添加一名学生")// 为每个handler添加方法功能描述
    @PostMapping("/student/add")
    @ApiImplicitParam(name = "student", value = "所添加的学生", dataTypeClass = Student.class)
    public int insertStudent(Student student){
        return studentService.insertStudent(student);
    }

    @ApiOperation("分页查询")
    @PostMapping(value="/findPage")
    public Object findPage(@RequestBody PageRequest pageQuery) {
        return studentService.findPage(pageQuery);
    }

}
