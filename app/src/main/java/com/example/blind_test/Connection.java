package com.example.blind_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.blind_test.model.PostCo;
import com.example.blind_test.network.Api;
import com.example.blind_test.network.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Connection extends AppCompatActivity {

    private EditText editTextPseudo;
    private EditText editTextPassword;
    private TextView editTextError;

    private Button buttonCo;

    private Api mAPIService;

    private static final String TAG = "Connection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        buttonCo = (Button) findViewById(R.id.buttonCo);
        editTextError = (TextView) findViewById(R.id.textViewError);
        editTextPseudo = (EditText) findViewById(R.id.editTextPseudo);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        editTextError.setVisibility(View.INVISIBLE);

        mAPIService = ApiUtils.getAPIService();

        buttonCo.setOnClickListener(myhandler1);

    }

    View.OnClickListener myhandler1 = new View.OnClickListener() {
        public void onClick(View v) {
            if((editTextPseudo.getText().toString().equals("") || editTextPseudo.getText().toString().equals("Pseudo"))
                    || editTextPassword.getText().toString().equals("")){
                editTextError.setText("Tous les champs ne sont pas renseignés");
                editTextError.setVisibility(View.VISIBLE);
            }
            else{

                sendPost(editTextPseudo.getText().toString(), editTextPassword.getText().toString());
            }
        }
    };

    private void sendPost(String email, String password) {
        mAPIService.coPost(email, password).enqueue(new Callback<PostCo>() {
            @Override
            public void onResponse(Call<PostCo> call, Response<PostCo> response) {
                if(response.isSuccessful()) {
                    Intent i = new Intent(Connection.this, FirstMenu.class);
                    i.putExtra("token", response.body().getToken() );
                    startActivity(i);
                }
                else{
                    editTextError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<PostCo> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

}
