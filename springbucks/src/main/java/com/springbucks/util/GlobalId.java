package com.springbucks.util;

import java.util.HashSet;
import java.util.Set;

public class GlobalId {
    private static GlobalId globalId = new GlobalId();

    // 起始的时间戳
    private final static long START_STMP = 1579305600000L;//毫秒时间戳41位转化成
    // 每一部分占用的位数，就三个
    private final static long SEQUENCE_BIT = 12;// 序列号占用的位数
    private final static long MACHINE_BIT = 5; // 机器标识占用的位数
    private final static long DATACENTER_BIT = 5;// 数据中心占用的位数
    // 每一部分最大值
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);//支持的最大数据中心你id，结果是31
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);//支持的最大机器id，结果是31
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    // 每一部分向左的位移
    private final static long MACHINE_LEFT = SEQUENCE_BIT;//
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;//数据标识id向左移17位(12+5)
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;//时间截向左移22位(5+5+12)

    private long datacenterId = 0; // 数据中心[0, 2^DATACENTER_BIT)
    private long machineId = 0; // 机器标识[0, 2^MACHINE_BIT)
    private long sequence = 0L; // 毫秒内序列号(0~4095)
    private long lastStmp = -1L;// 上一次时间戳

    //默认构造函数
    public GlobalId() {
    }

    /**
     * !#zh
     * 构造函数(分布式每台机子每条线程都不一样参数)
     *
     * @param datacenterId 数据中心
     * @param machineId    机器id
     */
    public GlobalId(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    //产生下一个ID
    public String nextStrId() {
        return String.valueOf(nextId());
    }

    //产生下一个ID
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //if条件里表示当前调用和上一次调用落在了相同毫秒内，只能通过第三部分，序列号自增来判断为唯一，所以+1.
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大，只能等待下一个毫秒
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            //执行到这个分支的前提是currTimestamp > lastTimestamp，说明本次调用跟上次调用对比，已经不再同一个毫秒内了，这个时候序号可以重新回置0了。
            sequence = 0L;
        }

        lastStmp = currStmp;
        //就是用相对毫秒数、机器ID和自增序号拼接
        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT      //数据中心部分
                | machineId << MACHINE_LEFT            //机器标识部分
                | sequence;                            //序列号部分
    }

    /**
     * !#zh
     * 获取下一个毫秒
     *
     * @return
     */
    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        Set<Long> set = new HashSet<>();
        long st = System.currentTimeMillis();
        for (int j = 0; j < 100000; j++) {
            long id = globalId.nextId();
            System.out.println(id);
            set.add(id);
        }
        long ed = System.currentTimeMillis();
        System.out.println(set.size() + "time:" + (ed - st));
    }
}
