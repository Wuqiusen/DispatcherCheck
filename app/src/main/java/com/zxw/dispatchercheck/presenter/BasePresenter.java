package com.zxw.dispatchercheck.presenter;


import com.zxw.dispatchercheck.MyApplication;
import com.zxw.dispatchercheck.presenter.view.BaseView;
import com.zxw.dispatchercheck.utils.SpUtils;

/**
 * author：CangJie on 2016/8/18 14:38
 * email：cangjie2016@gmail.com
 */
public class BasePresenter<V extends BaseView> {
    public V mvpView;
    protected int httpCount;

    public BasePresenter(V mvpView) {
        this.mvpView = mvpView;
    }

    protected String keyCode(){
        return SpUtils.getCache(MyApplication.mContext, SpUtils.KEYCODE);
    }
//    protected String userName(){
//        return SpUtils.getCache(MyApplication.mContext, SpUtils.USERPHONE);
//    }
    protected String userId(){
        return SpUtils.getCache(MyApplication.mContext, SpUtils.USER_ID);
    }
    protected String name(){
        return SpUtils.getCache(MyApplication.mContext, SpUtils.NAME);
    }
//    protected String timestamp(){
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
//        return formatter.format(curDate);
//    }
//    protected String md5(String str){
//        return MD5.MD5Encode(str);
//    }
}
