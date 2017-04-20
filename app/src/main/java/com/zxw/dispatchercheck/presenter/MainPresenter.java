package com.zxw.dispatchercheck.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.zxw.data.http.HttpMethods;
import com.zxw.data.http.bean.DepartCar;
import com.zxw.data.http.bean.Line;
import com.zxw.data.http.bean.LineParams;
import com.zxw.dispatchercheck.Constants;
import com.zxw.dispatchercheck.adapter.MainAdapter;
import com.zxw.dispatchercheck.presenter.view.MainView;
import com.zxw.dispatchercheck.utils.DebugLog;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * author：CangJie on 2016/9/20 17:26
 * email：cangjie2016@gmail.com
 */
public class MainPresenter extends BasePresenter<MainView> {
    private Context mContext;
    private int lineId = 3;
    private MainAdapter mainAdapter;
    private int pageNo = 1;

    public MainPresenter(Context context, MainView mvpView) {
        super(mvpView);
        this.mContext = context;
    }

    public void loadSendCarList() {
        HttpMethods.getInstance().getScheduleList(new Subscriber<List<DepartCar>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                mvpView.disPlay(e.getMessage());
            }

            @Override
            public void onNext(List<DepartCar> waitVehicles) {
                DebugLog.e("加载-------");
                if(waitVehicles != null && !waitVehicles.isEmpty()){
                    mainAdapter = new MainAdapter(mContext, waitVehicles);
                    mvpView.loadSendCarList(mainAdapter);
                }
                    if (waitVehicles.size() == Constants.PAGE_SIZE){
                        pageNo ++;
                    }else {
                        pageNo = 1;
                    }


            }
        }, null, null, lineId, pageNo, Constants.PAGE_SIZE);
    }



}
