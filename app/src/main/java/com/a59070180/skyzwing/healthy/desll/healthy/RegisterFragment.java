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

    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();

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

    public void initRegisterBtn(){

        Button _registerBtn = getView().findViewById(R.id.newAccount);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _email = getView().findViewById(R.id.register_email);
                EditText _password = getView().findViewById(R.id.register_password);
                EditText _rePassword = getView().findViewById(R.id.register_re_password);

                String _newEmailStr = _email.toString();
                String _newPasswordStr = _password.toString();
                String _newRePasswordStr = _rePassword.toString();

                if (_newPasswordStr.equals(_newRePasswordStr)){
                    fbAuth.createUserWithEmailAndPassword(_newEmailStr, _newPasswordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sendVerifiedEmail(authResult.getUser());
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).commit();
                            Toast.makeText(getActivity(), "Register Complete", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Register Fails", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if (_newEmailStr.isEmpty() || _newPasswordStr.isEmpty() || _newRePasswordStr.isEmpty()){
                    Toast.makeText(getActivity(), "Can't be blank.", Toast.LENGTH_SHORT).show();
                    Log.d("REGISTER", "Can't be blank");
                }
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
