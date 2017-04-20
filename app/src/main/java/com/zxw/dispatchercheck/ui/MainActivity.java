package com.zxw.dispatchercheck.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.zxw.dispatchercheck.R;
import com.zxw.dispatchercheck.adapter.MainAdapter;
import com.zxw.dispatchercheck.presenter.MainPresenter;
import com.zxw.dispatchercheck.presenter.view.MainView;
import com.zxw.dispatchercheck.ui.base.PresenterActivity;
import com.zxw.dispatchercheck.utils.DividerItemDecoration;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends PresenterActivity<MainPresenter> implements MainView {
    @Bind(R.id.tv_steward_send)
    TextView tvStewardSend;
    @Bind(R.id.rv_plan_list)
    RecyclerView rvPlanList;
//    @Bind(R.id.btn_refresh)
//    Button btnRefresh;
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private final int LOAD_DATA = 1;
    private Handler handler =  new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                switch (msg.what) {
                    case LOAD_DATA:

                        presenter.loadSendCarList();
                        break;
                }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        rvPlanList.setLayoutManager(new LinearLayoutManager(this));
        rvPlanList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        showLoading();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message obtain = Message.obtain();
                obtain.what = LOAD_DATA;
                handler.sendMessage(obtain);
            }
        };
        timer.schedule(timerTask,0, 1000 * 10);
        hideLoading();

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(MainActivity.this, this);
    }

    @Override
    public void loadSendCarList(MainAdapter mainAdapter) {
        rvPlanList.setAdapter(mainAdapter);

    }


//    @OnClick(R.id.btn_refresh)
//    public void onClick() {
//        presenter.loadSendCarList();
//    }

    @Override
    protected void onDestroy() {
        if (timer != null){
            timer.cancel();
            timer = null;
        }
        if (timerTask != null){
            timerTask.cancel();
            timerTask = null;
        }
        super.onDestroy();
    }
}
