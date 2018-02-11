package com.harmoush.bakingapp.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.harmoush.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.harmoush.bakingapp.activities.StepFragmentActivity.mVideoStepPostion;

/**
 * Created by Harmoush on 2/10/2018.
 */

public class FullscreenActivity extends AppCompatActivity {

    String mStepVideoURL;
    @BindView(R.id.playerViewfull) SimpleExoPlayerView mPlayerView;
    SimpleExoPlayer mExoPlayer;
    private Uri mediaUri;
    private Long mVideoStepPostion;
    private boolean playWhenReady;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        ButterKnife.bind(this);
        mStepVideoURL = getIntent().getStringExtra("mStepVideoURL");
        if(getIntent().hasExtra("mVideoStepPostion")){
            mVideoStepPostion = getIntent().getLongExtra("mVideoStepPostion",0);
            playWhenReady = getIntent().getBooleanExtra("playWhenReady",false);
        }

        mediaUri = Uri.parse(mStepVideoURL);
        if(savedInstanceState != null){
            mVideoStepPostion = savedInstanceState.getLong("mVideoStepPostion");
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");

        }
        initializePlayer();
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer();
        }


    }
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mVideoStepPostion = mExoPlayer.getCurrentPosition();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
    private void initializePlayer() {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector(),new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String mUserAgent = com.google.android.exoplayer2.util.Util.getUserAgent(this, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, mUserAgent), new DefaultExtractorsFactory(), null, null);
            if(mVideoStepPostion !=null)
                mExoPlayer.seekTo(mVideoStepPostion);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(playWhenReady);

        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("mVideoStepPostion",mExoPlayer.getCurrentPosition());
        outState.putBoolean("playWhenReady",playWhenReady);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            mVideoStepPostion = savedInstanceState.getLong("mVideoStepPostion");
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
