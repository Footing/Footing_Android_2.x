package team.far.footing.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by luoyy on 2015/10/10 0010.
 */
public class MessageCenter extends BmobObject {

    private UserInfo userInfo;
    private MessageBean messageBean;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public MessageBean getMessageBean() {
        return messageBean;
    }

    public void setMessageBean(MessageBean messageBean) {
        this.messageBean = messageBean;
    }
}
