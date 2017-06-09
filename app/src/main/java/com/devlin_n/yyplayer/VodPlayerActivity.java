package com.devlin_n.yyplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.devlin_n.library.FloatWindowManager;
import com.devlin_n.yin_yang_player.controller.AdController;
import com.devlin_n.yin_yang_player.controller.StandardVideoController;
import com.devlin_n.yin_yang_player.player.YinYangPlayer;
import com.devlin_n.yin_yang_player.player.VideoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 点播播放
 * Created by Devlin_n on 2017/4/7.
 */

public class VodPlayerActivity extends AppCompatActivity {

    private YinYangPlayer magicVideoView;
    private static final String URL_VOD = "http://mov.bn.netease.com/open-movie/nos/flv/2017/01/03/SC8U8K7BC_hd.flv";
    //    private static final String URL_VOD = "http://uploads.cutv.com:8088/video/data/201703/10/encode_file/515b6a95601ba6b39620358f2677a17358c2472411d53.mp4";
    private static final String URL_AD = "http://gslb.miaopai.com/stream/FQXM04zrW1dcXGiPdJ6Q3KAq2Fpv4TLV.mp4";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod_player);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("VOD");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        magicVideoView = (YinYangPlayer) findViewById(R.id.magic_video_view);
//        int widthPixels = getResources().getDisplayMetrics().widthPixels;
//        magicVideoView.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, widthPixels / 16 * 9));

        List<VideoModel> videos = new ArrayList<>();
        videos.add(new VideoModel(URL_AD, "广告", new AdController(this)));
        videos.add(new VideoModel(URL_VOD, "网易公开课-如何掌控你的自由时间", new StandardVideoController(this)));

        magicVideoView
                .autoRotate()
//                .enableCache()
//                .useSurfaceView()
//                .useAndroidMediaPlayer()
                .setVideos(videos)
//                .setUrl(URL_VOD)
//                .setTitle("网易公开课-如何掌控你的自由时间")
                .start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        magicVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        magicVideoView.resume();
        magicVideoView.stopFloatWindow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        magicVideoView.release();
    }


    @Override
    public void onBackPressed() {
        if (!magicVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FloatWindowManager.PERMISSION_REQUEST_CODE) {
            if (FloatWindowManager.getInstance().checkPermission(this)) {
                magicVideoView.startFloatWindow();
            } else {
                Toast.makeText(VodPlayerActivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startFloatWindow(View view) {
        magicVideoView.startFloatWindow();
    }

    public void wide(View view) {
        magicVideoView.setScreenType(YinYangPlayer.SCREEN_TYPE_16_9);
    }

    public void tv(View view) {
        magicVideoView.setScreenType(YinYangPlayer.SCREEN_TYPE_4_3);
    }

    public void match(View view) {
        magicVideoView.setScreenType(YinYangPlayer.SCREEN_TYPE_MATCH_PARENT);
    }

    public void original(View view) {
        magicVideoView.setScreenType(YinYangPlayer.SCREEN_TYPE_ORIGINAL);
    }
}