package com.harmoush.bakingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.harmoush.bakingapp.models.Recipe;
import com.harmoush.bakingapp.R;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harmoush on 2/10/2018.
 */

public class StepFragmentActivity extends Fragment {



    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.descriptionStep) TextView Description;
    private Recipe mRecipe;
    int position;
    @BindView(R.id.iv_img) ImageView mImage;
    Uri mediaUri;
    public static Long mVideoStepPostion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this,rootView);
        if (getArguments() != null) {
            mRecipe =  getArguments().getParcelable("mRecipe");
            position = getArguments().getInt("mPosition");

        }
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            mRecipe = intent.getExtras().getParcelable("mRecipe");
        }

        if (mRecipe.getSteps().get(position).getVideoURL().isEmpty() &&
                !mRecipe.getSteps().get(position).getThumbnailURL().isEmpty()) {
            Glide.with(getActivity())
                    .load(mRecipe.getSteps().get(position).getThumbnailURL())
                    .into(mImage);
            mPlayerView.setVisibility(View.GONE);
        }

        mPlayerView.setVisibility(View.VISIBLE);
        Description.setText(mRecipe.getSteps().get(position).getDescription());
        return rootView;
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
        mediaUri = Uri.parse(mRecipe.getSteps().get(position).getVideoURL());
        initializePlayer();

    }
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mVideoStepPostion = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
    private void initializePlayer() {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            Context context = getActivity();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector(),new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String mUserAgent = com.google.android.exoplayer2.util.Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), mUserAgent), new DefaultExtractorsFactory(), null, null);
            if(mVideoStepPostion !=null)
                mExoPlayer.seekTo(mVideoStepPostion);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Long currentPosition = mExoPlayer.getCurrentPosition();
        outState.putLong("mVideoStepPostion",currentPosition);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null)
            mVideoStepPostion = savedInstanceState.getLong("mVideoStepPostion");
    }
}