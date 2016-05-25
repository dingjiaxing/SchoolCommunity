package cn.edu.shu.scommunity.utils.http;

/**
 * Created by admin on 2016/5/14.
 */
public interface IServer {
    /**
     * 获取消息详情
     * @param token
     * @param message_id
     * @return
     */
    public String GetMessageDetail(String token, int message_id);

    /**
     * 登录
     * @param id
     * @param pwd
     * @param token
     * @return
     */
    public String Login(String id, String pwd, String token);

    /**
     * 获取活动列表
     * @param token
     * @param activity_id
     * @param type1
     * @param type2
     * @param type
     * @return
     */
    String GetActivityList(String token, String activity_id, String type1, String type2, String type);

    /**
     * 获取通知列表
     * @param token
     * @param activity_id
     * @param type1
     * @param type2
     * @param type
     * @return
     */
    public String GetInformationList(String token, String activity_id, String type1, String type2, String type);

    /**
     * 获取消息列表
     * @param token
     * @param message_id
     * @param type1
     * @return
     */
    public String GetMessageList(String token, String message_id, String type1);

    /**
     * 发送消息
     * @param token
     * @param receive_id
     * @param content
     * @param activity_id
     * @return
     */
    public  String SendMessage(String token, String receive_id, String content, String activity_id);

    /**
     * 参加活动
     * @param token
     * @param activity_id
     * @return
     */
    public String ParticipateActivity(String token, String activity_id);

    /**
     * 收藏信息
     * @param token
     * @param information_id
     * @param type
     * @return
     */
    public String CollecteInformation(String token, String information_id, String type);

    /**
     * 更新用户信息
     * @param token
     * @param email
     * @param phone
     * @param address
     * @return
     */
    public String UpdateUserDetail(String token, String email, String phone, String address);

    /**
     * 获取用户信息详情
     * @param token
     * @return
     */
    public String GetUserDetail(String token);

    /**
     * 获取活动详情
     * @param token
     * @param activity_id
     * @return
     */
    public String GetActivityDetail(String token, String activity_id);

    /**
     * 获取通知详情
     * @param token
     * @param information_id
     * @return
     */
    public String GetInformationDetail(String token, String information_id);

    /**
     * 获取消息详情
     * @param token
     * @param message_id
     * @return
     */
    public String GetMessageDetail(String token, String message_id);
}
