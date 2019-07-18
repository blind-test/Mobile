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
import android.widget.TextView;

import com.example.blind_test.R;
import com.example.blind_test.model.listUsers;
import com.example.blind_test.network.Api;
import com.example.blind_test.network.ApiUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class User extends Fragment {

    TextView title;
    TextView TextViewPseudo;
    TextView TextViewMail;
    TextView textViewError;
    EditText EditTextPseudo;
    EditText EditTextMail;
    Button buttonUpdate;

    private Api mAPIService;
    private String s;
    private String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        TextViewMail = (TextView) view.findViewById(R.id.TextViewMail);
        TextViewPseudo = (TextView) view.findViewById(R.id.TextViewPseudo);
        textViewError = (TextView) view.findViewById(R.id.textViewError);
        EditTextPseudo = (EditText) view.findViewById(R.id.EditTextPseudo);
        EditTextMail = (EditText) view.findViewById(R.id.EditTextMail);
        buttonUpdate = (Button) view.findViewById(R.id.buttonUpdate);

        textViewError.setVisibility(View.GONE);
        Bundle b = getActivity().getIntent().getExtras();
        s = b.getString("token");
        id = b.getString("current_user_id");

        System.out.println("-----------------\n");
        System.out.println(id + "\n");
        System.out.println("-----------------\n");
        mAPIService = ApiUtils.getAPIService();

        title.setText("gestion de compte");

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditTextPseudo.getText().toString().equals("")
                || !EditTextMail.getText().toString().equals("")) {
                    sendRequest(id,EditTextPseudo.getText().toString(),EditTextMail.getText().toString() , s);
                }
                else{
                    textViewError.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void sendRequest(String id, String pseudo, String mail, String token) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.updateUser(map, id , pseudo, mail).enqueue(new Callback<listUsers>() {
            @Override
            public void onResponse(Call<listUsers> call, Response<listUsers> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.commit();
                    fr.replace(R.id.activity_main_frame_layout, new Gamelist());
                }
            }

            @Override
            public void onFailure(Call<listUsers> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
}
