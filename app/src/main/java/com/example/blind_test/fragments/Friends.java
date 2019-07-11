package com.example.blind_test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.blind_test.R;

public class Friends extends Fragment {

    TextView title;
    TextView titleAttente;
    TextView titleFriends;
    Button buttonAddFriends;
    Button buttonAcceptFriend;
    Button buttonRefuseFriend;
    ListView listViewAttente;
    ListView listViewFriends;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        titleAttente = (TextView) view.findViewById(R.id.titleAttente);
        titleFriends = (TextView) view.findViewById(R.id.titleFriends);
        buttonAddFriends = (Button) view.findViewById(R.id.buttonAddFriends);
        buttonAcceptFriend = (Button) view.findViewById(R.id.buttonAcceptFriend);
        buttonRefuseFriend = (Button) view.findViewById(R.id.buttonRefuseFriend);
        listViewAttente = (ListView) view.findViewById(R.id.listViewAttente);
        listViewFriends = (ListView) view.findViewById(R.id.listViewFriends);

        titleAttente.setText("demande d'ami en attente");
        titleFriends.setText("liset d'ami");
        title.setText("amis");

        buttonAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.activity_main_frame_layout, new AddFriends());
                fr.commit();
            }
        });

        String[] listTest = new String[]{
                "test1",
                "test2",
                "test2",
                "test2",
                "test2",
                "test2",
                "test2",
                "test2",
                "test2",
                "test2",
                "bonjour"
        };

        String[] listTest2 = new String[]{
                "test1",
                "test2",
                "Aurevoir"
        };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , listTest);
        listViewFriends.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , listTest2);
        listViewAttente.setAdapter(arrayAdapter2);

        titleAttente.setVisibility(View.GONE);
        listViewAttente.setVisibility(View.GONE);
        buttonAcceptFriend.setVisibility(View.GONE);
        buttonRefuseFriend.setVisibility(View.GONE);

        listViewAttente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buttonAcceptFriend.setVisibility(View.VISIBLE);
                buttonRefuseFriend.setVisibility(View.VISIBLE);
                System.out.println("---------------------------\n");
                System.out.println("cliqué sur position : " + position + " et id : " + id + "\n");
                System.out.println("---------------------------\n");
            }
        });
    }
}