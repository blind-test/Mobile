package com.example.blind_test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.blind_test.R;
import com.example.blind_test.model.listUsers;
import com.example.blind_test.network.Api;
import com.example.blind_test.network.ApiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class ScoreDetail extends Fragment {


    TextView title;
    Button buttonSwap;
    ListView listViewScores;

    ArrayList<String> ar = new ArrayList<String>();
    private Api mAPIService;
    private String s;
    private String idGame;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scoresdetails,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        buttonSwap = (Button) view.findViewById(R.id.buttonSwap);
        listViewScores = (ListView) view.findViewById(R.id.listViewScores);


        mAPIService = ApiUtils.getAPIService();
        Bundle b = getActivity().getIntent().getExtras();
        s = b.getString("token");
        idGame = b.getString("id");

        title.setText("Détail score");
        getGeneral(s, idGame);

        buttonSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonSwap.getText().equals("Journée")){
                    buttonSwap.setText("Général");
                }
                else{
                    buttonSwap.setText("Journée");
                }
            }
        });
    }

    private void getGeneral(String token, String id) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.listScoreGeneral(map, id).enqueue(new Callback<List<listUsers>>() {
            @Override
            public void onResponse(Call<List<listUsers>> call, Response<List<listUsers>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    List<listUsers> liste = response.body();
                    if (!liste.isEmpty()) {
                        for (listUsers listUsers : liste) {
                            if (!listUsers.getNicknameFinal().isEmpty()) {
                                ar.add(listUsers.getNicknameFinal());
                            }
                        }
                        listViewScores.setAdapter(null);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ar);
                        listViewScores.setAdapter(arrayAdapter);
                    } else {
                    }

                }
            }

            @Override
            public void onFailure(Call<List<listUsers>> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
}
