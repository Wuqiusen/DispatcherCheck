package com.zxw.dispatchercheck.presenter;

import android.content.Context;

import com.zxw.data.http.HttpMethods;
import com.zxw.data.http.bean.DepartCar;
import com.zxw.data.http.bean.Line;
import com.zxw.data.http.bean.LineParams;
import com.zxw.dispatchercheck.adapter.MainAdapter;
import com.zxw.dispatchercheck.presenter.view.MainView;

import java.util.List;

import rx.Subscriber;

/**
 * author：CangJie on 2016/9/20 17:26
 * email：cangjie2016@gmail.com
 */
public class MainPresenter extends BasePresenter<MainView> {
    private Context mContext;
    public final static int TYPE_SALE_AUTO = 1, TYPE_SALE_MANUAL = 2;
    private int lineId = 3;
    private String lineName;
    private LineParams mLineParams;
    private Line mCurrentLine;
    private List<Line> mLineBeen;
    private MainAdapter mainAdapter;
    // 线路
    private static final Integer TYPE_LINE = 1;

    public MainPresenter(Context context, MainView mvpView) {
        super(mvpView);
        this.mContext = context;
    }

    public void loadSendCarList(){
        mvpView.showLoading();
        HttpMethods.getInstance().getScheduleList(new Subscriber<List<DepartCar>>() {
            @Override
            public void onCompleted() {
                mvpView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mvpView.disPlay(e.getMessage());
            }

            @Override
            public void onNext(List<DepartCar> waitVehicles) {
                mainAdapter = new MainAdapter(mContext, waitVehicles);
                mvpView.loadSendCarList(mainAdapter);

            }
        }, null, null, lineId);
    }

}
