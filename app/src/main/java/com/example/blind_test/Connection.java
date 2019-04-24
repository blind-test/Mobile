package com.example.blind_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Connection extends AppCompatActivity {

    private EditText editTextPseudo;
    private EditText editTextPassword;
    private TextView editTextError;

    private Button buttonCo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        buttonCo = (Button) findViewById(R.id.buttonCo);
        editTextError = (TextView) findViewById(R.id.textViewError);
        editTextPseudo = (EditText) findViewById(R.id.editTextPseudo);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        editTextError.setVisibility(View.INVISIBLE);

        buttonCo.setOnClickListener(myhandler1);
    }

    View.OnClickListener myhandler1 = new View.OnClickListener() {
        public void onClick(View v) {
            if((editTextPseudo.getText().toString().equals("") || editTextPseudo.getText().toString().equals("Pseudo"))
                    || editTextPassword.getText().toString().equals("")){
                editTextError.setText("Tous les champs ne sont pas renseignés");
                editTextError.setVisibility(View.VISIBLE);
            }
        }
    };
}
