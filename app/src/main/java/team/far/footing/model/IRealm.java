package team.far.footing.model;

import com.easemob.chat.EMMessage;

import java.util.List;

import team.far.footing.model.bean.realmbean.Message;

/**
 * Created by luoyy on 2015/11/9 0009.
 */
public interface IRealm {
    void setEMMessage(EMMessage message);

    List<Message> getMessMsg();

}
