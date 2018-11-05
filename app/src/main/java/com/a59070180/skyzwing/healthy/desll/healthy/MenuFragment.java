package com.a59070180.skyzwing.healthy.desll.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.a59070180.skyzwing.healthy.desll.healthy.Sleep.SleepFragment;
import com.a59070180.skyzwing.healthy.desll.healthy.Weight.WeightFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MenuFragment extends Fragment {
    ArrayList<String> _menu;
    FirebaseAuth fbAuth;

    public MenuFragment(){
        _menu = new ArrayList<>();
        _menu.add("BMI");
        _menu.add("Weight");
        _menu.add("Sleep");
        _menu.add("Sign out");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fbAuth = FirebaseAuth.getInstance();

        final ArrayAdapter<String> _menuAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, _menu);

        ListView _menuList = getView().findViewById(R.id.menu_list);
        _menuList.setAdapter(_menuAdapter);
        _menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Menu", "Click on menu = "+ _menu.get(position));

                if (_menu.get(position).equals("BMI")){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new BMIFragment()).addToBackStack(null).commit();
                    Log.d("USER", "Go to BMI");
                }
                else if (_menu.get(position).equals("Weight"))
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFragment()).addToBackStack(null).commit();
                else if (_menu.get(position).equals("Sleep"))
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new SleepFragment()).addToBackStack(null).commit();
                else if (_menu.get(position).equals("Sign out")) {
                    fbAuth.signOut();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).commit();
                }
            }
        });
    }
}
