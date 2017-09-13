package com.example.savr.mlayu.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.savr.mlayu.LariAdapter;
import com.example.savr.mlayu.Model.Lari;
import com.example.savr.mlayu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    DatabaseReference databaseReference;
    List<Lari> lariList;
    private RecyclerView recyclerView;
    private LariAdapter lariAdapter;
    private String id;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_history);

        lariList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            id = firebaseUser.getUid();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Lari");
        Query query=databaseReference.orderByChild("id_user");
        query=query.equalTo(id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot larisnapshot : dataSnapshot.getChildren()){
                    Lari lari = larisnapshot.getValue(Lari.class);
                    lariList.add(lari);
                }
                lariAdapter = new LariAdapter(lariList);
                recyclerView.setAdapter(lariAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
