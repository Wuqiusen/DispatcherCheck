package com.zxw.dispatchercheck.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.zxw.dispatchercheck.R;
import com.zxw.dispatchercheck.adapter.MainAdapter;
import com.zxw.dispatchercheck.presenter.MainPresenter;
import com.zxw.dispatchercheck.presenter.view.MainView;
import com.zxw.dispatchercheck.ui.base.PresenterActivity;
import com.zxw.dispatchercheck.utils.DividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends PresenterActivity<MainPresenter> implements MainView {
    @Bind(R.id.tv_steward_send)
    TextView tvStewardSend;
    @Bind(R.id.rv_plan_list)
    RecyclerView rvPlanList;
    @Bind(R.id.btn_refresh)
    Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        rvPlanList.setLayoutManager(new LinearLayoutManager(this));
        rvPlanList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        presenter.loadSendCarList();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(MainActivity.this, this);
    }

    @Override
    public void loadSendCarList(MainAdapter mainAdapter) {
        rvPlanList.setAdapter(mainAdapter);

    }


    @OnClick(R.id.btn_refresh)
    public void onClick() {
        presenter.loadSendCarList();
    }
}
