package com.example.savr.mlayu.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.savr.mlayu.R;

public class Login_user extends AppCompatActivity implements View.OnClickListener{

    private Button Button_LoginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        Button_LoginUser = (Button) findViewById(R.id.btnLoginUser);

        Button_LoginUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        signin();
    }

    private void signin() {
        Intent intent = new Intent(Login_user.this,Data_user.class);
        startActivity(intent);
    }
}
