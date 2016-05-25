package cn.edu.shu.scommunity.model;

import java.io.Serializable;

/**
 * Created by admin on 2016/4/29.
 */
public class MessageDetail implements Serializable {
    private int id; //消息编号
    private int activity_id;    //活动id
    private int send_id;    //发送方用户id
    private String send_name;   //发送名字
    private int receive_id;     //接收方id
    private String receive_name;        //接收方名字
    private String content;         //内容
    private String createtime;      //创建时间
    private int type;               //  0  为  接受的消息  1  为我发的消息

    public MessageDetail() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getSend_id() {
        return send_id;
    }

    public void setSend_id(int send_id) {
        this.send_id = send_id;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public int getReceive_id() {
        return receive_id;
    }

    public void setReceive_id(int receive_id) {
        this.receive_id = receive_id;
    }

    public String getReceive_name() {
        return receive_name;
    }

    public void setReceive_name(String receive_name) {
        this.receive_name = receive_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
