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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.blind_test.R;
import com.example.blind_test.model.Lobbies;
import com.example.blind_test.network.Api;
import com.example.blind_test.network.ApiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static android.support.constraint.Constraints.TAG;

public class Private extends Fragment {
    TextView textView5;
    EditText editTextPseudo;
    TextView textViewError;
    Button buttonCo;
    private Api mAPIService;
    private String s;
    private int lobbyId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_private,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewError = (TextView) view.findViewById(R.id.textViewError);
        editTextPseudo = (EditText) view.findViewById(R.id.editTextPseudo);
        buttonCo = (Button) view.findViewById(R.id.buttonCo);


        mAPIService = ApiUtils.getAPIService();
        Bundle b = getActivity().getIntent().getExtras();
        s = b.getString("token");

        textViewError.setVisibility(View.GONE);


        buttonCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextPseudo.getText().toString().equals("")){
                    textViewError.setText("Indiquez un code");
                    textViewError.setVisibility(View.VISIBLE);
                }
                else{
                    joinLobbies(s,editTextPseudo.getText().toString());
                }
            }
        });
    }

    private void joinLobbies(String token, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);

        mAPIService.getLobbiePrivate(map, code).enqueue(new Callback<Lobbies>() {
            @Override
            public void onResponse(Call<Lobbies> call, retrofit2.Response<Lobbies> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    Lobbies l = response.body();
                    lobbyId = Integer.parseInt(l.getId());
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    Game gameFragment = Game.newInstance(lobbyId);
                    fr.replace(R.id.activity_main_frame_layout, gameFragment);
                    fr.commit();
                }
                else{
                    textViewError.setText("Code invalide");
                    textViewError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Lobbies> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
}

