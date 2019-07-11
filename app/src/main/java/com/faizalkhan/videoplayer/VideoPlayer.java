package com.faizalkhan.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.khizar1556.mkvideoplayer.MKPlayer;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoPlayer extends AppCompatActivity {
    int position = -1;
    MKPlayer player;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        position = getIntent().getIntExtra("position", -1);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        playerVideo();

    }

    private void playerVideo() {

        player = new  MKPlayer(this);
        player.onComplete(new Runnable() {
            @Override
            public void run() {
                //callback when video is finish
                Toast.makeText(getApplicationContext(), "video play completed",Toast.LENGTH_SHORT).show();
            }
        }).onError(new MKPlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
                Toast.makeText(getApplicationContext(), "video play error", Toast.LENGTH_SHORT).show();
            }
        });
        player.setPlayerCallbacks(new MKPlayer.playerCallbacks() {
            @Override
            public void onNextClick() {
                position = position+1;

                String url= String.valueOf(MainActivity.fileArrayList.get(position));

                player.play(url);
                player.setTitle(url);
            }

            @Override
            public void onPreviousClick() {
                position = position-1;
                if(position>=0){
                String url= String.valueOf(MainActivity.fileArrayList.get(position));

                player.play(url);
                player.setTitle(url);
                }else{
                    Toast.makeText(VideoPlayer.this, "No Any Previous Video Left", Toast.LENGTH_SHORT).show();
                }

                /*String url = ((EditText) findViewById(R.id.et_url)).getText().toString();
                MKPlayerActivity.configPlayer(videoplayer.this).setTitle(url).play(url);*/
            }
        });
        String url= String.valueOf(MainActivity.fileArrayList.get(position));
        player.play(url);
        player.setTitle(url);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();

    }
}
