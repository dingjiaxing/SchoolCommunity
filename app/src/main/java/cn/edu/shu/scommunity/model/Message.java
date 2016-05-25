package cn.edu.shu.scommunity.model;

import java.io.Serializable;

/**
 * Created by admin on 2016/4/29.
 */
public class Message implements Serializable {
    private int id;             //编号
    private String receive_name;    //收件人姓名
    private String createtime;      //创建时间
    private String activity;    //活动名
    private String content;     //最新的消息内容

    public Message() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceive_name() {
        return receive_name;
    }

    public void setReceive_name(String receive_name) {
        this.receive_name = receive_name;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
