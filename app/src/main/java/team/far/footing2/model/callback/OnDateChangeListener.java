package team.far.footing2.model.callback;

import java.util.List;

import team.far.footing2.model.bean.MessageBean;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public interface OnDateChangeListener {
    void onConnectCompleted();
    void onDataChange(List<MessageBean> list);
    void onError(int i, String s);

}
