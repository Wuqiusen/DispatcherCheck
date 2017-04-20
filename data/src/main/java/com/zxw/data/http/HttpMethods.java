package com.zxw.data.http;


import com.zxw.data.http.bean.BaseBean;
import com.zxw.data.http.bean.DepartCar;
import com.zxw.data.http.bean.Line;
import com.zxw.data.http.bean.LineParams;
import com.zxw.data.http.bean.LoginBean;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author：CangJie on 2016/10/12 15:23
 * email：cangjie2016@gmail.com
 */
public class HttpMethods {
//    public static final String BASE_URL = "http://192.168.0.50:8081/yd_control_app/";
//    public static final String BASE_URL = "http://192.168.1.133:8082/yd_control_app/";
    public static final String BASE_URL = "http://120.77.48.103:8080/yd_control_app/";
    public Retrofit retrofit = RetrofitSetting.getInstance();

    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<BaseBean<T>, T> {
        @Override
        public T call(BaseBean<T> httpResult) {
            if (httpResult.returnCode == 505 || httpResult.returnCode == 510) {
                throw new ApiException(httpResult.returnCode ,httpResult.returnInfo);
            }
            return httpResult.returnData;
        }
    }
    //查询售票方式和作业计划方式
    public void lineParams(Subscriber<LineParams> subscriber, String userId, String keyCode, int lineId){
        HttpInterfaces.Browse browse = retrofit.create(HttpInterfaces.Browse.class);
        Observable<LineParams> map = browse.lineParams(userId, keyCode, lineId).map(new HttpResultFunc<LineParams>());
        toSubscribe(map, subscriber);
    }

    public void lines(Subscriber<List<Line>> subscriber, String code, String keyCode, int spotId, int pageNo, int pageSize){
        HttpInterfaces.Browse browse = retrofit.create(HttpInterfaces.Browse.class);
        Observable<List<Line>> map = browse.lines(code, keyCode, spotId, pageNo, pageSize).map(new HttpResultFunc<List<Line>>());
        toSubscribe(map, subscriber);
    }

    public void getScheduleList(Subscriber<List<DepartCar>> subscriber, String userId, String keyCode, int lineId, int pageNo, int pageSize){
        HttpInterfaces.Browse browse = retrofit.create(HttpInterfaces.Browse.class);
        Observable<List<DepartCar>> map = browse.getScheduleList(userId, keyCode, lineId, pageNo, pageSize).map(new HttpResultFunc<List<DepartCar>>());
        toSubscribe(map, subscriber);
    }

    public void login(Subscriber<LoginBean> subscriber, String code, String password, String time){
        HttpInterfaces.User user = retrofit.create(HttpInterfaces.User.class);
        Observable<LoginBean> observable = user.login(code, password, time).map(new HttpResultFunc<LoginBean>());
        toSubscribe(observable, subscriber);
    }


}
