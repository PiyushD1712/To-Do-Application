package com.example.to_do_application.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.to_do_application.model.Work;
import com.example.to_do_application.repository.ToDoDatabase;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel {

    private ToDoDatabase repositoryDB;

    public ToDoViewModel(@NonNull Application application) {
        super(application);
        repositoryDB = new ToDoDatabase(application);
    }
    public void insertNote(Work work){
        repositoryDB.insertTask(work);
    }
    public List<Work> getTasks(){
        return repositoryDB.getTasks();
    }
    public void deleteTask(Work work){
        repositoryDB.deleteTask(work);
    }
    public void updateTask(Work work){
        repositoryDB.updateTask(work);
    }
    public Work getTaskId(int id){
        return repositoryDB.getTaskFromId(id);
    }
}
