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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.blind_test.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class Game extends Fragment {

    TextView title;
    VideoView videoGame;
    ImageView imageGame;

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

        title.setText("Partie");
    }

}