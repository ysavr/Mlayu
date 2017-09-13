package com.example.savr.mlayu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.savr.mlayu.Model.Lari;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by SAVR on 13/09/2017.
 */

public class LariAdapter extends RecyclerView.Adapter<LariAdapter.MyViewHolder>{

    private List<Lari> lariList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView jarakTV,waktuTV,tanggalTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            jarakTV = (TextView) itemView.findViewById(R.id.listJarak);
            waktuTV = (TextView) itemView.findViewById(R.id.listWaktu);
            tanggalTV = (TextView) itemView.findViewById(R.id.listTanggal);
        }
    }

    public LariAdapter(List<Lari> lariList){
        this.lariList = lariList;
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_history, parent, false);

        return new MyViewHolder(itemView);
    }


    public void onBindViewHolder(MyViewHolder holder, int position) {
        Lari lari = lariList.get(position);

        holder.jarakTV.setText(Double.parseDouble(new DecimalFormat("#.###").format(lari.getJarak())) + " km");

        long waktu = lari.getWaktu();
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(waktu),
                TimeUnit.MILLISECONDS.toMinutes(waktu) - TimeUnit.MILLISECONDS.toMinutes(TimeUnit.MILLISECONDS.toHours(waktu)),
                TimeUnit.MILLISECONDS.toSeconds(waktu) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(waktu)));


        holder.waktuTV.setText(hms);
        //holder.waktuTV.setText(String.valueOf(lari.getWaktu()));
        holder.tanggalTV.setText(lari.getTanggal());

    }


    public int getItemCount() {
        return lariList.size();
    }
}
