package com.example.savr.mlayu.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.savr.mlayu.R;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister,buttonSigninWithEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonSigninWithEmail = (Button) findViewById(R.id.btnSignin);
        buttonRegister = (Button) findViewById(R.id.btnRegister);

        buttonSigninWithEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnSignin:
                signinWithEmail();
                break;
            case R.id.btnRegister:
                break;
        }
    }

    private void signinWithEmail() {
        Intent LoginUser = new Intent(Register.this,Login_user.class);
        startActivity(LoginUser);
    }
}
