package com.example.savr.mlayu.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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


public class MeFragment extends Fragment{
    private TextView profile_nama,profile_email,Textprofile_umur,
            Textprofile_tinggi,Textprofile_berat;
    private CircleImageView poto_Profil;
    private RadioGroup radioGroupJeniskel;
    private RadioButton radioLK,radioPR;
    public String id,email,name,img_url,gender;
    public Button btnEdit;
    String berat_badan,tinggi_badan,umur;

    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        progressDialog = new ProgressDialog(getActivity());

        profile_nama = (TextView) view.findViewById(R.id.nama_ME);
        profile_email = (TextView) view.findViewById(R.id.email_ME);
        poto_Profil = (CircleImageView) view.findViewById(R.id.poto_profile_ME);
        Textprofile_berat = (TextView) view.findViewById(R.id.berat_ME);
        Textprofile_tinggi = (TextView) view.findViewById(R.id.tinggi_ME);
        Textprofile_umur = (TextView) view.findViewById(R.id.umur_ME);

        radioGroupJeniskel = (RadioGroup) view.findViewById(R.id.radioJK_ME);
        radioLK = (RadioButton) view.findViewById(R.id.RadiomaleME);
        radioPR = (RadioButton) view.findViewById(R.id.RadiofemaleME);

        btnEdit = (Button) view.findViewById(R.id.Btn_Edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_nama.getText().toString();
                profile_email.getText().toString();
                Integer berat = Integer.parseInt(Textprofile_berat.getText().toString());
                Integer tinggi = Integer.parseInt(Textprofile_tinggi.getText().toString());
                Integer umur = Integer.parseInt(Textprofile_umur.getText().toString());

                String gender = "Laki-laki";
                if (radioGroupJeniskel.getCheckedRadioButtonId()==R.id.RadiomaleME){
                    gender = "Laki-laki";
                }else if (radioGroupJeniskel.getCheckedRadioButtonId()==R.id.RadiofemaleME){
                    gender = "Perempuan";
                }else {
                    Toast.makeText(getActivity(), "Pilih Jenis kelamin", Toast.LENGTH_SHORT).show();
                }
                UserProfile userProfile = new UserProfile(id,name,email,gender,umur,berat,tinggi);
                editUserProfile(userProfile);
            }
        });

//======================Retrieve data===============================
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser!=null){
            id = firebaseUser.getUid();
            name = firebaseUser.getDisplayName();
            email = firebaseUser.getEmail();
            img_url = firebaseUser.getPhotoUrl().toString();

            profile_nama.setText(name);
            profile_email.setText(email);
            Glide.with(this).load(img_url).into(poto_Profil);

            databaseReference = FirebaseDatabase.getInstance().getReference("user")
                    .child(firebaseUser.getUid());

            ValueEventListener getdata = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    berat_badan = userProfile.getBerat().toString();
                    tinggi_badan = userProfile.getTinggi().toString();
                    umur = userProfile.getUmur().toString();
                    gender = userProfile.getGender().toString();

                    Textprofile_umur.setText(umur);
                    Textprofile_berat.setText(berat_badan);
                    Textprofile_tinggi.setText(tinggi_badan);

                    if (gender.equals("Laki-laki")){
                        radioLK.setChecked(true);
                    }else if(gender.equals("Perempuan")){
                        radioPR.setChecked(true);
                    }else {
                        radioLK.setChecked(true);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference.addValueEventListener(getdata);

        }

        //==========================Show Dialog============================
        //Umur dialog
        Textprofile_umur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View ViewUmur = inflater.inflate(R.layout.dialog_umur,null);
                final EditText umur = (EditText) ViewUmur.findViewById(R.id.dialog_umurTV);

                builder.setMessage("Set Umur")
                        .setView(ViewUmur)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String T = umur.getText().toString();
                                if (!T.isEmpty()){
                                    Textprofile_umur.setText(T);
                                }else{
                                    Toast.makeText(getActivity(),"Silahkan diisi", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //Berat dialog
        Textprofile_berat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View ViewBerat = inflater.inflate(R.layout.dialog_berat,null);
                final EditText berat = (EditText) ViewBerat.findViewById(R.id.dialog_beratTV);

                builder.setMessage("Set Berat")
                        .setView(ViewBerat)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String b = berat.getText().toString();
                                if (!b.isEmpty()){
                                    Textprofile_berat.setText(b);
                                }else {
                                    Toast.makeText(getActivity(),"Silahkan diisi", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .setCancelable(false);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //Tinggi dialog
        Textprofile_tinggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View viewTinggi = inflater.inflate(R.layout.dialog_tinggi,null);

                final TextView tv = (TextView) viewTinggi.findViewById(R.id.tv);
                tv.setVisibility(View.GONE);
                NumberPicker numberPicker = (NumberPicker) viewTinggi.findViewById(R.id.numberpickertinggi);

                numberPicker.setMinValue(100);
                numberPicker.setMaxValue(250);
                numberPicker.setWrapSelectorWheel(true);

                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        String t =String.valueOf(newVal).toString();
                        Log.d("Tinggi badan: ", newVal+" cm");
                        tv.setText(t);
                    }
                });

                builder.setMessage("Set Tinggi")
                        .setView(viewTinggi)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Textprofile_tinggi.setText(tv.getText());
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }

    private void editUserProfile(UserProfile userProfile) {
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userProfile.getId());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        databaseReference.setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getActivity(),"Waoouuwww Nice",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    Toast.makeText(getActivity(),"Ga isoh om",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}