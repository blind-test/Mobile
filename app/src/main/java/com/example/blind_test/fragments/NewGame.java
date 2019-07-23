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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blind_test.R;
import com.example.blind_test.model.Lobbies;
import com.example.blind_test.model.Socket;
import com.example.blind_test.model.Theme;
import com.example.blind_test.model.listUsers;
import com.example.blind_test.network.Api;
import com.example.blind_test.network.ApiUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static android.support.constraint.Constraints.TAG;

public class NewGame extends Fragment {

    TextView title;
    CheckBox checkboxNewgame;
    Spinner spinnerNewgame;
    EditText editTextTime;
    EditText editTextQuestion;
    Button buttonNewGame;
    TextView textViewError;

    ArrayList<String> arr = new ArrayList<String>();
    ArrayList<String> arr2 = new ArrayList<String>();
    private Api mAPIService;
    private String s;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newgame,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        textViewError = (TextView) view.findViewById(R.id.textViewError);
        spinnerNewgame = (Spinner) view.findViewById(R.id.spinnerNewgame);
        editTextTime = (EditText) view.findViewById(R.id.editTextTime);
        editTextQuestion = (EditText) view.findViewById(R.id.editTextQuestion);
        buttonNewGame = (Button) view.findViewById(R.id.buttonNewGame);
        checkboxNewgame = (CheckBox) view.findViewById(R.id.checkboxNewgame);

        Bundle b = getActivity().getIntent().getExtras();
        s = b.getString("token");
        mAPIService = ApiUtils.getAPIService();

        textViewError.setVisibility(View.GONE);
        title.setText("Nouvelle Partie");

        getTheme(s);

        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextTime.getText().toString().equals("")){
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("La durée des manches ne peut pas être vide");
                }
                else if((Integer.parseInt(editTextTime.getText().toString()) > 35)
            || (Integer.parseInt(editTextTime.getText().toString()) < 25) ) {
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("durée des manches incorrect");

                }
                else if(editTextQuestion.getText().toString().equals("")){
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("Le nombre de questions ne peut pas être vide");
                }
                else if(Integer.parseInt(editTextQuestion.getText().toString()) == 0){
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("Le nombre de question doit être différent de 0");
                }
                else {
                    int pos = spinnerNewgame.getSelectedItemPosition();
                    String check = "";
                    if(checkboxNewgame.isChecked())
                        check = "false";
                    else
                        check = "true";
                    createLobbie( s, arr2.get(pos), editTextQuestion.getText().toString(), check);
                }
            }

        });
    }

    private void getTheme(String token) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.GetThemes(map).enqueue(new Callback<List<Theme>>() {
            @Override
            public void onResponse(Call<List<Theme>> call, retrofit2.Response<List<Theme>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    List<Theme> liste = response.body();
                    for (Theme theme : liste) {
                        arr.add(theme.getTitle());
                        arr2.add(theme.getId());
                    }
                    spinnerNewgame.setAdapter(null);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , arr);
                    spinnerNewgame.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Theme>> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    private void createLobbie(String token, String theme_id, String questions, String restricted) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);

        mAPIService.CreateLobbie(map, theme_id, questions, restricted).enqueue(new Callback<Lobbies>() {
            @Override
            public void onResponse(Call<Lobbies> call, retrofit2.Response<Lobbies> response) {
                if (response.isSuccessful()) {
                    Lobbies l = response.body();
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    if(l.getRestricted().equals("true")){
                        buttonNewGame.setVisibility(View.GONE);
                        textViewError.setVisibility(View.VISIBLE);
                        textViewError.setText("Votre clé est : " + l.getPrivate_key());

                    }
                    else {
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.activity_main_frame_layout, new Gamelist());
                        fr.commit();
                    }
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println("-----\n");
                        System.out.println(jObjError.toString()+"\n");
                        System.out.println("-----\n");
                        int pos = jObjError.toString().indexOf("questions\"");
                        pos +=12;
                        String verif = "";
                        String message ="";
                        for(int i = pos ; i < jObjError.toString().length() ; i++){
                            verif = String.valueOf(jObjError.toString().charAt(i));
                            if(verif.equals("\"")) {
                                break;
                            }
                            message = message + jObjError.toString().charAt(i);
                        }
                        final String question = message;
                        String rep = response.errorBody().toString();
                        textViewError.setVisibility(View.VISIBLE);
                        textViewError.setText(message);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Lobbies> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
}

