package com.admin.utils.checkchar;

import com.admin.utils.exception.BaseException;
import com.admin.utils.http.HttpStatus;
import com.admin.utils.uuid.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串比较工具类
 */
public class CompareCharUtils {

    private static Logger logger = LoggerFactory.getLogger(CompareCharUtils.class);

    /**
     * 两个对象比较
     * @param listStr1 对象1的属性值转化为list
     * @param listStr2 对象2的属性值转化为list
     * @return List<CompareModel>
     */
    private List<CompareModel> compares(List<String> listStr1, List<String> listStr2) {
        // 长度判断
        if (listStr1.size() != listStr2.size()) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "两个需比较的数据集长度不一致");
        }
        logger.info("对象属性值比较开始...");
        long startTime = System.currentTimeMillis();
        List<CompareModel> list = new ArrayList<>();
        int threadCount;
        if (listStr1.size() > 5) {
            threadCount = 5;
        } else {
            threadCount = listStr1.size();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch countDownLatch = new CountDownLatch(listStr1.size());
        for (int i=0; i<listStr1.size(); i++) {
            String char1= listStr1.get(i);
            String char2= listStr2.get(i);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        list.add(compareChar(char1, char2));
                        countDownLatch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(runnable);
        }
        try {
            countDownLatch.await();//阻塞当前线程，直到计数器的值为0
            long endTime = System.currentTimeMillis();
            logger.info("对象属性值比较结束...");
            // 主线程开始执行
            logger.info("对象属性值比较主线程开始执行...");
            String times = staTime(startTime, endTime);
            logger.info("对象属性值比较线程总耗时：{}", times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 相同字符校验
     * @param char1
     * @param char2
     * @return
     */
    private CompareModel compareChar(String char1, String char2) {
        logger.info("字符串比较开始...");
        Map<String, Object> map = new HashMap<>();
        int commonCharCount = 0;
        List<String> commonCharList = new ArrayList<>();
        char[] arr1 = char1.toCharArray();
        for (int i=0; i<arr1.length; i++) {
            if (char2.contains(String.valueOf(arr1[i])) && !speChar(String.valueOf(arr1[i]))) { // 不包含特殊字符
                logger.info("char1与char2相同的字符为：{}", arr1[i]);
                commonCharCount++;
                commonCharList.add(String.valueOf(arr1[i]));
            }
        }
        map.put("commonCharCount", commonCharCount);
        map.put("commonCharList", commonCharList);
        CompareModel compareModel = new CompareModel();
        compareModel.setCompareId(UUIDUtils.getUUID());
        compareModel.setChar1(char1);
        compareModel.setChar2(char2);
        compareModel.setCompareMap(map);
        logger.info("字符串比较结束...");
        return compareModel;
    }

    /**
     * 特殊字符校验
     * @param str
     * @return
     */
    private boolean speChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 线程运行时间统计
     * @param startTime 开始时间（毫秒）
     * @param endTime 结束时间（毫秒）
     * @return 年月日时分秒
     */
    private String staTime(long startTime, long endTime) {
        logger.info("计算对象属性值比较线程耗时开始...");
        String times = ""; //总时长
        String days = ""; //天
        String hours = ""; //小时
        String seconds = ""; //分钟
        String millis = ""; // 秒
        String timeMillis = ""; //毫秒
        int longTime = (int) (endTime - startTime);
        if (longTime < 1000) {
            times = longTime + "毫秒";
            logger.info("计算对象属性值比较线程耗时结束...");
        } else {
            int timeMilli = longTime%1000; //毫秒
            if (timeMilli != 0) {
                timeMillis = timeMilli + "毫秒";
            }
            int milli = longTime/1000;
            if (milli < 60) {
                millis = milli + "秒";
                times = millis + timeMillis;
                logger.info("计算对象属性值比较线程耗时结束...");
            } else {
                millis = milli%60 + "秒";
                int second = milli/60;
                if (second < 60) {
                    seconds = second + "分";
                    times = seconds + millis + timeMillis;
                    logger.info("计算对象属性值比较线程耗时结束...");
                } else {
                    seconds = second%60 + "分钟";
                    int hour = second/60;
                    if (hour < 24) {
                        hours = hour + "小时";
                        times = hours + seconds + millis + timeMillis;
                        logger.info("计算对象属性值比较线程耗时结束...");
                    } else {
                        hours = hour%24 + "小时";
                        days = hour/24 + "天";
                        times = days + hours + seconds + millis + timeMillis;
                        logger.info("计算对象属性值比较线程耗时结束...");
                    }
                }
            }
        }
        return times;
    }

    public static void main(String[] args) {}
}
