package team.far.footing.model.impl;

import com.easemob.chat.EMMessage;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import team.far.footing.app.APP;
import team.far.footing.model.IRealm;
import team.far.footing.model.bean.realmbean.Message;
import team.far.footing.uitl.LogUtils;

/**
 * Created by luoyy on 2015/11/9 0009.
 */
public class RModel implements IRealm {
    private Realm mRealm;
    public final static RModel Instances = new RModel();

    public final static RModel getInstances() {
        return Instances;
    }

    private RModel() {
    }

    @Override
    public void setEMMessage(EMMessage message) {
        mRealm = Realm.getInstance(APP.getContext());

        mRealm.beginTransaction();

        LogUtils.e("在Realm中保存1");
        Message msg = mRealm.createObject(Message.class);
        msg.setContent(message.getBody().toString());
        msg.setDate(message.getMsgTime());
        msg.setGetUser(message.getTo());
        msg.setSendUser(message.getFrom());
        LogUtils.e("在Realm中保存2");

        mRealm.commitTransaction();
    }

    @Override
    public List<Message> getMessMsg() {
        mRealm = Realm.getInstance(APP.getContext());
        RealmResults<Message> result = mRealm.where(Message.class).findAll();
        result.sort("age");

        List<Message> list = result.subList(0, result.size());
        return list;
    }
}
