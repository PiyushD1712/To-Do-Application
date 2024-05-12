package com.example.to_do_application.views;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.to_do_application.R;
import com.example.to_do_application.listeners.OnDialogCloseListener;
import com.example.to_do_application.model.Work;
import com.example.to_do_application.viewmodel.ToDoViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewTask extends BottomSheetDialogFragment {
    private EditText editAddTask;
    private ImageButton buttonAddTask;
    private ToDoViewModel viewModel;
    private TextView titleTextView;
    private Bundle bundle;

    private Handler handler;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isUpdate = false;
        int id=-1;
        bundle = this.getArguments();
        editAddTask = view.findViewById(R.id.idAddTask);
        buttonAddTask = view.findViewById(R.id.idAddTaskBtn);
        titleTextView = view.findViewById(R.id.idTitleTask);
        viewModel = new ViewModelProvider(getActivity()).get(ToDoViewModel.class);

        if(bundle!=null){
            isUpdate = true;
            id = bundle.getInt("id");
            String task = bundle.getString("task");
            editAddTask.setText(task);
            titleTextView.setText("Update Task");
        }

        boolean finalIsUpdate = isUpdate;
        int finalId = id;
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editAddTask.getText().toString();
                if(finalIsUpdate){
                    Work work = viewModel.getTaskId(finalId);
                    work.setTask(task);
                    viewModel.updateTask(work);
                }
                else {
                    if (!TextUtils.isEmpty(task)) {
                        Work work = new Work();
                        work.setTask(task);
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
                        String formatDate = dateFormat.format(date);
                        work.setDate(formatDate);
                        viewModel.insertNote(work);
                    }
                }
                dismiss();
            }
        });

    }
}
