package com.example.blind_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.blind_test.model.PostCo;
import com.example.blind_test.network.Api;
import com.example.blind_test.network.ApiUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu extends AppCompatActivity {

    private Api mAPIService;

    private  Button buttonOut;

    private static final String TAG = "Menu";

    private String s;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle b = getIntent().getExtras();
        s = b.getString("token");

        buttonOut = (Button) findViewById(R.id.buttonOut);

        buttonOut.setOnClickListener(myhandler1);

        mAPIService = ApiUtils.getAPIService();
    }

    View.OnClickListener myhandler1 = new View.OnClickListener() {
        public void onClick(View v) {
            sendPost(s);
        }
    };

    private void sendPost(String token) {
        System.out.println(token);
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.outDelete(map).enqueue(new Callback<PostCo>() {
            @Override
            public void onResponse(Call<PostCo> call, Response<PostCo> response) {
                if(response.isSuccessful()) {
                    System.out.println("222222222222222222");
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    startActivity(new Intent(Menu.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<PostCo> call, Throwable t) {
                System.out.println("1111111111111111111111111111");
                System.out.println("call : " + t + "\nt : " + t);
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
}
