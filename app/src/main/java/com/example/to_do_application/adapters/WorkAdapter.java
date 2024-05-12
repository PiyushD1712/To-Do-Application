package com.example.to_do_application.adapters;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_application.R;
import com.example.to_do_application.databinding.RecyclerItemTasksBinding;
import com.example.to_do_application.model.Work;
import com.example.to_do_application.viewmodel.ToDoViewModel;
import com.example.to_do_application.views.AddNewTask;

import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.MyViewHolder> {
    private final Context context;
    private List<Work> list;

    public WorkAdapter(Context context, List<Work> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerItemTasksBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.recycler_item_tasks,
                parent,false
        );
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Work work = list.get(position);
        holder.binding.setWork(work);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final RecyclerItemTasksBinding binding;

        public MyViewHolder(RecyclerItemTasksBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Work work = list.get(position);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    AddNewTask newTask = new AddNewTask();

                    Bundle bundle = new Bundle();
                    bundle.putInt("update",-1);
                    bundle.putInt("id",work.getId());
                    bundle.putString("task",work.getTask());

                    newTask.setArguments(bundle);
                    newTask.show(activity.getSupportFragmentManager(),null);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
