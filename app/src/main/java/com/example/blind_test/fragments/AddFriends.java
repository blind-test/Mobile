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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.blind_test.R;

public class AddFriends extends Fragment {

    TextView title;
    EditText editTextFiltre;
    Button buttonFilter;
    Button buttonAddFriends;
    ListView listViewFriend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addfriends,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        editTextFiltre = (EditText) view.findViewById(R.id.editTextFiltre);
        buttonFilter = (Button) view.findViewById(R.id.buttonFilter);
        buttonAddFriends = (Button) view.findViewById(R.id.buttonAddFriends);
        listViewFriend = (ListView) view.findViewById(R.id.listViewFriend);

        String[] listTest = new String[]{
                "test1",
                "test2",
                "bonjour"
        };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , listTest);
        listViewFriend.setAdapter(arrayAdapter);

        listViewFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buttonAddFriends.setVisibility(View.VISIBLE);
                System.out.println("---------------------------\n");
                System.out.println("cliqué sur position : " + position + " et id : " + id + "\n");
                System.out.println("---------------------------\n");
            }
        });

        buttonAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.activity_main_frame_layout, new Friends());
                fr.commit();
            }
        });

        title.setText("Ajout d'amis");
        buttonAddFriends.setVisibility(View.GONE);
    }

}
