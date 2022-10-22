package com.springboot_redis_example.util;

import java.util.*;

public class bitmap {
    int arrnum = 800;
    int len_int = 32;
    int mmax = 9999;
    int mmin = 1000;
    int n = mmax - mmin + 1;
    public static void main(String args[]) {
        new bitmap().findduplicate();
        new bitmap().finddup_jdk();
    }
    public void finddup_jdk() {
        System.out.println("*******调用jdk中的库方法--开始********");
        BitSet  bitarray = new BitSet(n);
        int[] array = getarray(arrnum);
        for (int i = 0; i < arrnum; i++) {
            bitarray.set(array[i] - mmin);
        }
        int count = 0;
        for (int j = 0; j < bitarray.length(); j++) {
            if (bitarray.get(j)) {
                System.out.print(j + mmin + " ");
                count++;
            }
        }
        System.out.println();
        System.out.println("排序后的数组大小为：" + count );
        System.out.println("*******调用jdk中的库方法--结束********");
    }
    public void findduplicate() {
        int[] array = getarray(arrnum);
        int[] bitarray = setbit(array);
        printbitarray(bitarray);
    }
    public void printbitarray(int[] bitarray) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (getbit(bitarray, i) != 0) {
                count++;
                System.out.print(i + mmin + "\t");
            }
        }
        System.out.println();
        System.out.println("去重排序后的数组大小为：" + count);
    }
    public int getbit(int[] bitarray, int k) {// 1右移 k % 32位 与上 数组下标为 k/32 位置的值
        return bitarray[k / len_int] & (1 << (k % len_int));
    }
    public int[] setbit(int[] array) {// 首先取得数组位置下标 i/32, 然后 或上
        // 在该位置int类型数值的bit位：i % 32
        int m = array.length;
        int bit_arr_len = n / len_int + 1;
        int[] bitarray = new int[bit_arr_len];
        for (int i = 0; i < m; i++) {
            int num = array[i] - mmin;
            bitarray[num / len_int] |= (1 << (num % len_int));
        }
        return bitarray;
    }
    public int[] getarray(int arrnum) {

        int array1[] = { 1000, 1002, 1032, 1033, 6543, 9999, 1033, 1000 };
        int array[] = new int[arrnum];
        System.out.println("数组大小：" + arrnum);
        Random r = new Random();
        for (int i = 0; i < arrnum; i++) {
            array[i] = r.nextInt(n) + mmin;
        }
        System.out.println(Arrays.toString(array));
        return array;
    }
}
