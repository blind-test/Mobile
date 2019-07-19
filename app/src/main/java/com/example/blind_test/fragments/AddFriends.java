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
import android.widget.EditText;
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

public class AddFriends extends Fragment {

    TextView title;
    TextView textViewError;
    EditText editTextFiltre;
    Button buttonFilter;
    Button buttonAddFriends;
    ListView listViewFriend;

    private Api mAPIService;

    private String s;
    private int idAdd;
    ArrayList<String> ar = new ArrayList<String>();
    ArrayList<Integer> arId = new ArrayList<Integer>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addfriends,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        textViewError = (TextView) view.findViewById(R.id.textViewError);
        editTextFiltre = (EditText) view.findViewById(R.id.editTextFiltre);
        buttonFilter = (Button) view.findViewById(R.id.buttonFilter);
        buttonAddFriends = (Button) view.findViewById(R.id.buttonAddFriends);
        listViewFriend = (ListView) view.findViewById(R.id.listViewFriend);
        textViewError.setVisibility(View.GONE);

        Bundle b = getActivity().getIntent().getExtras();
        s = b.getString("token");

        mAPIService = ApiUtils.getAPIService();

        listViewFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buttonAddFriends.setVisibility(View.VISIBLE);
                System.out.println(arId.get(position));
                idAdd = (arId.get(position));
            }
        });

        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                haveListUser(editTextFiltre.getText().toString(), s);
            }
        });

        buttonAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(idAdd, s);
            }
        });

        title.setText("Ajout d'amis");
        buttonAddFriends.setVisibility(View.GONE);
    }

    private void haveListUser(String name, String token) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.listUserGET(map,name).enqueue(new Callback<List<listUsers>>() {
            @Override
            public void onResponse(Call<List<listUsers>> call, Response<List<listUsers>> response) {
                if(response.isSuccessful()) {
                    textViewError.setVisibility(View.GONE);
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    List <listUsers> liste = response.body();
                    if(!liste.isEmpty()) {
                        for (listUsers listUsers : liste) {
                            ar.add(listUsers.getNicknameFinal());
                            arId.add(listUsers.getId());
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , ar);
                        listViewFriend.setAdapter(arrayAdapter);
                    }
                    else{
                        textViewError.setVisibility(View.VISIBLE);
                    }
                    response.raw().body().close();
                }
            }

            @Override
            public void onFailure(Call<List<listUsers>> call, Throwable t) {
                Log.e(TAG, "Unable to submit get to API.");
            }
        });
    }

    private void sendRequest(int id, String token) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.askFriend(map,id).enqueue(new Callback<listUsers>() {
            @Override
            public void onResponse(Call<listUsers> call, Response<listUsers> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.commit();
                    fr.replace(R.id.activity_main_frame_layout, new Friends());
                }
            }

            @Override
            public void onFailure(Call<listUsers> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

}
