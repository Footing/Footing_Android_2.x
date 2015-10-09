package team.far.footing.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public class MessageBean extends BmobObject {

    private UserInfo getuser;
    private UserInfo senduser;
    private String message;
    private String content;
    //  未读 0  已读 1
    private Integer isread;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserInfo getGetuser() {
        return getuser;
    }

    public void setGetuser(UserInfo getuser) {
        this.getuser = getuser;
    }

    public UserInfo getSenduser() {
        return senduser;
    }

    public void setSenduser(UserInfo senduser) {
        this.senduser = senduser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }
}
