package cn.edu.shu.scommunity.model;

import java.io.Serializable;

/**
 * Created by admin on 2016/4/29.
 */
public class ActivityDetail implements Serializable {
    private int id;         //活动编号
    private String title;   //活动名称
    private String description;     //简介
    private String type;        //活动类型
    private int limit_num;      //人数限制
    private int actual_num;     //实际人数
    private String start_time;      //活动开始时间
    private String end_time;        //活动结束时间
    private String no_participate_time; //不可报名时间
    private String attach;          //图片地址
    private int createuser;         //创建人id
    private String createuser_name;     //创建人名字
    private String updatetime;      //修改时间

    public ActivityDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(int limit_num) {
        this.limit_num = limit_num;
    }

    public int getActual_num() {
        return actual_num;
    }

    public void setActual_num(int actual_num) {
        this.actual_num = actual_num;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getNo_participate_time() {
        return no_participate_time;
    }

    public void setNo_participate_time(String no_participate_time) {
        this.no_participate_time = no_participate_time;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getCreateuser() {
        return createuser;
    }

    public void setCreateuser(int createuser) {
        this.createuser = createuser;
    }

    public String getCreateuser_name() {
        return createuser_name;
    }

    public void setCreateuser_name(String createuser_name) {
        this.createuser_name = createuser_name;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
