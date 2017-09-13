package com.example.savr.mlayu.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.savr.mlayu.HomeActivity;
import com.example.savr.mlayu.Model.UserProfile;
import com.example.savr.mlayu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.id;

public class Data_user extends AppCompatActivity implements View.OnClickListener{
    private TextView profile_nama,profile_email, Textprofile_umur,Textprofile_tinggi,Textprofile_berat;
    private CircleImageView poto_Profil;
    private Button Button_save;
    private RadioGroup radioGroupJeniskel;
    private RadioButton radioLK,radioPR;
    private String id,email,name,img_url;

    int berat_badan,tinggi,umur;

    ProgressDialog progressDialog;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user);

        progressDialog = new ProgressDialog(this);

        profile_nama = (TextView) findViewById(R.id.profile_nama);
        profile_email = (TextView) findViewById(R.id.profile_email);
        poto_Profil = (CircleImageView) findViewById(R.id.userphoto);

        radioGroupJeniskel = (RadioGroup) findViewById(R.id.radioJK);
        Textprofile_umur = (TextView) findViewById(R.id.profile_umur);
        Textprofile_tinggi = (TextView) findViewById(R.id.profile_tinggi);
        Textprofile_berat = (TextView) findViewById(R.id.profile_berat);
        radioLK = (RadioButton) findViewById(R.id.Radiomale);
        radioPR = (RadioButton) findViewById(R.id.Radiofemale);

        Button_save = (Button) findViewById(R.id.btnsimpan_profile);

        Button_save.setOnClickListener(this);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            id = firebaseUser.getUid();
            name = firebaseUser.getDisplayName();
            email = firebaseUser.getEmail();
            img_url = firebaseUser.getPhotoUrl().toString();

            profile_nama.setText(name);
            profile_email.setText(email);
            Glide.with(this).load(img_url).into(poto_Profil);

            databaseReference = FirebaseDatabase.getInstance().getReference("user").child(firebaseUser.getUid());
            ValueEventListener getdata = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    berat_badan=userProfile.getBerat();
                    Log.d("Berat badan: ", berat_badan+" kg");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference.addValueEventListener(getdata);
        }
    }

    @Override
    public void onClick(View v) {
        profile_nama.getText().toString();
        profile_email.getText().toString();
        Integer berat = Integer.parseInt(Textprofile_berat.getText().toString());
        Integer tinggi = Integer.parseInt(Textprofile_tinggi.getText().toString());
        Integer umur = Integer.parseInt(Textprofile_umur.getText().toString());

        String gender = "Laki-laki";
        if (radioGroupJeniskel.getCheckedRadioButtonId()==R.id.Radiomale){
            gender = "Laki-laki";
        }else if (radioGroupJeniskel.getCheckedRadioButtonId()==R.id.Radiofemale){
            gender = "Perempuan";
        }else {
            Toast.makeText(this, "Pilih Jenis kelamin", Toast.LENGTH_SHORT).show();
        }
        UserProfile userProfile = new UserProfile(id,name,email,gender,umur,berat,tinggi);
        registerUserProfile(userProfile);
    }

    //insert userprofile ke database
    private void registerUserProfile(UserProfile userProfile){
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userProfile.getId());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        databaseReference.setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(Data_user.this, "Berhasil Simpan", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent gotoMlayuFragment = new Intent(Data_user.this, HomeActivity.class);
                    startActivity(gotoMlayuFragment);
                }
            }
        });
    }
}