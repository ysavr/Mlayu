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
    private String id,name,img_url;
    private Double mylatitude;
    private Double mylongitude;

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragmentDetail);
        mapFragment.getMapAsync(this);

        final PolylineOptions polyline = new PolylineOptions().color(Color.RED).geodesic(true);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null){
            id = firebaseUser.getUid();
            name = firebaseUser.getDisplayName();
            img_url = firebaseUser.getPhotoUrl().toString();

            profile_nama.setText(name);
            Glide.with(this).load(img_url).into(poto_Profil);


            //retrieve Titik Latlang

        }

        Bundle intent = getIntent().getExtras();
        intent.getString("id");
        tanggal_lari.setText("Ran on "+intent.getString("tanggal"));
        jaraktextView.setText(intent.getString("jarak"));
        durasitextView.setText(intent.getString("durasi"));
        kaloritextView.setText(intent.getString("kalori"));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
//
//        LatLng center = new LatLng(-7.8039629, 110.3900468);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(center, 15);
//        mGoogleMap.moveCamera(update);
//        PolylineOptions polilyne = new PolylineOptions().color(Color.RED).geodesic(true);
//        pol=mGoogleMap.addPolyline(polilyne);

        databaseReference = FirebaseDatabase.getInstance().getReference("Titik");

        ValueEventListener getData=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<LatLng> rute = new ArrayList<>();
                PolylineOptions polilyne = new PolylineOptions().color(Color.RED).geodesic(true);
                pol=mGoogleMap.addPolyline(polilyne);

                for (DataSnapshot titiksnapshot: dataSnapshot.getChildren()){
                    Titik titik=titiksnapshot.getValue(Titik.class);
                    if (titik!=null) {
                        Log.d("titik", String.valueOf(titik.getLatitude()));

                        LatLng point = new LatLng(titik.getLatitude(), titik.getLongitude());
                        rute.add(point);
                    }else {
                        Log.d("titik", String.valueOf("null"));
                    }
                }

                if (rute.size()>0){
                //    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rute.get(0),19));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(rute.get(0),19));
                }
                pol.setPoints(rute);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.child(getIntent().getStringExtra("id")).addListenerForSingleValueEvent(getData);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
