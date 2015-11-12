package team.far.footing.ui.vu;


import team.far.footing.ui.adaper.MessageAdapter;

/**
 * Created by MathiasLuo on 2015/11/11.
 */
public interface IMessageVu {

    void show(MessageAdapter adapter);

    void addFriendSucess();

    void addFriendFail(int i, String reason);

    void addFriending();

}
