package com.example.savr.mlayu;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.savr.mlayu.Model.Lari;
import com.example.savr.mlayu.Model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by SAVR on 17/11/2017.
 */

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context,"Alarm Success Set!!!!!",Toast.LENGTH_LONG).show();
        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(5000);
        getCalorieData();

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_direction_run_blue);
        builder.setContentTitle("RUNS");
        builder.setContentText("You must run to burn your calories");
        builder.setColor(Color.parseColor("#98002E"));
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_LIGHTS);
        Intent tosplash= new Intent(context,Splashscreen.class);
        TaskStackBuilder stackBuilder= TaskStackBuilder.create(context);
        stackBuilder.addParentStack(Splashscreen.class);
        stackBuilder.addNextIntent(tosplash);
        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager NM=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0,builder.build());
    }

    private DatabaseReference databaseReference;
    private String id;
    double totalkalori = 0;
    String timeStamp;

    private void getCalorieData() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Calendar calendar = Calendar.getInstance();
        timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
        String tanggal = timeStamp;

        if (firebaseUser!=null){
            id = firebaseUser.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference("Lari");
            Query query=databaseReference.orderByChild("id_user");
            query=query.equalTo(id);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot larisnapshot : dataSnapshot.getChildren()){
                        Lari lari = larisnapshot.getValue(Lari.class);
                        Log.d("id lari: ", lari.getId()+"");
                        Log.d("Kalori terbakar: ", lari.getKalori()+"");
                        totalkalori = totalkalori+lari.getKalori();
                        Log.d("Total kalori: ",totalkalori+"");
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
