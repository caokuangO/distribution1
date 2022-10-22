package com.springboot_redis_example.service;

import com.springboot_redis_example.entity.UserScore;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("RankService")
public class RankFunctionImpl implements RankService {

    Jedis jedis = new Jedis("127.0.0.1", 6379);

    @Override
    public List<UserScore> getUserScoreList() {
        // 按照用户分数多少排行，取出前五名
        Set<Tuple> tuples = jedis.zrevrangeWithScores("user-score", 0, 4);
        List<UserScore> list = new ArrayList<>();
        int ranking = 0;
        for (Tuple tuple : tuples) {
            UserScore us = new UserScore();
            String element = tuple.getElement();
            String[] arr = element.split("#");
            us.setScore((int) tuple.getScore());
            ranking++;
            us.setRanking(ranking);
            us.setName(arr[1]);
            us.setImg(arr[2]);
            list.add(us);
        }
        return list;
    }

    @Override
    public Long insertUserScore(UserScore userScore) {
        String value = userScore.getUserid()+"#"+userScore.getName()+"#"+userScore.getImg();
        final Long zadd = jedis.zadd("user-score", userScore.getScore(), value);
        return zadd;
    }
}
