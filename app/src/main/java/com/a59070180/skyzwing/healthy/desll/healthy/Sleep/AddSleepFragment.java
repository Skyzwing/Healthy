package com.a59070180.skyzwing.healthy.desll.healthy.Sleep;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a59070180.skyzwing.healthy.desll.healthy.R;

public class AddSleepFragment extends Fragment {
    SQLiteDatabase myDB;
    Sleep sleep;
    String status;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS sleeps (id INTEGER PRIMARY KEY AUTOINCREMENT, date VARCHAR(10), sleepTime VARCHAR(5), awakeTime VARCHAR(5))");
    Bundle bundle = getArguments();
    try {
        sleep = (Sleep) bundle.getSerializable("sleep object");
        status = "edit";
    }catch (NullPointerException e){
        if (sleep == null)
        {
            status = "new";
        }
        else
        {
            Log.d("Error", "null pointer : " + e.getMessage());
        }
        Log.d("Error", "status: " + e.getMessage());
    }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.framgent_sleep_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (status.equals("edit"))
            setValue();
        initBack();
        initAddBtn();
    }

    public void setValue(){
        EditText date = getView().findViewById(R.id.sleep_form_date);
        EditText sleeping = getView().findViewById(R.id.sleep_form_sleep);
        EditText awake = getView().findViewById(R.id.sleep_form_awake);

        date.setText(sleep.getDate());
        sleeping.setText(sleep.getStartSleepTime());
        awake.setText(sleep.getEndSleepTime());

        Button button = getView().findViewById(R.id.sleep_form_add);
        button.setText("edit");
    }

    public void initBack(){
        Button backBtn = getView().findViewById(R.id.form_back_menu);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    public void initAddBtn(){
        Button button = getView().findViewById(R.id.sleep_form_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText date = getView().findViewById(R.id.sleep_form_date);
                EditText sleeping = getView().findViewById(R.id.sleep_form_sleep);
                EditText awake = getView().findViewById(R.id.sleep_form_awake);
                String dateStr = date.getText().toString();
                String sleepingStr = sleeping.getText().toString();
                String awakeStr = awake.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put("date",dateStr);
                contentValues.put("sleepTime", sleepingStr);
                contentValues.put("awakeTime", awakeStr);

                if (status.equals("new"))
                    myDB.insert("sleeps", null, contentValues);
                else if (status.equals("edit"))
                    myDB.update("sleeps", contentValues, "id = " + sleep.getId(), null);
                Toast.makeText(getContext(), "add new sleep", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        });
    }
}
