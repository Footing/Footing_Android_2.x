package team.far.footing2.model;

import com.easemob.EMEventListener;

import cn.bmob.v3.listener.FindListener;
import team.far.footing2.model.bean.MessageBean;
import team.far.footing2.model.bean.UserInfo;
import team.far.footing2.model.callback.OnDateChangeListener;
import team.far.footing2.model.callback.OnUserListener;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public interface IMessageModel {


    /**
     * 利用广播接受信息
     * <p>
     * 信息监听有两种,界面建议直接监听，不用广播。
     */
    void startBroadcastListener();

    /**
     * @param emEventListener 利用监听器接搜数据
     *                        <p>
     *                        <p>
     *                        建议写的方式：有些常量
     *                        new EMEventListener() {
     * @Override public void onEvent(EMNotifierEvent event) {
     * <p>
     * switch (event.getEvent()) {
     * case EventNewMessage: // 接收新消息
     * {
     * EMMessage message = (EMMessage) event.getData();
     * break;
     * }
     * case EventDeliveryAck:{//接收已发送回执
     * <p>
     * break;
     * }
     * <p>
     * case EventNewCMDMessage:{//接收透传消息
     * <p>
     * break;
     * }
     * <p>
     * case EventReadAck:{//接收已读回执
     * <p>
     * break;
     * }
     * <p>
     * case EventOfflineMessage: {//接收离线消息
     * List<EMMessage> messages = (List<EMMessage>) event.getData();
     * break;
     * }
     * <p>
     * case EventConversationListChanged: {//通知会话列表通知event注册（在某些特殊情况，SDK去删除会话的时候会收到回调监听）
     * <p>
     * break;
     * }
     * <p>
     * default:
     * break;
     * }
     * }
     * }
     * }
     */
    void startEventListener(EMEventListener emEventListener);

    /**
     * 删除msg
     *
     * @param username
     */
    void deleteMsg(String username, final OnUserListener onUserListener);

    /**
     * 给用户发送文字消息
     *
     * @param userInfo
     */
    void sendTxtMssageToUser(UserInfo userInfo, String Content, OnUserListener onUserListener);

    /**
     * @param userInfo
     * @param filePath       ----语音文件的path
     * @param onUserListener
     */

    void sendVoiceMessageToUser(UserInfo userInfo, String filePath, OnUserListener onUserListener);


    void sendImagMessageToUser(UserInfo userInfo, String filePath, OnUserListener onUserListener);

    void sendLocationMessageToUser(UserInfo userInfo, String locationAddress, double latitude, double longitude, OnUserListener onUserListener);

    void sendFileMessageToUser(UserInfo userInfo, String filePath, OnUserListener onUserListener);

    /**
     * 得到当前用户的所有 message
     *
     * @param findListener
     */
    void getAllMessage(FindListener<MessageBean> findListener);


}
