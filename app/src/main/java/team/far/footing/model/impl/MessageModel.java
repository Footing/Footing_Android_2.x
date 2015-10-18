package team.far.footing.model.impl;

import android.content.IntentFilter;

import com.easemob.EMCallBack;
import com.easemob.EMEventListener;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.LocationMessageBody;
import com.easemob.chat.NormalFileMessageBody;
import com.easemob.chat.TextMessageBody;
import com.easemob.chat.VoiceMessageBody;

import java.io.File;

import cn.bmob.v3.listener.FindListener;
import team.far.footing.app.APP;
import team.far.footing.model.IMessageModel;
import team.far.footing.model.bean.MessageBean;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.callback.OnUserListener;
import team.far.footing.receiver.NewMessageBroadcastReceiver;
import team.far.footing.uitl.BmobUtils;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public class MessageModel implements IMessageModel {
    public static final MessageModel instance = new MessageModel();

    private EMConversation conversation = null;

    private EMConversation getConversation() {
        if (conversation == null) {
            conversation = EMChatManager.getInstance().getConversation(BmobUtils.getCurrentUser().getUsername());

        }
        return conversation;
    }

    private MessageModel() {

    }

    public static MessageModel getInstance() {
        return instance;
    }


    @Override
    public void startBroadcastListener() {
        //只有注册了广播才能接收到新消息，目前离线消息，在线消息都是走接收消息的广播（离线消息目前无法监听，在登录以后，接收消息广播会执行一次拿到所有的离线消息）
        NewMessageBroadcastReceiver msgReceiver = new NewMessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
        intentFilter.setPriority(3);
        APP.getContext().registerReceiver(msgReceiver, intentFilter);
    }

    @Override
    public void startEventListener(EMEventListener emEventListener) {
        EMChatManager.getInstance().registerEventListener(emEventListener);
    }

    @Override
    public void deleteMsg(String username, final OnUserListener onUserListener) {
        //删除和某个user的整个的聊天记录(包括本地)
        EMChatManager.getInstance().deleteConversation(username);
    }


    @Override
    public void sendTxtMssageToUser(final UserInfo userInfo, final String content, final OnUserListener onUserListener) {
        //获取到与聊天人的会话对象。参数username为聊天人的userid或者groupid，后文中的username皆是如此
        EMConversation conversation = EMChatManager.getInstance().getConversation(BmobUtils.getCurrentUser().getUsername());
        //创建一条文本消息
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
        //如果是群聊，设置chattype,默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        //设置消息body
        TextMessageBody txtBody = new TextMessageBody(content);
        message.addBody(txtBody);
        //设置接收人
        message.setReceipt(userInfo.getUsername());
        //把消息加入到此会话对象中
        conversation.addMessage(message);
        //发送消息
        sendMessage(message, onUserListener);

    }

    @Override
    public void sendVoiceMessageToUser(UserInfo userInfo, String filePath, final OnUserListener onUserListener) {
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.VOICE);
        //如果是群聊，设置chattype,默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        VoiceMessageBody body = new VoiceMessageBody(new File(filePath), 1024);
        message.addBody(body);
        message.setReceipt(userInfo.getUsername());
        getConversation().addMessage(message);
        sendMessage(message, onUserListener);
    }

    @Override
    public void sendImagMessageToUser(UserInfo userInfo, String filePath, OnUserListener onUserListener) {
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.IMAGE);
        //如果是群聊，设置chattype,默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);

        ImageMessageBody body = new ImageMessageBody(new File(filePath));
        // 默认超过100k的图片会压缩后发给对方，可以设置成发送原图
        // body.setSendOriginalImage(true);
        message.addBody(body);
        message.setReceipt(userInfo.getUsername());
        getConversation().addMessage(message);
        sendMessage(message, onUserListener);
    }

    @Override
    public void sendLocationMessageToUser(UserInfo userInfo, String locationAddress, double latitude, double longitude, OnUserListener onUserListener) {
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.LOCATION);
        //如果是群聊，设置chattype,默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        LocationMessageBody locBody = new LocationMessageBody(locationAddress, latitude, longitude);
        message.addBody(locBody);
        message.setReceipt(userInfo.getUsername());
        getConversation().addMessage(message);
        sendMessage(message, onUserListener);
    }

    @Override
    public void sendFileMessageToUser(UserInfo userInfo, String filePath, OnUserListener onUserListener) {
        // 创建一个文件消息
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.FILE);
        // 如果是群聊，设置chattype,默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        //设置接收人的username
        message.setReceipt(userInfo.getUsername());
        // add message body
        NormalFileMessageBody body = new NormalFileMessageBody(new File(filePath));
        message.addBody(body);
        conversation.addMessage(message);
        sendMessage(message, onUserListener);
    }

    @Override
    public void getAllMessage(final FindListener<MessageBean> findListener) {


    }


    private void sendMessage(EMMessage message, final OnUserListener onUserListener) {
        //发送消息
        EMChatManager.getInstance().sendMessage(message, new EMCallBack() {
            @Override
            public void onSuccess() {
                if (onUserListener != null)
                    onUserListener.Success();
            }

            @Override
            public void onError(int i, String s) {
                if (onUserListener != null) onUserListener.Failed(i, s);
            }

            @Override
            public void onProgress(int i, String s) {
                if (onUserListener != null) onUserListener.onProgress(i, s);
            }
        });

    }


}
