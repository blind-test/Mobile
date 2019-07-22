package com.example.blind_test.fragments;

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

public class Friends extends Fragment {

    TextView title;
    TextView titleAttente;
    TextView titleFriends;
    TextView sendBy;
    Button buttonAddFriends;
    Button buttonAcceptFriend;
    Button buttonRefuseFriend;
    ListView listViewAttente;
    ListView listViewFriends;

    private Api mAPIService;
    private String idUser;
    private int idAdd;
    ArrayList<String> ar = new ArrayList<String>();
    ArrayList<String> ar2 = new ArrayList<String>();
    ArrayList<Integer> arId = new ArrayList<Integer>();
    ArrayList<Integer> verif = new ArrayList<Integer>();
    private String s;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        sendBy = (TextView) view.findViewById(R.id.sendBy);
        titleAttente = (TextView) view.findViewById(R.id.titleAttente);
        titleFriends = (TextView) view.findViewById(R.id.titleFriends);
        buttonAddFriends = (Button) view.findViewById(R.id.buttonAddFriends);
        buttonAcceptFriend = (Button) view.findViewById(R.id.buttonAcceptFriend);
        buttonRefuseFriend = (Button) view.findViewById(R.id.buttonRefuseFriend);
        listViewAttente = (ListView) view.findViewById(R.id.listViewAttente);
        listViewFriends = (ListView) view.findViewById(R.id.listViewFriends);

        Bundle b = getActivity().getIntent().getExtras();
        s = b.getString("token");
        idUser = b.getString("current_user_id");

        mAPIService = ApiUtils.getAPIService();
        sendBy.setVisibility(View.GONE);

        titleAttente.setText("demande d'ami en attente");
        titleFriends.setText("liste d'ami");
        title.setText("amis");

        buttonAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.activity_main_frame_layout, new AddFriends());
                fr.commit();
            }
        });


        titleAttente.setVisibility(View.GONE);
        listViewAttente.setVisibility(View.GONE);
        buttonAcceptFriend.setVisibility(View.GONE);
        buttonRefuseFriend.setVisibility(View.GONE);

        listViewAttente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("---------\n");
                System.out.println(idUser+"       "+position+"      "+verif.get(position)+"\n");
                System.out.println("---------\n");
                if(verif.get(position) != Integer.parseInt(idUser)){
                    sendBy.setVisibility(View.VISIBLE);
                    buttonAcceptFriend.setVisibility(View.GONE);
                    buttonRefuseFriend.setVisibility(View.GONE);
                    idAdd = (arId.get(position));
                }
                else{
                    sendBy.setVisibility(View.GONE);
                    buttonAcceptFriend.setVisibility(View.VISIBLE);
                    buttonRefuseFriend.setVisibility(View.VISIBLE);
                    idAdd = (arId.get(position));
                }

            }
        });

        buttonAcceptFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response("accepted", s);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.activity_main_frame_layout, new Friends());
                fr.commit();
            }
        });

        buttonRefuseFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response("rejected", s);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.activity_main_frame_layout, new Friends());
                fr.commit();
            }
        });

        getPending(s,"pending");
        getAccepted(s,"accepted");
    }

    private void response(String resp, final String token) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        String a = String.valueOf(idAdd);
        mAPIService.responsFriend(map, a, resp).enqueue(new Callback<listUsers>() {
            @Override
            public void onResponse(Call<listUsers> call, Response<listUsers> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<listUsers> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    private void getPending(final String token, String status) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.listUserStatus(map, status).enqueue(new Callback<List<listUsers>>() {
            @Override
            public void onResponse(Call<List<listUsers>> call, Response<List<listUsers>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    List <listUsers> liste = response.body();
                    if(!liste.isEmpty()) {
                        titleAttente.setVisibility(View.VISIBLE);
                        listViewAttente.setVisibility(View.VISIBLE);
                        for (listUsers listUsers : liste) {
                            if(!listUsers.getNicknameFinal().isEmpty()) {
                                ar.add(listUsers.getNicknameFinal());
                                arId.add(listUsers.getFriendship_id());
                                verif.add(listUsers.getAsked_by());
                            }
                        }
                        listViewAttente.setAdapter(null);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , ar);
                        listViewAttente.setAdapter(arrayAdapter);

                    }
                    else{
                        listViewAttente.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<listUsers>> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    private void getAccepted(String token, String status) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.listUserStatus(map, status).enqueue(new Callback<List<listUsers>>() {
            @Override
            public void onResponse(Call<List<listUsers>> call, Response<List<listUsers>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    List <listUsers> liste = response.body();
                    if(!liste.isEmpty()) {
                        for (listUsers listUsers : liste) {
                            if(!listUsers.getNicknameFinal().isEmpty()) {
                                ar2.add(listUsers.getNicknameFinal());
                            }
                        }
                        listViewFriends.setAdapter(null);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , ar2);
                        listViewFriends.setAdapter(arrayAdapter);
                    }
                    else{
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