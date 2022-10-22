package com.springbucks.service;

import com.springbucks.entity.UserScore;

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
