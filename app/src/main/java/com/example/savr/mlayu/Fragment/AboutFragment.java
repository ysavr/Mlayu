package com.example.savr.mlayu.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.savr.mlayu.R;


public class AboutFragment extends Fragment {
    TextView tentang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        tentang = (TextView) v.findViewById(R.id.textabout);
        tentang.setText("Aplikasi ini dibuat untuk memotivasi orang - orang agar " +
                "memiliki kebiasaan hidup sehat. Aplikasi ini di desain untuk mmebantu tracking lari" +
                "anda. Aplikasi ini dapat memberikan rute yang anda lewati saat berlari dan" +
                "memberikan info jarak dan waktu lari yang anda tempuh, jumlah kalori terbakar.");
        return v;



    }

}
