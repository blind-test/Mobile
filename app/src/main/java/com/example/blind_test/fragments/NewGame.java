package com.example.blind_test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.blind_test.R;

public class NewGame extends Fragment {

    TextView title;
    CheckBox checkboxNewgame;
    Spinner spinnerNewgame;
    EditText editTextTime;
    EditText editTextQuestion;
    Button buttonNewGame;
    TextView textViewError;

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



        textViewError.setVisibility(View.GONE);
        title.setText("Nouvelle Partie");
    }
}
