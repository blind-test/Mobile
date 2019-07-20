package com.example.blind_test;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.blind_test.fragments.Scores;
import com.example.blind_test.model.PostCo;
import com.example.blind_test.network.Api;
import com.example.blind_test.network.ApiUtils;
import com.example.blind_test.fragments.*;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstMenu extends AppCompatActivity {

    private Api mAPIService;
    private Integer idScore;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_audio:
                    getSupportFragmentManager()
                            .beginTransaction().replace(R.id.activity_main_frame_layout, new Gamelist()).commit();
                    return true;
                case R.id.bottom_friends:
                    getSupportFragmentManager()
                            .beginTransaction().replace(R.id.activity_main_frame_layout, new Friends()).commit();
                    return true;
                case R.id.bottom_trophy:
                    getSupportFragmentManager()
                            .beginTransaction().replace(R.id.activity_main_frame_layout, new Scores()).commit();
                    return true;
            }
            return false;
        }
    };

    private static final String TAG = "Menu";

    private String s;

    private int status = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstmenu);

        Bundle b = getIntent().getExtras();
        s = b.getString("token");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main_frame_layout, new Gamelist())
                .commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mAPIService = ApiUtils.getAPIService();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getMenuInflater().inflate(R.menu.option, (android.view.Menu) menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.status:
                if(status == 1){
                    item.setIcon(R.drawable.ic_cercle_occupied);
                    status = 2;
                }
                else if(status == 2){
                    item.setIcon(R.drawable.ic_cercle_offline);
                    status = 3;
                }
                else{
                    item.setIcon(R.drawable.ic_cercle_online);
                    status = 1;
                }
                return true;
            case R.id.user:
                getSupportFragmentManager()
                        .beginTransaction().replace(R.id.activity_main_frame_layout, new User()).commit();
                return true;
            case R.id.deco:
                sendPost(s);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendPost(String token) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.outDelete(map).enqueue(new Callback<PostCo>() {
            @Override
            public void onResponse(Call<PostCo> call, Response<PostCo> response) {
                if(response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." );
                    startActivity(new Intent(FirstMenu.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<PostCo> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
}
