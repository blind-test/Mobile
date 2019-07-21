package com.example.blind_test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.blind_test.R;
import com.example.blind_test.adapter.GameAdapter;
import com.example.blind_test.model.Lobbies;
import com.example.blind_test.network.Api;
import com.example.blind_test.network.ApiUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class Gamelist extends Fragment {

    TextView title;
    private Api mAPIService;
    private ListView listViewLobbies;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gamelist,container,false);
        Button button2 = (Button) view.findViewById(R.id.buttonCreateGame);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.activity_main_frame_layout, new NewGame());
                fr.commit();
            }
        });
        listViewLobbies = (ListView) view.findViewById(R.id.lobbyList);
        mAPIService = ApiUtils.getAPIService();
        Bundle b = getActivity().getIntent().getExtras();
        listLobbies(b.getString("token"));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void listLobbies(final String token) {
        Log.i(TAG, "token " + token);
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.listLobbies(map).enqueue(new Callback<List<Lobbies>>() {
            @Override
            public void onResponse(Call<List<Lobbies>> call, Response<List<Lobbies>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "GET lobbies " + response.body().toString());

                    List<Lobbies> lobbies = response.body();
                    if(!lobbies.isEmpty()) {
                        listViewLobbies.setVisibility(View.VISIBLE);
                        listViewLobbies.setAdapter(null);
                        GameAdapter adapter = new GameAdapter(getActivity(), lobbies);
                        listViewLobbies.setAdapter(adapter);

                    }
                    else{
                        listViewLobbies.setVisibility(View.GONE);
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
