package com.example.savr.mlayu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class Login extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private LinearLayout Prof_Section;
    private Button SignOut;
    private SignInButton SignIn;
    private TextView Nama,Email;
    private ImageView Prof_Pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Prof_Section = (LinearLayout) findViewById(R.id.Prof_section);
        SignOut = (Button) findViewById(R.id.btn_logout);
        SignIn = (SignInButton) findViewById(R.id.btn_login);
        Nama = (TextView) findViewById(R.id.name);
        Email = (TextView) findViewById(R.id.email);
        Prof_Pic = (ImageView) findViewById(R.id.Prof_pic);
        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        Prof_Section.setVisibility(View.GONE);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_login:
                SignIn();
                break;
            case R.id.btn_logout:
                SignOut();
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void SignIn()
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        Intent signin = new Intent(Login.this,HomeActivity.class);
        startActivityForResult(intent,REQ_CODE);
        startActivity(signin);

    }

    private void SignOut()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    private void handleResult(GoogleSignInResult result)
    {
        if (result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String img_url = account.getPhotoUrl().toString();
            Nama.setText(name);
            Email.setText(email);
            Glide.with(this).load(img_url).into(Prof_Pic);
            updateUI(true);
        }
        else
        {
            updateUI(false);
        }
    }

    private void updateUI (boolean isLogin)
    {
        if (isLogin)
        {
            Prof_Section.setVisibility(View.VISIBLE);
            SignIn.setVisibility(View.GONE);
        }
        else
        {
            Prof_Section.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
        }


    }

    @Override
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
}
