package com.example.savr.mlayu.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.savr.mlayu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister,buttonSigninWithEmail;
    private EditText editTextEmail,editTextUsername,editTextpassword;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){

        }

        buttonSigninWithEmail = (Button) findViewById(R.id.btnSignin);
        buttonRegister = (Button) findViewById(R.id.btnRegister);

        editTextEmail = (EditText) findViewById(R.id.emailRegister);
//        editTextUsername = (EditText) findViewById(R.id.usernameRegister);
        editTextpassword = (EditText) findViewById(R.id.passwordRegister);

        buttonSigninWithEmail.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnSignin:
                signinWithEmail();
                break;
            case R.id.btnRegister:
                registerUser();
                break;
        }
    }

    private void registerUser() {
//        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length()<6){
            Toast.makeText(getApplicationContext(),"Password too short, enter minimum 6 character",Toast.LENGTH_SHORT).show();
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this,"Registered Succesfully",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),DummyActivity.class));
                            progressDialog.dismiss();
                        }else {
                            Toast.makeText(Register.this,"Could not register",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }

    private void signinWithEmail() {
        Intent LoginUser = new Intent(Register.this,Login_user.class);
        startActivity(LoginUser);
    }
}
