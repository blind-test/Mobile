package com.example.blind_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Inscription extends AppCompatActivity {

    private EditText editTextPseudo;
    private EditText editTextEmail;
    private EditText editTextPassword1;
    private EditText editTextPassword2;
    private TextView editTextError;

    private Button buttonVali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        buttonVali = (Button) findViewById(R.id.buttonVali);
        editTextError = (TextView) findViewById(R.id.textViewError);
        editTextPseudo = (EditText) findViewById(R.id.editTextPseudo);
        editTextPassword1 = (EditText) findViewById(R.id.editTextPassword1);
        editTextPassword2 = (EditText) findViewById(R.id.editTextPassword2);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        editTextError.setVisibility(View.INVISIBLE);

        buttonVali.setOnClickListener(myhandler1);
    }

    View.OnClickListener myhandler1 = new View.OnClickListener() {
        public void onClick(View v) {
            if(editTextPassword1.getText() != editTextPassword2.getText()) {
                editTextError.setText("Les mots de passe ne sont pas identiques");
                editTextError.setVisibility(View.VISIBLE);
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editTextPassword1.getText().toString()).matches()) {
                editTextError.setText("email incorrect");
                editTextError.setVisibility(View.VISIBLE);
            }
            if((editTextPseudo.getText().toString().equals("") || editTextPseudo.getText().toString().equals("Pseudo"))
                    || editTextPassword1.getText().toString().equals("") ||editTextPassword2.getText().toString().equals("")
                    || (editTextEmail.getText().toString().equals("")) || editTextEmail.getText().toString().equals("Email")){
                editTextError.setText("Tous les champs ne sont pas renseignés");
                editTextError.setVisibility(View.VISIBLE);
            }
        }
    };
}
