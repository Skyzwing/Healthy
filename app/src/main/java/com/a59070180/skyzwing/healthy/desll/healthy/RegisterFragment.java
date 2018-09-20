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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment {


    private FirebaseAuth fbAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRegisterBtn();
    }

    private void initRegisterBtn(){

        fbAuth = FirebaseAuth.getInstance();
        Button _registerBtn = getView().findViewById(R.id.newAccount);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText _email = getView().findViewById(R.id.register_email);
                EditText _password = getView().findViewById(R.id.register_password);
                EditText _rePassword = getView().findViewById(R.id.register_re_password);

                String _newEmailStr = _email.getText().toString();
                String _newPasswordStr = _password.getText().toString();
                String _newRePasswordStr = _rePassword.getText().toString();

                if (_newEmailStr.isEmpty() || _newPasswordStr.isEmpty() || _newRePasswordStr.isEmpty()){
                    Toast.makeText(getActivity(), "Can't be blank.", Toast.LENGTH_SHORT).show();
                    Log.d("REGISTER", "Can't be blank");
                }
                else if(_newPasswordStr.equals(_newRePasswordStr)){
                    fbAuth.createUserWithEmailAndPassword(_newEmailStr, _newPasswordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sendVerifiedEmail(authResult.getUser());
                            Toast.makeText(getActivity(), "Register Complete", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).addToBackStack(null).commit();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Register Fails", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if (_newPasswordStr.length() < 6)
                    Toast.makeText(getActivity(), "Please fill Password at least 6 or more ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendVerifiedEmail (FirebaseUser _user){
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
