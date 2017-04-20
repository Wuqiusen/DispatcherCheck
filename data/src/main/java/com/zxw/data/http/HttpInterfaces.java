package com.zxw.data.http;

import com.zxw.data.http.bean.BaseBean;
import com.zxw.data.http.bean.DepartCar;
import com.zxw.data.http.bean.Line;
import com.zxw.data.http.bean.LineParams;
import com.zxw.data.http.bean.LoginBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * author：CangJie on 2016/10/12 15:14
 * email：cangjie2016@gmail.com
 */
public class HttpInterfaces {

    /**
     * 版本更新
     */
//    public interface UpdateVersion {
//        /**
//         * 获取版本信息
//         */
//        @FormUrlEncoded
//        @POST
//        Observable<BaseBean<VersionBean>> checkVersion(@Url String url, @Field("time") String keyCode);
//
//        /**
//         * 下载安装包
//         */
//        @GET
//        Call<ResponseBody> getFile(@Url String url);
//    }
    /**
     * 获取数据
     */
    public interface Browse {

        @FormUrlEncoded
        @POST("phone/control/manage/task/line/schedule/list/all")
        Observable<BaseBean<LineParams>> lineParams(@Field("userId") String userId,
                                                    @Field("keyCode") String keyCode,
                                                    @Field("lineId") int lineId);

        /**
         * @param code     工号
         * @param keyCode  唯一标识
         * @param pageSize 第几页(可选,如不填默认1)
         * @param pageNo   一页显示多少条记录(如选,如不填默认20)
         * @return 线路列表的观察者
         */
        @FormUrlEncoded
        @POST("phone/control/manage/task/line/list")
        Observable<BaseBean<List<Line>>> lines(@Field("userId") String code,
                                               @Field("keyCode") String keyCode,
                                               @Field("spotId") int spotId,
                                               @Field("pageNo") int pageNo,
                                               @Field("pageSize") int pageSize);

        /**
         * 31.	根据线路id获取待发车计划列表(新)
         */
        @FormUrlEncoded
        @POST("phone/control/manage/task/line/schedule/list/all")
        Observable<BaseBean<List<DepartCar>>> getScheduleList(@Field("userId") String userId,
                                                              @Field("keyCode") String keyCode,
                                                              @Field("lineId") int lineId,
                                                              @Field("pageNo") int pageNo,
                                                              @Field("pageSize") int pageSize);


    }
    /**
     * 用户信息
     */
    public interface User {
        @FormUrlEncoded
        @POST("phone/control/manage/login")
        Observable<BaseBean<LoginBean>> login(@Field("code") String code,
                                              @Field("password") String password,
                                              @Field("time") String time);

    }

    }



