package cn.edu.shu.scommunity.model;

import java.io.Serializable;

/**
 * Created by admin on 2016/4/29.
 */
public class Information implements Serializable {
    private String id;         //编号
    private String title;   //标题
    private String url;  //图片地址
    private String type;    //类型名
    private String updatetime;  //修改时间

    public Information() {
    }

    public Information(String id, String title, String url, String type, String updatetime) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.type = type;
        this.updatetime = updatetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
