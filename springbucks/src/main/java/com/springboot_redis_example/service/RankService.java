package com.springboot_redis_example.service;

import com.springboot_redis_example.entity.Student;
import com.springboot_redis_example.entity.UserScore;
import com.springboot_redis_example.util.PageRequest;
import com.springboot_redis_example.util.PageResult;

import java.util.List;


/**
 * @Author: caokuang
 * @Date: 2022/10/21 22:44
 * @Version 1.0
 */
public interface RankService {

    public List<UserScore> getUserScoreList();

    public Long insertUserScore(UserScore userScore);
}
