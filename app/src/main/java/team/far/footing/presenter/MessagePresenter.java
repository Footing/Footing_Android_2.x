package team.far.footing.presenter;

import android.app.Activity;
import android.widget.Toast;

import java.util.List;

import team.far.footing.model.IFriendModel;
import team.far.footing.model.IRealm;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.bean.realmbean.Message;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.callback.OnUserListener;
import team.far.footing.model.impl.FriendModel;
import team.far.footing.model.impl.RModel;
import team.far.footing.ui.adaper.MessageAdapter;
import team.far.footing.ui.vu.IMessageVu;
import team.far.footing.uitl.LogUtils;

/**
 * Created by MathiasLuo on 2015/11/11.
 */
public class MessagePresenter {

    private IMessageVu messageVu;
    private IRealm iRealm;
    private IFriendModel friendModel;


    public MessagePresenter(IMessageVu messageVu) {
        friendModel = FriendModel.getInstance();
        this.messageVu = messageVu;
        iRealm = RModel.getInstances();
        showMsg();
    }


    private void showMsg() {
        List<Message> messages = iRealm.getSyStemMessMsg();
        LogUtils.e(messages.get(0).getContent());
        messageVu.show(new MessageAdapter(messages, (Activity) messageVu,this));
    }


    public void addFriend(String id) {
        friendModel.queryUserById(id, new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<UserInfo> object) {
                if (object.size() != 0)
                    friendModel.ensureAddFriend(object.get(0), new OnUserListener() {
                        @Override
                        public void Success() {
                            messageVu.addFriendSucess();
                        }

                        @Override
                        public void Failed(int i, String reason) {
                            messageVu.addFriendFail(i, reason);
                        }

                        @Override
                        public void onProgress(int i, String s) {
                            messageVu.addFriending();
                        }
                    });
                else {
                    messageVu.addFriendFail(0000, "查无此人");
                }

            }

            @Override
            public void onError(int code, String msg) {
                messageVu.addFriendFail(code, msg);
            }
        });


    }

    public void refuseFriend(String id) {
        friendModel.refuseAddFriend();
    }
}
