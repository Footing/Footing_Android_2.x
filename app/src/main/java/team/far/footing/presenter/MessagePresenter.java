package team.far.footing.presenter;

import android.app.Activity;

import java.util.List;

import team.far.footing.model.IRealm;
import team.far.footing.model.bean.realmbean.Message;
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

    public MessagePresenter(IMessageVu messageVu) {
        this.messageVu = messageVu;
        iRealm = RModel.getInstances();
        showMsg();
    }


    private void showMsg() {
        List<Message> messages = iRealm.getMessMsg();
        LogUtils.e(messages.get(0).getContent());
        messageVu.show(new MessageAdapter(messages, (Activity) messageVu));
    }

}
