package com.zxw.dispatchercheck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxw.data.http.bean.DepartCar;
import com.zxw.data.http.bean.LineParams;
import com.zxw.dispatchercheck.R;
import com.zxw.dispatchercheck.presenter.MainPresenter;
import com.zxw.dispatchercheck.utils.DisplayTimeUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.LineHolder> {
    private Context mContext;
    private List<DepartCar> mDatas;
    private final LayoutInflater mLayoutInflater;

    public MainAdapter(Context mContext, List<DepartCar> waitVehicles) {
        this.mContext = mContext;
        this.mDatas = waitVehicles;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public LineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.item_car, parent, false);
        return new LineHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final LineHolder holder, final int position) {
        holder.tvCarSequence.setText((position + 1) + "");

        holder.tvCarCode.setText(mDatas.get(position).getCode());
        if (mDatas.get(position).getTaskEditBelongId() == mDatas.get(position).getTaskEditRunId()){
            holder.tvCarCode.setBackground(mContext.getResources().getDrawable(R.drawable.ll_stop_car_red_btn_bg));
        }else {
            holder.tvCarCode.setBackground(mContext.getResources().getDrawable(R.drawable.ll_stop_car_green_btn_bg));
        }

        // 驾驶员
        holder.tvDriver.setText(mDatas.get(position).getDriverName());

        // 乘务员
        holder.tvTrainman.setVisibility(View.INVISIBLE);

        // 计划时刻
        holder.tvPlanTime.setText(DisplayTimeUtil.substring(mDatas.get(position).getVehTime()));
        // 发车间隔
        holder.tvIntervalTime.setText(String.valueOf(mDatas.get(position).getSpaceTime()));
        // 到站时刻
        holder.tvSystemEnterTime.setText(DisplayTimeUtil.substring(mDatas.get(position).getArriveTime()));
        // 非营运任务
        holder.tvNoOperationTask.setText(noOperationStatus(mDatas.get(position).getUnRunTaskStatus()));

        // 发车
        holder.tvIsDouble.setText(mDatas.get(position).getIsDouble() == 0 ?"单班":"双班");
        // 任务类型
        holder.tvWorkType.setText(mDatas.get(position).getTypeName());



    }
    private String noOperationStatus(int status){
        if (status == 1)
            return "无";
        else if (status == 2)
            return "已完成";
        else
            return "未完成";
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class LineHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_car_sequence)
        TextView tvCarSequence;
        @Bind(R.id.tv_car_code)
        TextView tvCarCode;
        @Bind(R.id.tv_system_enter_time)
        TextView tvSystemEnterTime;
        @Bind(R.id.tv_plan_time)
        TextView tvPlanTime;
        @Bind(R.id.tv_interval_time)
        TextView tvIntervalTime;
        @Bind(R.id.tv_is_double)
        TextView tvIsDouble;
        @Bind(R.id.tv_driver)
        TextView tvDriver;
        @Bind(R.id.tv_trainman)
        TextView tvTrainman;
        @Bind(R.id.tv_work_type)
        TextView tvWorkType;
        @Bind(R.id.tv_no_operation_task)
        TextView tvNoOperationTask;
        LineHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
