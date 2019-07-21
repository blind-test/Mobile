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
import com.example.blind_test.model.Score;
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

    ArrayList<String> arrMessage = new ArrayList<String>();
    private Api mAPIService;
    private String s;
    private String idGame;
    private String idUser;

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
        idUser = b.getString("current_user_id");

        title.setText("Détail score");
        getGeneral(s, idGame);

        buttonSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonSwap.getText().equals("Journée")){
                    buttonSwap.setText("Général");
                    getGeneralD(s, idGame);
                }
                else{
                    buttonSwap.setText("Journée");
                    getGeneral(s, idGame);
                }
            }
        });
    }

    private void getGeneral(String token, String id) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.listScoreGeneral(map, id).enqueue(new Callback<List<Score>>() {
            @Override
            public void onResponse(Call<List<Score>> call, Response<List<Score>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    List<Score> liste = response.body();
                    if (!liste.isEmpty()) {
                        arrMessage.clear();
                        int i = 0;
                        for (Score score : liste) {
                            if(i == 0){
                                arrMessage.add("1er - "+score.getNickname()+" - "+score.getScore()+"points");
                            }
                            if(i!=0 && i < 5){
                                arrMessage.add((i+1)+"eme - "+score.getNickname()+" - "+score.getScore()+"points");
                            }
                            if(score.getId().equals(idUser) && i >= 5){
                                arrMessage.add((i+1)+"eme - "+score.getNickname()+" - "+score.getScore()+"points");
                            }
                            i++;
                        }
                        listViewScores.setAdapter(null);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrMessage);
                        listViewScores.setAdapter(arrayAdapter);
                    } else {
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Score>> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }


    private void getGeneralD(String token, String id) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.listScoreGeneralDay(map, id).enqueue(new Callback<List<Score>>() {
            @Override
            public void onResponse(Call<List<Score>> call, Response<List<Score>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    List<Score> liste = response.body();
                    if (!liste.isEmpty()) {
                        arrMessage.clear();
                        int i = 0;
                        for (Score score : liste) {
                            if(i == 0){
                                arrMessage.add("1er - "+score.getNickname()+" - "+score.getScore()+"points");
                            }
                            if(i!=0 && i < 5){
                                arrMessage.add((i+1)+"eme - "+score.getNickname()+" - "+score.getScore()+"points");
                            }
                            if(score.getId().equals(idUser)){
                                arrMessage.add((i+1)+"eme - "+score.getNickname()+" - "+score.getScore()+"points");
                            }
                            i++;
                        }
                        listViewScores.setAdapter(null);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrMessage);
                        listViewScores.setAdapter(arrayAdapter);
                    } else {
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Score>> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
}
