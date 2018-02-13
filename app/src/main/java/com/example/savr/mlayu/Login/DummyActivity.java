package com.example.savr.mlayu.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.savr.mlayu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DummyActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button btnLogout;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,Login_user.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        name = (TextView) findViewById(R.id.name);
        btnLogout = (Button) findViewById(R.id.btnLogoutUser);

        btnLogout.setOnClickListener(this);

        name.setText("Hallo"+user.getEmail());
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,Login_user.class));
        }
    }
}
