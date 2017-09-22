package com.example.savr.mlayu.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.savr.mlayu.Model.UserProfile;
import com.example.savr.mlayu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class MeFragment extends Fragment{
    private TextView profile_nama,profile_email, Textprofile_umur,Textprofile_tinggi,Textprofile_berat;
    private CircleImageView poto_Profil;
    public String id,email,name,img_url;
    public Button btnEdit;
    String berat_badan,tinggi_badan,umur;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        profile_nama = (TextView) view.findViewById(R.id.nama_ME);
        profile_email = (TextView) view.findViewById(R.id.email_ME);
        poto_Profil = (CircleImageView) view.findViewById(R.id.poto_profile_ME);
        Textprofile_berat = (TextView) view.findViewById(R.id.berat_ME);
        Textprofile_tinggi = (TextView) view.findViewById(R.id.tinggi_ME);
        Textprofile_umur = (TextView) view.findViewById(R.id.umur_ME);

        btnEdit = (Button) view.findViewById(R.id.Btn_Edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"wawww",Toast.LENGTH_SHORT).show();
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

                    Textprofile_umur.setText(umur);
                    Textprofile_berat.setText(berat_badan);
                    Textprofile_tinggi.setText(tinggi_badan);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference.addValueEventListener(getdata);

        }
        //==========================Show Dialog============================
        //berat dialog
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
                                    Toast.makeText(getActivity(),"Silahkan diisi",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .setCancelable(false);

                AlertDialog dialog =builder.create();
                dialog.show();
            }
        });

        Textprofile_tinggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View viewTinggi = inflater.inflate(R.layout.dialog_tinggi,null);

                final TextView tv = (TextView) viewTinggi.findViewById(R.id.tv);
                tv.setVisibility(View.GONE);
                NumberPicker numberPicker = (NumberPicker) viewTinggi.findViewById(R.id.numberpickertinggi);

                numberPicker.setMinValue(100);
                numberPicker.setMaxValue(300);
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

}