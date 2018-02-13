package com.example.savr.mlayu.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.savr.mlayu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_user extends AppCompatActivity implements View.OnClickListener{

    private Button Button_LoginUser;
    private EditText editTextEmailLogin,editTextpasswordLogin;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),DummyActivity.class));
        }
        editTextEmailLogin = (EditText) findViewById(R.id.usernameLogin);
        editTextpasswordLogin = (EditText) findViewById(R.id.passwordLogin);

        Button_LoginUser = (Button) findViewById(R.id.btnLoginUser);

        Button_LoginUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        signin();
    }

    private void signin() {
        String email = editTextEmailLogin.getText().toString().trim();
        String password = editTextpasswordLogin.getText().toString().trim();

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

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(Login_user.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),DummyActivity.class));
                        }
                    }
                });

    }
}
