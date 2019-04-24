package com.example.blind_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonInscription;
    private Button buttonConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonInscription = (Button) findViewById(R.id.buttonInscription);
        buttonConnection = (Button) findViewById(R.id.buttonConnection);

        buttonInscription.setOnClickListener(myhandler1);
        buttonConnection.setOnClickListener(myhandler2);
    }

    View.OnClickListener myhandler1 = new View.OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, Inscription.class));
        }
    };

    View.OnClickListener myhandler2 = new View.OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, Connection.class));
        }
    };

}
