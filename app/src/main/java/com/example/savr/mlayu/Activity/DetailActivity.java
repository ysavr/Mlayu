package com.example.savr.mlayu.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.savr.mlayu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {
    private TextView profile_nama, tanggal_lari;
    private TextView jaraktextView,durasitextView,kaloritextView;
    private CircleImageView poto_Profil;
    private String id,email,name,img_url;

    ProgressDialog progressDialog;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        poto_Profil = (CircleImageView) findViewById(R.id.detailUserPhoto);
        profile_nama = (TextView) findViewById(R.id.detailProfile_nama);
        tanggal_lari = (TextView) findViewById(R.id.detailTanggalLari);

        jaraktextView = (TextView) findViewById(R.id.detailjarak);
        durasitextView = (TextView) findViewById(R.id.detailDurasi);
        kaloritextView = (TextView) findViewById(R.id.detailKalori);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null){
            id = firebaseUser.getUid();
            name = firebaseUser.getDisplayName();
            img_url = firebaseUser.getPhotoUrl().toString();

            profile_nama.setText(name);
            Glide.with(this).load(img_url).into(poto_Profil);

        }

        Bundle intent = getIntent().getExtras();
        tanggal_lari.setText("Ran on "+intent.getString("tanggal"));
        jaraktextView.setText(intent.getString("jarak"));
        durasitextView.setText(intent.getString("durasi"));
        kaloritextView.setText(intent.getString("kalori"));
    }
}
