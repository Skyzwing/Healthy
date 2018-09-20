package com.a59070180.skyzwing.healthy.desll.healthy;

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
import android.widget.TextView;
import android.widget.Toast;

import com.a59070180.skyzwing.healthy.desll.healthy.MenuFragment;
import com.a59070180.skyzwing.healthy.desll.healthy.R;


public class BMIFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCalculateBMI();
        previousPageBtn();
    }
    void initCalculateBMI(){
        Button _calculate = getView().findViewById(R.id.bmi_calculate_btn);
        _calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _weight = getView().findViewById(R.id.bmi_weight);
                EditText _height = getView().findViewById(R.id.bmi_height);
                String _weightStr = _weight.toString();
                String _heightStr = _height.toString();
                TextView _bmiResult_box = getView().findViewById(R.id.bmi_Result);

                if (_weightStr.isEmpty() || _heightStr.isEmpty()){
                    Toast.makeText(getActivity(), "Please fill weight and height!", Toast.LENGTH_SHORT).show();
                    Log.d("BMI", "Please check weight and height!");
                }
                else{
                    Double _heightCalculate = Double.parseDouble(_heightStr)/100;
                    Double _weightCalculate = Double.parseDouble(_weightStr);
                    Double _resultBmi = _weightCalculate/(Math.pow(_heightCalculate,2));
                    _bmiResult_box.setText(String.format("%.2f", _resultBmi));
                }
            }
        });
    }

    void previousPageBtn(){
        Button _backBtn = getView().findViewById(R.id.previous);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).addToBackStack(null).commit();
            }
        });
    }
}
