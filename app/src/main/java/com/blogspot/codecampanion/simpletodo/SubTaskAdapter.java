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

    public void setListener(SetOnItemClickListener listener) {
        this.listener = listener;
    }

    private SetOnItemClickListener listener;

    public void setSubTaskList(List<SubTask> subTaskList) {
        this.subTaskList = subTaskList;
        notifyDataSetChanged();
    }

    public class SubTaskViewHolder extends RecyclerView.ViewHolder {

        TextView subTasktext;
        public SubTaskViewHolder(@NonNull View itemView) {
            super(itemView);

            subTasktext = itemView.findViewById(R.id.textViewsubTask);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null & position != RecyclerView.NO_POSITION){
                        listener.OnItemClick(subTaskList.get(position));
                    }

                }
            });
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


    public interface SetOnItemClickListener{
        void OnItemClick(SubTask subTask);
    }

    SubTask getSubTask(int position){
        return subTaskList.get(position);
    }
}
