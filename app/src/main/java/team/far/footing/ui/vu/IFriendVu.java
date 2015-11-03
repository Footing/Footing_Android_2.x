package team.far.footing.ui.vu;

import android.support.v7.widget.RecyclerView;

/**
 * Created by luoyy on 2015/11/3 0003.
 */
public interface IFriendVu {

    void showfriends(RecyclerView.Adapter adapter);

    void stopRefresh();

    void showEmpty();


}
