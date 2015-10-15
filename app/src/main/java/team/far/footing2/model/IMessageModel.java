package team.far.footing2.model;

import cn.bmob.v3.listener.FindListener;
import team.far.footing2.model.bean.MessageBean;
import team.far.footing2.model.bean.UserInfo;
import team.far.footing2.model.callback.OnDateChangeListener;
import team.far.footing2.model.callback.OnUserListener;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public interface IMessageModel {
    void startListener(OnDateChangeListener onDateChangeListener);

    /**
     * 删除msg
     *
     * @param username
     */
    void deleteMsg(String username, final OnUserListener onUserListener);

    /**
     * 把msg标记为 已读
     */
    void makeMsgReaded(MessageBean messageBean);
    /**
     * 给用户发送消息
     *
     * @param userInfo
     * @param messager
     */
    void sendMssageToUser(UserInfo userInfo, String messager, String Content, OnUserListener onUserListener);

    /**
     * 得到当前用户的所有 message
     *
     * @param findListener
     */
    void getAllMessage(FindListener<MessageBean> findListener);


}
