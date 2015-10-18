package team.far.footing.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import team.far.footing.R;
import team.far.footing.ui.adaper.HomeAdapter;
import team.far.footing.uitl.DensityUtils;

/**
 * 主界面的内容区域
 *
 * @author stormouble
 */
public class HomeFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    @Bind(R.id.home_rfab_layout)
    RapidFloatingActionLayout mRfabLayout;
    @Bind(R.id.home_rfab)
    RapidFloatingActionButton mRfabButton;
    @Bind(R.id.home_recycler_view)
    RecyclerView mRecyclerView;

    private RapidFloatingActionHelper mRfabHelper;

    private HomeAdapter mHomeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRfab();
        setupRecyclerView();
    }

    private void setupRfab() {
        RapidFloatingActionContentLabelList rfabContent = new RapidFloatingActionContentLabelList(getActivity());
        rfabContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel(getResources().getString(R.string.home_fab_search))
                .setResId(R.mipmap.ic_rfab_search)
                .setIconNormalColor(getResources().getColor(R.color.home_fab_item_search))
                .setIconPressedColor(getResources().getColor(R.color.home_fab_item_dark_search))
                .setLabelColor(getResources().getColor(R.color.secondary_text))
                .setWrapper(3));
        items.add(new RFACLabelItem<Integer>()
                .setLabel(getResources().getString(R.string.home_fab_draw))
                .setResId(R.mipmap.ic_rfab_draw)
                .setIconNormalColor(getResources().getColor(R.color.home_fab_item_draw))
                .setIconPressedColor(getResources().getColor(R.color.home_fab_item_dark_draw))
                .setLabelColor(getResources().getColor(R.color.secondary_text))
                .setWrapper(3));
        items.add(new RFACLabelItem<Integer>()
                .setLabel(getResources().getString(R.string.home_fab_walk))
                .setResId(R.mipmap.ic_rfab_walk)
                .setIconNormalColor(getResources().getColor(R.color.home_fab_item_walk))
                .setIconPressedColor(getResources().getColor(R.color.home_fab_item_dark_walk))
                .setLabelColor(getResources().getColor(R.color.secondary_text))
                .setWrapper(3));
        rfabContent
                .setItems(items)
                .setIconShadowRadius(DensityUtils.dp2px(getActivity(), 4))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(DensityUtils.dp2px(getActivity(), 2));

        mRfabHelper = new RapidFloatingActionHelper(
                getActivity(),
                mRfabLayout,
                mRfabButton,
                rfabContent
        ).build();
    }

    private void onRfabClicked(int i) {

    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        mHomeAdapter = new HomeAdapter(getContext());
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRFACItemLabelClick(int i, RFACLabelItem rfacLabelItem) {
        onRfabClicked(i);
        mRfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int i, RFACLabelItem rfacLabelItem) {
        onRfabClicked(i);
        mRfabHelper.toggleContent();
    }
}
