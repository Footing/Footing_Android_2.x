package team.far.footing.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMMessage;

import java.lang.ref.WeakReference;
import java.util.List;

import team.far.footing.model.IMessageModel;
import team.far.footing.model.IRealm;
import team.far.footing.model.impl.MessageModel;
import team.far.footing.model.impl.RModel;
import team.far.footing.ui.activity.HomeActivity;
import team.far.footing.ui.vu.IHomeVu;
import team.far.footing.uitl.LogUtils;

/**
 * Created by luoyy on 2015/10/25 0025.
 */
public class HomePresenter {

    private IHomeVu mHomeVu;
    private IMessageModel messageModel;
    private Handler handler;
    private IRealm mIRealm;

    //新来的单个消息
    private EMMessage mNewMsg;


    public HomePresenter(IHomeVu mHomeVu, HomeActivity activity) {
        this.mHomeVu = mHomeVu;
        messageModel = MessageModel.getInstance();
        mIRealm = RModel.getInstances();
        startListenMsg();
        handler = new MyHandler(activity);
    }


    private void startListenMsg() {
        messageModel.startEventListener(new EMEventListener() {
                                            @Override
                                            public void onEvent(EMNotifierEvent emNotifierEvent) {
                                                switch (emNotifierEvent.getEvent()) {
                                                    case EventNewMessage: // 接收新消息
                                                        mNewMsg = (EMMessage) emNotifierEvent.getData();
                                                        LogUtils.e("新消息来了:"+mNewMsg.getBody().toString());
                                                        //把消息保存到realm

                                                        mIRealm.setAddFriendMessage(mNewMsg);
                                                        LogUtils.e("新消息保存了就不做了吗？");

                                                        Message message = handler.obtainMessage();
                                                        message.what = 0x0001;
                                                        handler.sendMessage(message);
                                                        break;

                                                    case EventDeliveryAck:
                                                        //接收已发送回执
                                                        break;

                                                    case EventNewCMDMessage:
                                                        //接收透传消息
                                                        break;

                                                    case EventReadAck:
                                                        //接收已读回执
                                                        break;

                                                    case EventOfflineMessage:
                                                        //接收离线消息
                                                        List<EMMessage> messages = (List<EMMessage>) emNotifierEvent.getData();
                                                        break;

                                                    case EventConversationListChanged:
                                                        //通知会话列表通知event注册（在某些特殊情况，SDK去删除会话的时候会收到回调监听）
                                                        break;

                                                    default:
                                                        break;
                                                }
                                            }
                                        }

        );

    }

    class MyHandler extends Handler {

        private WeakReference<Activity> activity;

        public MyHandler(Activity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x0001:
                    //新消息来了
                    mHomeVu.getNewMsg(mNewMsg);
                    break;
            }

        }
    }

}
