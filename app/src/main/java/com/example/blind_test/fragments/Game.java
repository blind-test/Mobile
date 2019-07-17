package com.example.blind_test.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.blind_test.R;
import com.example.blind_test.model.Socket;
import com.example.blind_test.model.listUsers;
import com.example.blind_test.network.Api;
import com.example.blind_test.network.ApiUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.Callback;

import static android.support.constraint.Constraints.TAG;


public class Game extends Fragment {

    TextView title;
    VideoView videoGame;
    ImageView imageGame;
    Button start;
    TextView output;
    private Api mAPIService;
    private OkHttpClient client;
    private String s;
    public Message m = new Message();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        videoGame = (VideoView) view.findViewById(R.id.videoGame);
        imageGame = (ImageView) view.findViewById(R.id.imageGame);
        start = (Button) view.findViewById(R.id.start);
        output = (TextView) view.findViewById(R.id.output);
        client = new OkHttpClient();
        Bundle b = getActivity().getIntent().getExtras();
        s = b.getString("token");
        mAPIService = ApiUtils.getAPIService();
        connectionGame(s);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    m.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
/*
        Uri uri = Uri.parse("https://dev-blind-test.s3-eu-west-1.amazonaws.com/video/Risitas+ISSOUUUUUU.mp4");
        videoGame.setVideoURI(uri);
        videoGame.requestFocus();
        videoGame.start();

        try {
        MediaPlayer mp = new MediaPlayer();
            mp.setDataSource("https://dev-blind-test.s3-eu-west-1.amazonaws.com/music/ye-banished-privateers-coopers-rum.mp3");
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = "https://dev-blind-test.s3-eu-west-1.amazonaws.com/picture/2019-06-06-17-51-00-shadow.jpg";
        url.replaceAll("https://","http://");

        Picasso.get().load(url).into(imageGame);
*/

        title.setText("Partie");
    }

    private void connectionGame(String token) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.joinPublicGame(map).enqueue(new Callback<Socket>() {
            @Override
            public void onResponse(Call<Socket> call, retrofit2.Response<Socket> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Socket> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
    public class Message extends WebSocketListener {

        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("Hello, it's SSaurel !");
            webSocket.send("What's up ?");
            webSocket.send(ByteString.decodeHex("deadbeef"));
            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output("Receiving : " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            output("Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error : " + t.getMessage());
        }

        private void start() throws JSONException {
            Request request = new Request.Builder()
                    .url("ws://blind-test-api.herokuapp.com/chat")
                    .build();
            Message listener = new Message();
            WebSocket ws = client.newWebSocket(request, listener);
            JSONObject parameter = new JSONObject();
            Map<String, String> map = new HashMap<>();
            map.put("JWT", s);
            parameter.put("event","join");
            parameter.put("topic","chat_room:lobby_1");
            parameter.put("payload",map );
            ws.send(parameter.toString());
            client.dispatcher().executorService().shutdown();
        }

        private void output(final String txt) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    output.setText(output.getText().toString() + "\n\n" + txt);
                }
            });
        }
    }
}