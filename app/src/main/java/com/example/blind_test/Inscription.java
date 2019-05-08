package com.example.blind_test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.blind_test.model.PostAuth;
import com.example.blind_test.network.Api;
import com.example.blind_test.network.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inscription extends AppCompatActivity {

    private EditText editTextPseudo;
    private EditText editTextEmail;
    private EditText editTextPassword1;
    private EditText editTextPassword2;
    private TextView editTextError;

    private Button buttonVali;

    private Api mAPIService;

    private static final String TAG = "Inscription";

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

        mAPIService = ApiUtils.getAPIService();

        editTextError.setVisibility(View.INVISIBLE);

        buttonVali.setOnClickListener(myhandler1);
    }

    View.OnClickListener myhandler1 = new View.OnClickListener() {
        public void onClick(View v) {
            if(editTextPseudo.getText().toString().equals("")
                    || editTextPassword1.getText().toString().equals("")
                    ||editTextPassword2.getText().toString().equals("")
                    || editTextEmail.getText().toString().equals("")){
                editTextError.setText("Tous les champs ne sont pas renseignés");
                editTextError.setVisibility(View.VISIBLE);
            }
            else if(!editTextPassword1.getText().toString().equals( editTextPassword2.getText().toString())){
                editTextError.setText("Les mots de passes sont différents");
                editTextError.setVisibility(View.VISIBLE);
            }
            else{
                sendPost(editTextEmail.getText().toString(), editTextPassword1.getText().toString(), editTextPassword2.getText().toString(), editTextPseudo.getText().toString());
            }
        }
    };

    private void sendPost(String email, String password, String password_confirmation, String nickname) {
        mAPIService.inscriPost(email, password, password_confirmation, nickname).enqueue(new Callback<PostAuth>() {
            @Override
            public void onResponse(Call<PostAuth> call, Response<PostAuth> response) {

                if(response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    startActivity(new Intent(Inscription.this, MainActivity.class));
                }
                else{
                    editTextError.setText("Pseudo ou Email déjà utilisé");
                    editTextError.setVisibility(View.VISIBLE);
                }
       /*         else if(!editTextPseudo.getText().toString().equals(response.body().getNickname())){
                    editTextError.setText("Ce Pseudo est déjà utilisé");
                    editTextError.setVisibility(View.VISIBLE);
                }
                else if(!editTextEmail.getText().toString().equals(response.body().getEmail())){
                    editTextError.setText("Cette Email est déjà utilisé");
                    editTextError.setVisibility(View.VISIBLE);
                }*/


            }

            @Override
            public void onFailure(Call<PostAuth> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

}
