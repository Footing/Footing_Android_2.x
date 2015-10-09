package team.far.footing.model.impl;

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
import team.far.footing.app.APP;
import team.far.footing.model.IMessageModel;
import team.far.footing.model.bean.MessageBean;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.callback.OnDateChangeListener;
import team.far.footing.model.callback.OnUserInfoListener;
import team.far.footing.model.callback.OnUserListener;
import team.far.footing.uitl.BmobUtils;

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
        final BmobRealTimeData rtd = new BmobRealTimeData();
        rtd.start(APP.getContext(), new ValueEventListener() {
            @Override
            public void onConnectCompleted() {
                //对当前用户对应的好友表中的自己那行进行监听
                rtd.subRowUpdate("_User", BmobUtils.getCurrentUser().getObjectId());
                onDateChangeListener.onConnectCompleted();
            }

            @Override
            public void onDataChange(final JSONObject jsonObject) {

                getAllMessage(new FindListener<MessageBean>() {
                    @Override
                    public void onSuccess(List<MessageBean> list) {
                        onDateChangeListener.onDataChange(list);
                    }

                    @Override
                    public void onError(int i, String s) {
                        onDateChangeListener.onError(i, s);
                    }
                });


            }
        });
    }

    @Override
    public void deleteMsg(MessageBean messageBean, final OnUserListener onUserListener) {
        messageBean.delete(APP.getContext(), new DeleteListener() {
            @Override
            public void onSuccess() {
                onUserListener.Success();
            }

            @Override
            public void onFailure(int i, String s) {
                onUserListener.Failed(i, s);
            }
        });
    }

    @Override
    public void makeMsgReaded(MessageBean messageBean) {
        messageBean.setIsread(1);
        messageBean.update(APP.getContext());
    }

    @Override
    public void sendMssageToUser(final UserInfo userInfo, final String message, final String content, final OnUserListener onUserListener) {
        final MessageBean messageBean = new MessageBean();
        messageBean.setGetuser(userInfo);

        BmobUtils.getCurrentUserInfo(new OnUserInfoListener() {
            @Override
            public void Success(final UserInfo userInfo) {
                messageBean.setSenduser(userInfo);
                messageBean.setIsread(0);
                messageBean.setMessage(message);
                messageBean.setContent(content);
                messageBean.save(APP.getContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        AddMessage(userInfo, messageBean, onUserListener);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        onUserListener.Failed(i, s);
                    }
                });
            }

            @Override
            public void Failed(int i, String reason) {

            }
        });


    }

    @Override
    public void AddMessage(UserInfo userInfo, MessageBean messageBean, final OnUserListener onUserListener) {
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(messageBean);
        userInfo.setMessages(bmobRelation);
        userInfo.update(APP.getContext(), new UpdateListener() {
            @Override
            public void onSuccess() {
                if (onUserListener != null) onUserListener.Success();
            }

            @Override
            public void onFailure(int i, String s) {
                if (onUserListener != null) onUserListener.Failed(i, s);
            }
        });
    }

    @Override
    public void getAllMessage(FindListener<MessageBean> findListener) {
        BmobQuery<MessageBean> query = new BmobQuery<MessageBean>();
        query.addWhereRelatedTo("messages", new BmobPointer(BmobUtils.getCurrentUser()));
        query.findObjects(APP.getContext(), findListener);
    }


}
