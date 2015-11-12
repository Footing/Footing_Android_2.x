package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.realmbean.Message;
import team.far.footing.presenter.MessagePresenter;
import team.far.footing.ui.adaper.MessageAdapter;
import team.far.footing.ui.vu.IMessageVu;
import team.far.footing.ui.widget.DividerItemDecoration;

public class MessageActivity extends BaseActivity implements IMessageVu {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.msg_ry_view)
    RecyclerView msgRyView;
    @Bind(R.id.fab)
    FloatingActionButton fab;


    private MessagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        init();

        presenter = new MessagePresenter(this);
    }

    private void init() {
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        msgRyView.setLayoutManager(new LinearLayoutManager(this));
        msgRyView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));
    }

    @Override
    public void show(MessageAdapter adapter) {
        msgRyView.setAdapter(adapter);
    }

    @Override
    public void addFriendSucess() {
        showdialog("成功添加好友");
    }

    @Override
    public void addFriendFail(int i, String reason) {
        showdialog("添加好友失败"+"\n"+reason);
    }

    @Override
    public void addFriending() {
        showdialog("正在添加好友，请稍等...");
    }
}
