package com.harmoush.bakingapp.Activities;

import android.net.Uri;
import android.os.Bundle;
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
import com.harmoush.bakingapp.Models.Recipe;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this,rootView);
        if (getArguments() != null) {
            mRecipe =  getArguments().getParcelable("mRecipe");
            position = getArguments().getInt("mPosition");

        }
        if (getActivity().getIntent() != null) {
            mRecipe = (Recipe)getActivity().getIntent().getExtras().get("mRecipe");
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
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer(Uri.parse(mRecipe.getSteps().get(position).getVideoURL()));
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector(),new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String mUserAgent = com.google.android.exoplayer2.util.Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), mUserAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


}
