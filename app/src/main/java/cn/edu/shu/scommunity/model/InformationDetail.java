package cn.edu.shu.scommunity.model;

import java.io.Serializable;

/**
 * Created by admin on 2016/4/29.
 */
public class InformationDetail implements Serializable {
    private int id;     //活动编号
    private String title;   //活动名称
    private String content;     //活动内容
    private String type;        //通知类型
    private String attach;      //图片地址
    private int createuser;        //创建人id
    private String createuser_name;     //创建人姓名
    private String updatetime;      //修改时间

    public InformationDetail() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
