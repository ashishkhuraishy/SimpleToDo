package com.blogspot.codecampanion.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder> {

    private List<SubTask> subTaskList = new ArrayList<>();

    public void setSubTaskList(List<SubTask> subTaskList) {
        this.subTaskList = subTaskList;
    }

    public class SubTaskViewHolder extends RecyclerView.ViewHolder {

        TextView subTasktext;
        public SubTaskViewHolder(@NonNull View itemView) {
            super(itemView);

            subTaskList = itemView.findViewById(R.id.textViewsubTask);
        }
    }

    @NonNull
    @Override
    public SubTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_list_activity, parent, false);

        return new SubTaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTaskViewHolder holder, int position) {
        SubTask currentPosition = subTaskList.get(position);

        holder.subTasktext.setText(currentPosition.getSubTask());

    }

    @Override
    public int getItemCount() {
        return subTaskList.size();
    }


}
