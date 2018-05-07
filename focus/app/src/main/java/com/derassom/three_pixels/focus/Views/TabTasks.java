package com.derassom.three_pixels.focus.Views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.derassom.three_pixels.focus.R;
import com.derassom.three_pixels.focus.database.FocusDatabase;
import com.derassom.three_pixels.focus.entity.Task;
import com.derassom.three_pixels.focus.utils.CustomAdapter;
import com.derassom.three_pixels.focus.utils.TaskAdapter;
import com.derassom.three_pixels.focus.utils.TaskListModle;
import com.derassom.three_pixels.focus.utils.TaskModle;

import java.util.ArrayList;
import java.util.List;

// Created by Lokmane Krizou on 4/22/2018.
public class TabTasks  extends Fragment {
    ListView listView;
    FocusDatabase db;
    TaskAdapter adapter;
    List<TaskListModle> taskModleList;
    Handler mHandler;
    private long UPDATE_INTERVAL=1000;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        listView=(ListView)rootView.findViewById(R.id.taskList);

        return rootView;
    }

    Runnable taskUpadate=new Runnable() {
    @Override
    public void run() {
        updateTasks();
    }
};

    private void updateTasks(){
        db = FocusDatabase.getFocusDatabase(getActivity());
        List<Task> taskList = db.taskDao().getSelectedTasks(true);
        mHandler=new Handler();
        taskModleList = new ArrayList<>();
        if(taskList.size()==0){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("No Task have been selected, Go to Settings to add your tasks")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(getActivity(), com.derassom.three_pixels.focus.Settings.class));
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                    });
                builder.show();
        }
        for(Task task: taskList) {
            Log.d("AddTask",task.getTaskName());
            taskModleList.add(new TaskListModle(task.getTaskComplete(), task.getTaskName(),"Task"+task.getTaskid()));
        }
        adapter = new TaskAdapter(getActivity(), taskModleList);
        try {
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    TaskListModle model = taskModleList.get(i);
                    Task task= db.taskDao().getTask(model.getTaskName());
                    task.setTaskName(model.getTaskName());
                    if (model.isSelected()) {
                        model.setSelected(false);
                        task.setTaskComplete(false);
                        db.taskDao().update(task);

                    } else {
                        model.setSelected(true);
                        task.setTaskComplete(true);
                        db.taskDao().update(task);
                    }
                    taskModleList.set(i, model);
                    //now update adapter
                    adapter.updateRecords(taskModleList);
                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity(), "No Tasks", Toast.LENGTH_SHORT).show();
        }


    }
    void startRepeatingTask()
    {
        taskUpadate.run();
    }

    void stopRepeatingTask()
    {
        mHandler.removeCallbacks(taskUpadate);
    }

    @Override
    public void onResume() {

        super.onResume();
        startRepeatingTask();
    }
    @Override
    public void onDestroy () {

        super.onDestroy();
        stopRepeatingTask();
    }
}
