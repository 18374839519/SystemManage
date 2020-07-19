package com.admin.utils.checkchar;

import java.util.Map;

public class CompareModel {

    private String compareId; // 结果集id

    private String char1; // 第一个比较字段

    private String char2; // 第二个比较字段

    private Map<String, Object> compareMap; // 比对结果

    public String getCompareId() {
        return compareId;
    }

    public void setCompareId(String compareId) {
        this.compareId = compareId;
    }

    public String getChar1() {
        return char1;
    }

    public void setChar1(String char1) {
        this.char1 = char1;
    }

    public String getChar2() {
        return char2;
    }

    public void setChar2(String char2) {
        this.char2 = char2;
    }

    public Map<String, Object> getCompareMap() {
        return compareMap;
    }

    public void setCompareMap(Map<String, Object> compareMap) {
        this.compareMap = compareMap;
    }
}
