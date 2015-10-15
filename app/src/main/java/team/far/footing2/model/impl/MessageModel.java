package team.far.footing2.model.impl;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;

import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;
import team.far.footing2.app.APP;
import team.far.footing2.model.IMessageModel;
import team.far.footing2.model.bean.MessageBean;
import team.far.footing2.model.bean.MessageCenter;
import team.far.footing2.model.bean.UserInfo;
import team.far.footing2.model.callback.OnDateChangeListener;
import team.far.footing2.model.callback.OnUserInfoListener;
import team.far.footing2.model.callback.OnUserListener;
import team.far.footing2.uitl.BmobUtils;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public class MessageModel implements IMessageModel {
    public static final MessageModel instance = new MessageModel();

    private MessageModel() {

    }

    public static MessageModel getInstance() {
        return instance;
    }


    @Override
    public void startListener(final OnDateChangeListener onDateChangeListener) {


    }

    @Override
    public void deleteMsg(String username, final OnUserListener onUserListener) {
        //删除和某个user的整个的聊天记录(包括本地)
        EMChatManager.getInstance().deleteConversation(username);

    }

    @Override
    public void makeMsgReaded(MessageBean messageBean) {

    }

    @Override
    public void sendMssageToUser(final UserInfo userInfo, final String msg, final String content, final OnUserListener onUserListener) {
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
        EMChatManager.getInstance().sendMessage(message, new EMCallBack() {
            @Override
            public void onSuccess() {
                onUserListener.Success();
            }

            @Override
            public void onError(int i, String s) {
                onUserListener.Failed(i, s);
            }

            @Override
            public void onProgress(int i, String s) {
                onUserListener.onProgress(i, s);
            }
        });

    }

    @Override
    public void getAllMessage(final FindListener<MessageBean> findListener) {


    }


}
