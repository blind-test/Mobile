package com.example.blind_test.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.blind_test.R;
import com.example.blind_test.model.Socket;
import com.example.blind_test.network.Api;
import com.example.blind_test.network.ApiUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
    ImageButton sendMessage;
    Button buttonStart;
    ListView output;
    EditText editMessage;
    TextView question;
    TextView timer;
    private int lobbyId;
    private Api mAPIService;
    private OkHttpClient client;
    private OkHttpClient client2;
    private String s;
    public WebSocket ws;
    public WebSocket ws2;
    public Message m = new Message();
    public Medias m2 = new Medias();
    public String message = "";
    ArrayList<String> listMessage = new ArrayList<String>();

    public static Game newInstance(int lobbyId) {
        Bundle bundle = new Bundle();
        bundle.putInt("lobbyId", lobbyId);
        Game gameFragment = new Game();
        gameFragment.setArguments(bundle);
        return gameFragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            lobbyId = bundle.getInt("lobbyId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readBundle(getArguments());

        return inflater.inflate(R.layout.fragment_game,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        timer = (TextView) view.findViewById(R.id.timer);
        question = (TextView) view.findViewById(R.id.question);
        videoGame = (VideoView) view.findViewById(R.id.videoGame);
        imageGame = (ImageView) view.findViewById(R.id.imageGame);
        buttonStart = (Button) view.findViewById(R.id.buttonStart);
        sendMessage = (ImageButton) view.findViewById(R.id.sendMessage);
        output = (ListView) view.findViewById(R.id.output);
        editMessage = (EditText) view.findViewById(R.id.editMessage);
        client = new OkHttpClient();
        client2 = new OkHttpClient();
        Bundle b = getActivity().getIntent().getExtras();
        s = b.getString("token");
        mAPIService = ApiUtils.getAPIService();

        videoGame.setVisibility(View.GONE);
        imageGame.setVisibility(View.GONE);
        buttonStart.setVisibility(View.GONE);
        question.setVisibility(View.GONE);
        timer.setVisibility(View.GONE);

        connectionMessage(s, lobbyId);


        try {
            m.start();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                connectionGame(s, lobbyId);
                try {
                    if(ws2 == null) {
                        m2.start();
                    }
                    else{
                        m2.startAgain();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    m.sendMessage();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        title.setText("Partie");
    }

    private void connectionGame(String token, int id) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.joinGame(map,Integer.toString(id) ).enqueue(new Callback<Socket>() {
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

    private void connectionMessage(String token, int id) {
        Map<String, String> map = new HashMap<>();
        map.put("JWT", token);
        mAPIService.joinMessage(map, Integer.toString(id) ).enqueue(new Callback<Socket>() {
            @Override
            public void onResponse(Call<Socket> call, retrofit2.Response<Socket> response) {
                if (response.isSuccessful()) {
                    Socket rep = response.body();
                    if(rep.getRunning().equals("false")){
                        buttonStart.setVisibility(View.VISIBLE);
                    }
                    else{
                        connectionGame(s, lobbyId);
                        try {
                            m2.start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            if(!text.equals("ping")) {
                String message = "";

                System.out.println("------\n");
                System.out.println(text+"\n");
                System.out.println("------\n");
                if(text.contains("subject\":\"msg")) {

                    int pos = text.indexOf("nickname");
                    pos += 11;
                    String verif = "";
                    for (int i = pos; i < text.length(); i++) {
                        verif = String.valueOf(text.charAt(i));
                        if (verif.equals("\"")) {
                            break;
                        }
                        message = message + text.charAt(i);
                    }
                    message = message + " : ";
                    pos = text.indexOf("content");
                    pos += 10;
                    for (int i = pos; i < text.length(); i++) {
                        verif = String.valueOf(text.charAt(i));
                        if (verif.equals("\"")) {
                            break;
                        }
                        message = message + text.charAt(i);
                    }
                    listMessage.add(message);
                    output();
                }
                else if(text.contains("subject\":\"game" )){
                    if(text.contains("subject\":\"game:new")){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buttonStart.setVisibility(View.GONE);
                            }
                        });
                    }
                    if(text.contains("subject\":\"game:finish")){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buttonStart.setVisibility(View.VISIBLE);
                                timer.setVisibility(View.GONE);
                                imageGame.setVisibility(View.GONE);
                                videoGame.setVisibility(View.GONE);
                                question.setText("");
                            }
                        });
                        listMessage.add("Partie terminé!");
                        output();
                    }
                }
                else if (text.contains("subject\":\"round" )){
                    if(text.contains("subject\":\"round:new")){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buttonStart.setVisibility(View.GONE);
                            }
                        });
                        //récup question
                        int pos = text.indexOf("question\"");
                        pos +=11;
                        String verif = "";
                        for(int i = pos ; i < text.length() ; i++){
                            verif = String.valueOf(text.charAt(i));
                            if(verif.equals("\"")) {
                                break;
                            }
                            message = message + text.charAt(i);
                        }
                        final String question = message;
                        sendQuestion(question);

                        //récup type média
                        pos = text.indexOf("kind");
                        pos +=7;
                        message = "";
                        for(int i = pos ; i < text.length() ; i++){
                            verif = String.valueOf(text.charAt(i));
                            if(verif.equals("\"")) {
                                break;
                            }
                            message = message + text.charAt(i);
                        }
                        //guestion du média
                        if(message.equals("picture")){
                            pos = text.indexOf("file_url");
                            pos +=11;
                            message = "";
                            for(int i = pos ; i < text.length() ; i++){
                                verif = String.valueOf(text.charAt(i));
                                if(verif.equals("\"")) {
                                    break;
                                }
                                message = message + text.charAt(i);
                            }
                            final String finalURL = message;
                            sendImage(finalURL);
                        }
                        else if(message.equals("video")){
                            pos = text.indexOf("file_url");
                            pos +=11;
                            message = "";
                            for(int i = pos ; i < text.length() ; i++){
                                verif = String.valueOf(text.charAt(i));
                                if(verif.equals("\"")) {
                                    break;
                                }
                                message = message + text.charAt(i);
                            }
                            System.out.println("-------------\n");
                            System.out.println(message+"\n");
                            System.out.println("-------------\n");
                            final String finalMessage = message;
                            sendVideo(finalMessage);
                        }
                        else if(message.equals("music")){
                            pos = text.indexOf("file_url");
                            pos +=11;
                            message = "";
                            for(int i = pos ; i < text.length() ; i++){
                                verif = String.valueOf(text.charAt(i));
                                if(verif.equals("\"")) {
                                    break;
                                }
                                message = message + text.charAt(i);
                            }
                            final String FinalMessage = message;
                            sendMusic(FinalMessage);

                        }
                    }
                    if(text.contains("subject\":\"round:finish")){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buttonStart.setVisibility(View.GONE);
                                imageGame.setVisibility(View.GONE);
                                videoGame.setVisibility(View.GONE);
                            }
                        });
                        int pos = text.indexOf("answers");
                        pos +=13;
                        String verif = "";
                        message = "réponse : ";
                        for(int i = pos ; i < text.length() ; i++){
                            verif = String.valueOf(text.charAt(i));
                            if(verif.equals("\\")) {
                                break;
                            }
                            message = message + text.charAt(i);
                        }
                        listMessage.add(message);
                    }
                }
                else if(text.contains("timer:decrement" )){
                    int pos = text.indexOf("time\"");
                    pos +=6;
                    String verif = "";
                    for(int i = pos ; i < text.length() ; i++){
                        verif = String.valueOf(text.charAt(i));
                        if(verif.equals("}")) {
                            break;
                        }
                        message = message + text.charAt(i);
                    }
                    final String urlFinal = message;
                    sendTimer(urlFinal);
                }
            }
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            System.out.println("Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            System.out.println("Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            System.out.println("Error : " + t.getMessage());
        }

        public void sendMessage() throws JSONException {
            JSONObject parameter = new JSONObject();
            JSONObject payload = new JSONObject();
            payload.put("JWT", s);
            payload.put("content",editMessage.getText().toString());
            parameter.put("event","message");
            parameter.put("topic","chat_room:lobby_" + lobbyId);
            parameter.put("payload", payload);
            ws.send(parameter.toString());
            editMessage.getText().clear();
        }


        private void start() throws JSONException {
            Request request = new Request.Builder()
                    .url("ws://blind-test-api.herokuapp.com/chat")
                    .build();
            Message listener = new Message();
            ws = client.newWebSocket(request, listener);
            JSONObject parameter = new JSONObject();
            JSONObject payload = new JSONObject();
            payload.put("JWT", s);
            parameter.put("event","join");
            parameter.put("topic","chat_room:lobby_" + lobbyId);
            parameter.put("payload", payload);
            output();
            ws.send(parameter.toString());
            client.dispatcher().executorService().shutdown();
        }

        private void output() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , listMessage);
                    output.setAdapter(arrayAdapter);
                    output.setSelection(arrayAdapter.getCount() - 1);
                }
            });
        }

        private void sendImage(final String url) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    url.replaceAll("https://","http://");
                    Picasso.get().load(url).into(imageGame);

                    videoGame.setVisibility(View.GONE);
                    imageGame.setVisibility(View.VISIBLE);
                }
            });
        }

        private void sendVideo(final String url) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri uri = Uri.parse(url);
                    videoGame.setVideoURI(uri);
                    videoGame.requestFocus();
                    videoGame.start();
                    videoGame.setVisibility(View.VISIBLE);
                    imageGame.setVisibility(View.GONE);
                }
            });
        }


        private void sendMusic(final String url) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        MediaPlayer mp = new MediaPlayer();
                        mp.setDataSource(url);
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        private void sendQuestion(final String url) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    question.setVisibility(View.VISIBLE);
                    question.setText(url);
                }
            });
        }

        private void sendTimer(final String url) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    timer.setVisibility(View.VISIBLE);
                    timer.setText(url);
                }
            });
        }

    }

    public class Medias extends WebSocketListener {

        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
        }



        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            System.out.println("Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            System.out.println("Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            System.out.println("Error : " + t.getMessage());
        }

        private void start() throws JSONException {
            Request request = new Request.Builder()
                    .url("ws://blind-test-api.herokuapp.com/game")
                    .build();
            Message listener = new Message();
            ws2 = client2.newWebSocket(request, listener);
            JSONObject parameter = new JSONObject();
            parameter.put("event","join");
            parameter.put("topic","game_room:lobby_" + lobbyId);
            ws2.send(parameter.toString());
            client2.dispatcher().executorService().shutdown();
        }

        private void startAgain() throws JSONException {
            Request request = new Request.Builder()
                    .url("ws://blind-test-api.herokuapp.com/game")
                    .build();
            JSONObject parameter = new JSONObject();
            parameter.put("event","join");
            parameter.put("topic","game_room:lobby_" + lobbyId);
            ws2.send(parameter.toString());
        }

    }
}