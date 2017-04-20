package com.zxw.dispatchercheck.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.zxw.data.http.HttpMethods;
import com.zxw.data.http.bean.DepartCar;
import com.zxw.data.http.bean.Line;
import com.zxw.data.http.bean.LineParams;
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
    private List<String> mWaitVehicles = new ArrayList<>();
    private List<String> mWaitVehiclesBefore = new ArrayList<>();

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
                if (mWaitVehiclesBefore.isEmpty()) {
                    for (DepartCar departCar : waitVehicles) {
                        mWaitVehiclesBefore.add(departCar.getCode());
                    }
                }
                else {
                    for (DepartCar departCar : waitVehicles) {
                        mWaitVehicles.add(departCar.getCode());
                    }
                }
                if (!checkList(mWaitVehiclesBefore, mWaitVehicles)){
                    DebugLog.e("刷新-------");
                    mainAdapter = new MainAdapter(mContext, waitVehicles);
                    mvpView.loadSendCarList(mainAdapter);
                }
                if (mWaitVehicles != null && !mWaitVehicles.isEmpty()){
                    mWaitVehiclesBefore.clear();
                    mWaitVehiclesBefore.addAll(mWaitVehicles);
                    mWaitVehicles.clear();
                }

            }
        }, null, null, lineId);
    }


    private boolean checkList(List<String> list1, List<String> list2){
        boolean isSame = true;
        if (list1.size() == list2.size()){
            for (int i = 0; i < list1.size(); i++){
                if (!TextUtils.equals(list1.get(i), list2.get(i))){
                    isSame = false;
                }
            }
        }else {
            isSame = false;
        }
        return isSame;
    }

}
