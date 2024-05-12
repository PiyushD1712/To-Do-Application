package com.example.to_do_application.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.to_do_application.R;
import com.example.to_do_application.adapters.WorkAdapter;
import com.example.to_do_application.databinding.ActivityMainBinding;
import com.example.to_do_application.listeners.OnDialogCloseListener;
import com.example.to_do_application.model.Work;
import com.example.to_do_application.viewmodel.ToDoViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {
    private ActivityMainBinding binding;
    private WorkAdapter workAdapter;
    private ToDoViewModel viewModel;

    private List<Work> workList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ToDoViewModel.class);
        fetchData();
        binding.idFABBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask newTask =  new AddNewTask();
                newTask.show(getSupportFragmentManager(),null);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Work work = workList.get(position);
                viewModel.deleteTask(work);
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                workList.remove(position);
                workAdapter.notifyItemRemoved(position);
            }
        }).attachToRecyclerView(binding.idRecyclerView);
    }
    public void fetchData(){
        workList = viewModel.getTasks();
        workAdapter =  new WorkAdapter(this,workList);
        binding.idRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.idRecyclerView.setAdapter(workAdapter);
        workAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        fetchData();
        workAdapter.notifyDataSetChanged();
    }
}