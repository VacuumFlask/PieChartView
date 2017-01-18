package cn.vacuumflask.piechartview;

/**
 * Created by Administrator on 2017/1/17 0017.
 * 数据类
 */
public class DataBean {
    private int data;//数据
    private String description;//描述

    public DataBean() {
    }

    public DataBean(int data, String description) {
        this.data = data;
        this.description = description;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
