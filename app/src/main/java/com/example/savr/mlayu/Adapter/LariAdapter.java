package com.example.savr.mlayu.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savr.mlayu.Activity.DetailActivity;
import com.example.savr.mlayu.Model.Lari;
import com.example.savr.mlayu.R;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by SAVR on 13/09/2017.
 */

public class LariAdapter extends RecyclerView.Adapter<LariAdapter.MyViewHolder>{

    private List<Lari> lariList;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView jarakTV,waktuTV,tanggalTV;
        public LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            jarakTV = (TextView) itemView.findViewById(R.id.listJarak);
            waktuTV = (TextView) itemView.findViewById(R.id.listWaktu);
            tanggalTV = (TextView) itemView.findViewById(R.id.listTanggal);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearlayout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            lariList.get(getAdapterPosition());
        }
    }


    public LariAdapter(List<Lari> lariList,Activity activity){
        this.lariList = lariList;
        this.activity = activity;
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_history, parent, false);

        return new MyViewHolder(itemView);
    }

    //menampilkan data
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Lari lari = lariList.get(position);
        String id_lari;
        id_lari = lari.getId();
        holder.jarakTV.setText(Double.parseDouble(new DecimalFormat("#.###").format(lari.getJarak())) + " km");

        long waktu = lari.getWaktu();
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(waktu),
                TimeUnit.MILLISECONDS.toMinutes(waktu) - TimeUnit.MILLISECONDS.toMinutes(TimeUnit.MILLISECONDS.toHours(waktu)),
                TimeUnit.MILLISECONDS.toSeconds(waktu) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(waktu)));

        holder.waktuTV.setText(hms);
        holder.tanggalTV.setText(lari.getTanggal());

        final String kalori = String.valueOf(Double.parseDouble(new DecimalFormat("#.###").format(lari.getKalori())));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ToDetail = new Intent(activity,DetailActivity.class);

                ToDetail.putExtra("id",lari.getId());
                ToDetail.putExtra("jarak", holder.jarakTV.getText().toString());
                ToDetail.putExtra("durasi",holder.waktuTV.getText().toString());
                ToDetail.putExtra("tanggal",holder.tanggalTV.getText().toString());
                ToDetail.putExtra("kalori",kalori.toString());

                v.getContext().startActivity(ToDetail);
            }
        });
    }

    public int getItemCount() {
        return lariList.size();
    }
}
