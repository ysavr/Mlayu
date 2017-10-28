package com.example.savr.mlayu.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.savr.mlayu.Activity.HomeActivity;
import com.example.savr.mlayu.Model.UserProfile;
import com.example.savr.mlayu.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private LinearLayout Prof_Section;
    private RelativeLayout framelogin;
    private Button SignOut,Button_withemail;
    private SignInButton SignIn;
    private TextView Nama,Email;
    private ImageView Prof_Pic;

    private GoogleApiClient googleApiClient;

    private static final int REQ_CODE = 9001;

    ProgressDialog progressDialog;
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        Prof_Section = (LinearLayout) findViewById(R.id.Prof_section);
//        SignOut = (Button) findViewById(R.id.btn_logout);
        SignIn = (SignInButton) findViewById(R.id.btn_login);
        Button_withemail = (Button) findViewById(R.id.btn_loginemail);
        Button_withemail.setVisibility(View.GONE);

//        Nama = (TextView) findViewById(R.id.name);
        Email = (TextView) findViewById(R.id.email);
//        Prof_Pic = (ImageView) findViewById(R.id.Prof_pic);
        framelogin = (RelativeLayout) findViewById(R.id.layoutlogin);

        SignIn.setOnClickListener(this);
//        SignOut.setOnClickListener(this);
        Button_withemail.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);


//        Prof_Section.setVisibility(View.GONE);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

//        if (firebaseUser!=null){
//            String id = firebaseUser.getUid();
//            progressDialog.show();
//            databaseReference = FirebaseDatabase.getInstance().getReference("user").child(firebaseUser.getUid());
//            ValueEventListener getdata = new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
//                    if (userProfile!=null){
//                        if (userProfile.getGender()==null){
//                            goToDataUser();
//                        }
//                        else{
//                            goToHome();
//                        }
//                    }else {
//                        goToDataUser();
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            };
//            databaseReference.addValueEventListener(getdata);
//        }

    }

    private void goToDataUser() {
        progressDialog.show();
        Intent gotoDataUser = new Intent(getApplicationContext(),Data_user.class);
        startActivity(gotoDataUser);
        progressDialog.dismiss();
    }

    private void goToHome(){
        progressDialog.show();
        Intent gotoMlayuFragment = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(gotoMlayuFragment);
        progressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_login:
                SignIn();
                break;
//            case R.id.btn_logout:
//                SignOut();
//                break;
            case R.id.btn_loginemail:
                Register();
                break;
        }

    }

    private void Register() {
        Intent RegisterLogin = new Intent(Login.this,Register.class);
        startActivity(RegisterLogin);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void SignIn()
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }

    private void SignOut()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    updateUI(false);
                }else{

                }
            }
        });
    }

    private void handleResult(GoogleSignInResult result)
    {
        if (result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
            String name = account.getDisplayName();
            String email = account.getEmail();
            String img_url = account.getPhotoUrl().toString();
//            Nama.setText(name);
//            Email.setText(email);
//            Glide.with(this).load(img_url).into(Prof_Pic);

            progressDialog.setMessage("Please wait...");
            progressDialog.show();
         //  updateUI(true);
        }
        else
        {
          //  updateUI(false);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("id",acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("success","sad");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(true);
//                            Intent signin = new Intent(Login.this,HomeActivity.class);
//                            startActivity(signin);
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (firebaseUser!=null){
//                                String id = firebaseUser.getUid();
//
//                                databaseReference = FirebaseDatabase.getInstance().getReference("user").child(firebaseUser.getUid());
//                                ValueEventListener getdata = new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
//                                        if (userProfile!=null){
//                                            if (userProfile.getGender()==null)goToDataUser();
//                                            else goToHome();
//                                        }else {
//                                            goToDataUser();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                };
//                                databaseReference.addValueEventListener(getdata);
                            }
                            Intent signin = new Intent(Login.this,Data_user.class);
                            startActivity(signin);
                            progressDialog.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(false);
                        }
                    }
                });
    }

    private void updateUI (boolean isLogin)
    {
//        if (isLogin)
//        {
//            Prof_Section.setVisibility(View.GONE);
//            framelogin.setVisibility(View.GONE);
//        }
//        else
//        {
//            Prof_Section.setVisibility(View.GONE);
//            framelogin.setVisibility(View.VISIBLE);
//        }
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
