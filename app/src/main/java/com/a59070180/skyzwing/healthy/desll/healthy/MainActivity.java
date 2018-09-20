package com.a59070180.skyzwing.healthy.desll.healthy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore fbStore;
    FirebaseAuth fbAuth;
    FirebaseUser fbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbAuth = FirebaseAuth.getInstance();
        fbUser = fbAuth.getCurrentUser();
        fbStore = FirebaseFirestore.getInstance();

        if(savedInstanceState == null) {
            if (fbUser == null)
                getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).commit();
            else
                getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).commit();
        }
    }
}
