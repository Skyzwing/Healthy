package com.a59070180.skyzwing.healthy.desll.healthy.Sleep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.a59070180.skyzwing.healthy.desll.healthy.R;

import java.util.ArrayList;

public class SleepFragment extends Fragment{
    private SQLiteDatabase myDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        myDB.execSQL("create table if not exists sleeps (id integer primary key autoincrement, date varchar(10), sleepTime int(5), awakeTime int(5))");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBack();
        initAddButton();
        initShowTime();

    }

    public void initBack(){
        Button backBtn = getView().findViewById(R.id.back_menu);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    public void initAddButton(){
        Button addBtn = getView().findViewById(R.id.add_sleep);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new AddSleepFragment()).addToBackStack(null).commit();
            }
        });
    }

    public void initShowTime(){
        Cursor cursor = myDB.rawQuery("select id, date, sleepTime, awakeTime from sleeps", null);
        final ArrayList<Sleep> sleepList = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String date = cursor.getString(1);
            String sleep = cursor.getString(2);
            String awake = cursor.getString(3);

            Sleep sleepC = new Sleep();
            sleepC.setId(id);
            sleepC.setDate(date);
            sleepC.setStartSleepTime(sleep);
            sleepC.setEndSleepTime(awake);
            sleepList.add(sleepC);
        }
        cursor.close();

        ListView listView = getView().findViewById(R.id.menu_sleep);
        SleepAdapter sleepAdapter = new SleepAdapter(getActivity(), R.layout.fragment_sleep_item, sleepList);
        listView.setAdapter(sleepAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("sleep object", sleepList.get(position));
                Fragment addSleepFragment = new AddSleepFragment();
                addSleepFragment.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.main_view, addSleepFragment).addToBackStack(null).commit() ;
            }
        });
    }
}
