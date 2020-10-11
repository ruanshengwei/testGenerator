package com.github.ruanshengwei.model;

import java.math.BigDecimal;

public class Person {

    private String name;

    private Integer old;

    private int oldInt;

    private Long aLong;

    private long aLong2;

    private Double aDouble;

    private double aDouble2;

    private Account account;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getOld() {
        return old;
    }

    public void setOld(Integer old) {
        this.old = old;
    }

    public int getOldInt() {
        return oldInt;
    }

    public void setOldInt(int oldInt) {
        this.oldInt = oldInt;
    }

    public Long getaLong() {
        return aLong;
    }

    public void setaLong(Long aLong) {
        this.aLong = aLong;
    }

    public long getaLong2() {
        return aLong2;
    }

    public void setaLong2(long aLong2) {
        this.aLong2 = aLong2;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    public double getaDouble2() {
        return aDouble2;
    }

    public void setaDouble2(double aDouble2) {
        this.aDouble2 = aDouble2;
    }
}
