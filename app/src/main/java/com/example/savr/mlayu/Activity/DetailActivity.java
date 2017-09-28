package com.example.savr.mlayu.Activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.savr.mlayu.Model.Titik;
import com.example.savr.mlayu.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks {

    private TextView profile_nama, tanggal_lari;
    private TextView jaraktextView,durasitextView,kaloritextView;
    private CircleImageView poto_Profil;
    private String id,email,name,img_url;

    private Polyline pol;
    GoogleMap mGoogleMap;

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

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragmentDetail);

        final PolylineOptions polyline = new PolylineOptions().color(Color.RED).geodesic(true);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null){
            id = firebaseUser.getUid();
            name = firebaseUser.getDisplayName();
            img_url = firebaseUser.getPhotoUrl().toString();

            profile_nama.setText(name);
            Glide.with(this).load(img_url).into(poto_Profil);


            //retrieve Titik Latlang
            databaseReference = FirebaseDatabase.getInstance().getReference("Titik").child("Lari");

            ValueEventListener getData=new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    ArrayList<Titik> rute = new ArrayList<>();
//                    for (DataSnapshot titiksnapshot: dataSnapshot.getChildren()){
//                        rute.add(titiksnapshot.getValue(Titik.class));
//                        Fragment googleMap = getSupportFragmentManager().findFragmentById(R.id.mapfragmentDetail);
//
//                        PolylineOptions polylineOptions = new PolylineOptions();
//
//                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference.addValueEventListener(getData);
        }

        Bundle intent = getIntent().getExtras();
        tanggal_lari.setText("Ran on "+intent.getString("tanggal"));
        jaraktextView.setText(intent.getString("jarak"));
        durasitextView.setText(intent.getString("durasi"));
        kaloritextView.setText(intent.getString("kalori"));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        LatLng center = new LatLng(-7.8039629, 110.3900468);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(center, 15);
        mGoogleMap.moveCamera(update);
        PolylineOptions polilyne = new PolylineOptions().color(Color.RED).geodesic(true);
        pol=mGoogleMap.addPolyline(polilyne);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
