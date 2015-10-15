package team.far.footing2.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by luoyy on 2015/10/7 0007.
 */
public class UserInfo extends BmobObject {


    //用于保存 一个用户对应一个Friends中的objectId
    private String FriendId;
    //昵称
    private String NickName;
    //签名
    private String Signature;
    //头像url
    private String HeadPortraitFilePath;
    //点赞数
    private Integer PraiseCount;
    //头像fileame
    private String HeadPortraitFileName;
    //角色等级
    private Integer level;
    //今日路程
    private Integer today_distance;
    //总的路程
    private Integer all_distance;
    //今日任务完成没  0表示未完成  1表示完成。
    private Integer is_finish_today;
    //  0表示不是第三方登陆  1 为第三方登陆
    private Integer isAuth;
    //  表示今日
    private String today_date;
    //用户名
    private String username;
    private Userbean userbean;
    //消息
    private BmobRelation messages;
    //消息中心 id
    private String MessageCenterId;

    public String getMessageCenterId() {
        return MessageCenterId;
    }

    public void setMessageCenterId(String messageCenterId) {
        MessageCenterId = messageCenterId;
    }

    public BmobRelation getMessages() {
        return messages;
    }

    public void setMessages(BmobRelation messages) {
        this.messages = messages;
    }


    public String getFriendId() {
        return FriendId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFriendId(String friendId) {
        FriendId = friendId;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getHeadPortraitFilePath() {
        return HeadPortraitFilePath;
    }

    public void setHeadPortraitFilePath(String headPortraitFilePath) {
        HeadPortraitFilePath = headPortraitFilePath;
    }

    public Integer getPraiseCount() {
        return PraiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        PraiseCount = praiseCount;
    }

    public String getHeadPortraitFileName() {
        return HeadPortraitFileName;
    }

    public void setHeadPortraitFileName(String headPortraitFileName) {
        HeadPortraitFileName = headPortraitFileName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getToday_distance() {
        return today_distance;
    }

    public void setToday_distance(Integer today_distance) {
        this.today_distance = today_distance;
    }

    public Integer getAll_distance() {
        return all_distance;
    }

    public void setAll_distance(Integer all_distance) {
        this.all_distance = all_distance;
    }

    public Integer getIs_finish_today() {
        return is_finish_today;
    }

    public void setIs_finish_today(Integer is_finish_today) {
        this.is_finish_today = is_finish_today;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public String getToday_date() {
        return today_date;
    }

    public void setToday_date(String today_date) {
        this.today_date = today_date;
    }


    public Userbean getUserbean() {
        return userbean;
    }

    public void setUserbean(Userbean userbean) {
        this.userbean = userbean;
    }
}
