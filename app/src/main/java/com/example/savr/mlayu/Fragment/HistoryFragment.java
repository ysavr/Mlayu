package com.example.savr.mlayu.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.savr.mlayu.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        ArrayList<String> data = new ArrayList<>();
        data.add("mlayu1");
        data.add("mlayu2");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),R.layout.list_history,R.id.listJarak,data
        );

        ListView listView = (ListView) view.findViewById(R.id.listView_history);
        listView.setAdapter(adapter);
        return view;
    }
}
