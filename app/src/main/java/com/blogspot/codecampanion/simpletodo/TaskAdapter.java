package com.blogspot.codecampanion.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskViewHolder> {

    private OnItemClickListener listener;

    void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    TaskAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getTask().equals(newItem.getTask());
        }
    };

    class TaskViewHolder extends RecyclerView.ViewHolder{

        private TextView task;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            task = itemView.findViewById(R.id.textViewTask);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && RecyclerView.NO_POSITION != position){
                        listener.OnItemClick(getItem(position));
                    }
                }
            });

        }
    }

    /*Getting Item To be Deleted while Swiping*/
    Task getTask(int position){
        return getItem(position);
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.task_list_activity, parent, false
        );

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentItem = getItem(position);

        holder.task.setText(currentItem.getTask());

    }

    public interface OnItemClickListener{
        void OnItemClick(Task task);
    }


}
