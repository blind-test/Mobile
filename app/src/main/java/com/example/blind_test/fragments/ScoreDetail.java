package com.example.blind_test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.blind_test.R;

public class ScoreDetail extends Fragment {


    TextView title;
    Button buttonSwap;
    ListView listViewScores;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scores,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        buttonSwap = (Button) view.findViewById(R.id.buttonSwap);
        listViewScores = (ListView) view.findViewById(R.id.listViewScores);

        title.setText("Détail score");

        buttonSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonSwap.getText().equals("journée")){
                    buttonSwap.setText("général");
                }
                else{
                    buttonSwap.setText("journée");
                }
            }
        });
    }
}
