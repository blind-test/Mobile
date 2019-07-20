package com.example.blind_test.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.blind_test.FirstMenu;
import com.example.blind_test.MainActivity;
import com.example.blind_test.R;
import com.example.blind_test.model.Lobbies;
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

public class Scores extends Fragment {

    TextView title;
    Button buttonDetail;
    ListView listViewScores;

    Bundle b ;
    ArrayList<String> ar = new ArrayList<String>();
    ArrayList<Integer> ar2 = new ArrayList<Integer>();
    private String s;
    private Api mAPIService;
    private String idGame;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scores,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        buttonDetail = (Button) view.findViewById(R.id.buttonDetail);
        listViewScores = (ListView) view.findViewById(R.id.listViewScores);
        b = getActivity().getIntent().getExtras();
        buttonDetail.setVisibility(View.GONE);
        s = b.getString("token");

        mAPIService = ApiUtils.getAPIService();
        title.setText("Scores");

        listViewScores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buttonDetail.setVisibility(View.VISIBLE);
                buttonDetail.setVisibility(View.VISIBLE);
                System.out.println(ar2.get(position));
                idGame = (ar2.get(position).toString());

            }
        });

        buttonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getIntent().putExtra("id",idGame);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.commit();
                fr.replace(R.id.activity_main_frame_layout, new ScoreDetail());
            }
        });

        listLobbies(s);
    }

    private void listLobbies(final String token) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.listLobbies(map).enqueue(new Callback<List<Lobbies>>() {
            @Override
            public void onResponse(Call<List<Lobbies>> call, Response<List<Lobbies>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    List <Lobbies> liste = response.body();
                    if(!liste.isEmpty()) {
                        listViewScores.setVisibility(View.VISIBLE);
                        listViewScores.setVisibility(View.VISIBLE);
                        for (Lobbies listLobbies : liste) {
                            ar.add(listLobbies.getTheme_title());
                            ar2.add(Integer.parseInt(listLobbies.getId()));
                        }
                        listViewScores.setAdapter(null);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , ar);
                        listViewScores.setAdapter(arrayAdapter);

                    }
                    else{
                        listViewScores.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Lobbies>> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
}
