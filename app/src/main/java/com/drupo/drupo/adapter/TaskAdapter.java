package com.drupo.drupo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.drupo.drupo.R;
import com.drupo.drupo.model.TaskModel;
import com.drupo.drupo.utils.AppUtils;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context mCtx;
    private List<TaskModel> taskModels;

    public TaskAdapter(Context mCtx, List<TaskModel> taskModels) {
        this.mCtx = mCtx;
        this.taskModels = taskModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.tasks_items, null);

        AppUtils.setScaleAnimation(view);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //getting the product of the specified position
        TaskModel taskModel = taskModels.get(position);
        holder.task_name_tv.setText(taskModel.getTaskName());
        holder.time_tv.setText(taskModel.getTaskTime());
        holder.address_tv.setText(taskModel.getTaskAddress());
        holder.task_iv.setImageDrawable(mCtx.getResources().getDrawable(taskModel.getImage()));
    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView task_name_tv, time_tv, address_tv;
        private ImageView task_iv;

        public ViewHolder(View itemView) {
            super(itemView);

            task_name_tv = itemView.findViewById(R.id.task_name_tv);
            time_tv = itemView.findViewById(R.id.time_tv);
            address_tv = itemView.findViewById(R.id.address_tv);
            task_iv = itemView.findViewById(R.id.task_iv);
        }
    }
}
