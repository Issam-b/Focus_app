package com.derassom.three_pixels.focus;

import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.derassom.three_pixels.focus.database.FocusDatabase;
import com.derassom.three_pixels.focus.entity.App;
import com.derassom.three_pixels.focus.entity.Task;
import com.derassom.three_pixels.focus.utils.CustomAdapter;
import com.derassom.three_pixels.focus.utils.DatabaseInit;
import com.derassom.three_pixels.focus.utils.TaskModle;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {
    TimePicker timePicker;
    ListView listView;
    EditText addTask;
    FocusDatabase db;
    CustomAdapter adapter;
    List<TaskModle> taskList;
    Handler mHandler;
    boolean changed;

    private long UPDATE_INTERVAL=1000;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        listView = (ListView) findViewById(R.id.listview);
        addTask=(EditText)findViewById(R.id.addTask);
        timePicker=(TimePicker)findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);


        db = FocusDatabase.getFocusDatabase(this);
        mHandler=new Handler();

    }

    Runnable setTasks=new Runnable() {
        @Override
        public void run() {
                showTasks();


        }
    };

    public void showTasks(){
        taskList=setList();
        adapter = new CustomAdapter(this, taskList);
        listView.setAdapter(adapter);

        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TaskModle model = taskList.get(i);
                Task task= db.taskDao().getTask(model.getTaskName());
                Log.d("SELECTED",model.getTaskName());
                task.setTaskName(model.getTaskName());
                if (model.isSelected()){
                    model.setSelected(false);
                    task.setSelectedTask(false);
                    db.taskDao().update(task);
                }
                else {
                    model.setSelected(true);
                    task.setSelectedTask(true);
                    db.taskDao().update(task);
                    Toast.makeText(Settings.this, "Task Selected Successfully", Toast.LENGTH_SHORT).show();
                }
                DatabaseInit.printTasks(db);
                taskList.set(i, model);

                //now update adapter
                adapter.updateRecords(taskList);
            }
        });


    }

    public List<TaskModle> setList(){

    List<TaskModle> standardTasks = new ArrayList<>();

    for(Task task: db.taskDao().getAll()) {
            standardTasks.add(new TaskModle(task.getSelectedTask(), task.getTaskName()));
        }
    return standardTasks;
}

    public void addTask(View view) {

        String add=addTask.getText().toString();
        if(add.matches("")) {
            Toast.makeText(this, "Enter your task", Toast.LENGTH_SHORT).show();
        }
       else {
            Task newTask = new Task();
            newTask.setSelectedTask(false);
            newTask.setTaskComplete(false);
            newTask.setTaskName(addTask.getText().toString());
            db.taskDao().insert(newTask);
            Toast.makeText(this, "Your task is successfully added", Toast.LENGTH_SHORT).show();
            addTask.setText("");
            startRepeatingTask();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void submit(View view) {

        Log.d("timepicker", String.valueOf(timePicker.getHour()));
        Log.d("timepicker", String.valueOf(timePicker.getMinute()));
        for(App app: db.appDao().getAll()){
            app.setTimeAllowed(timePicker.getHour()*60*60*1000+timePicker.getMinute()*60*1000);
            db.appDao().update(app);
        }
        DatabaseInit.printApps(db);

    }

    void startRepeatingTask()
    {
        setTasks.run();
    }

    void stopRepeatingTask()
    {
        mHandler.removeCallbacks(setTasks);
    }

    @Override
    public void onResume() {
        startRepeatingTask();
        super.onResume();
    }
    @Override
    public void onDestroy () {

        super.onDestroy();
        stopRepeatingTask();
    }

}

