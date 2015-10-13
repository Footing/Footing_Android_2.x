package team.far.footing2.model.callback;

import team.far.footing2.model.bean.MapBean;

/**
 * Created by luoyy on 2015/8/14 0014.
 */
public interface OnUpdateMapListener {
    void onSuccess(MapBean mapBean);

    void onFailure(int i, String s);
}
