package com.example.streaming_assign01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class MainActivity2 extends AppCompatActivity {
    PlayerView playerView;
    SimpleExoPlayer player;
    String videoURl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4";

    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playPackPosition = 0;

    Button btnVideo_2;
    Button goToActivity3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        playerView = findViewById(R.id.video_view2);
        btnVideo_2 = findViewById(R.id.Btn_Video2);
        goToActivity3 = findViewById(R.id.goToActivity3);

        btnVideo_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initVideo();
            }
        });

        goToActivity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity3.class);
                startActivity(i);
                releaseVideo();
            }
        });
    }

    public void initVideo() {
        //create player
        player = ExoPlayerFactory.newSimpleInstance(this);
        //connect Player with playerView
        playerView.setPlayer(player);
        //media source
        Uri uri = Uri.parse(videoURl);
        DataSource.Factory factory = new DefaultDataSourceFactory(this, "exoplayer-codelab");
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(factory).createMediaSource(uri);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playPackPosition);
        player.prepare(mediaSource, false, false);
    }


    public void releaseVideo() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playPackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            initVideo();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseVideo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseVideo();
    }
}