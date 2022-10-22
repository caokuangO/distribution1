package com.springbucks.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springbucks.entity.Student;
import com.springbucks.mapper.StudentMapper;
import com.springbucks.util.PageRequest;
import com.springbucks.util.PageResult;
import com.springbucks.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: caokuang
 * @Date: 2022/10/21 22:44
 * @Version 1.0
 */
@Service("StudentService")
public class StudentImpl implements StudentService{

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 查询学生信息
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "user",key = "#id")
    public Student selectStudentById(Integer id) {
        return studentMapper.selectStudentById(id);
    }

    /**
     * 新增学生信息
     * @param student
     * @return
     */
    @Override
    @Transactional
    public int insertStudent(Student student) {
        return studentMapper.insertStudent(student);
    }

    /**
     * 更新用户信息
     * @param student
     * @return
     */
    @Override
    @Transactional
    public int updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int deleteStudent(Integer id) {
        return studentMapper.deleteStudentById(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    /**
     * 调用分页插件完成分页
     * @param pageQuery
     * @return
     */
    private PageInfo<Student> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Student> sysUser = studentMapper.selectPage();
        return new PageInfo<Student>(sysUser);
    }

}
