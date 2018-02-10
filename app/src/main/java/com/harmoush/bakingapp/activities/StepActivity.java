package com.harmoush.bakingapp.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.harmoush.bakingapp.models.Recipe;
import com.harmoush.bakingapp.R;
import com.harmoush.bakingapp.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.harmoush.bakingapp.activities.StepFragmentActivity.mVideoStepPostion;

/**
 * Created by Harmoush on 2/10/2018.
 */

public class StepActivity extends AppCompatActivity {
    @BindView(R.id.view_pager) ViewPager mViewPager;
    private int mNumberofFragments = 0;
    private int mPosition;
    Recipe mRecipe;
    @BindView(R.id.bt_previous)
    Button mPrevious;
    @BindView(R.id.bt_next)
    Button mNext;
    private int mRecipeStepPostion;
    private int mOrientationConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        if(savedInstanceState!=null) {
            mRecipeStepPostion = savedInstanceState.getInt("mRecipeStepPostion");
            mVideoStepPostion = savedInstanceState.getLong("mVideoStepPostion");
        }
        Intent in = getIntent();
        mNumberofFragments = in.getExtras().getInt("mSize");
        if(in.getExtras()!=null) {
            mPosition = getIntent().getIntExtra("mPosition",5);
            mRecipe = getIntent().getParcelableExtra("mRecipe");
        }

        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        ViewPagerAdapter adapter = new ViewPagerAdapter
                (getSupportFragmentManager(), mNumberofFragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mPosition);
        if (mPosition == 0) {
            mPrevious.setVisibility(View.GONE);
        }
        mOrientationConfiguration = this.getResources().getConfiguration().orientation;
        if (mOrientationConfiguration == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(this , FullscreenActivity.class) ;
            intent.putExtra("mStepVideoURL",mRecipe.getSteps().get(mRecipeStepPostion).getVideoURL());
            if (mVideoStepPostion !=null) {
                intent.putExtra("mVideoStepPostion",mVideoStepPostion);
            }
            startActivity(intent);
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mPrevious.setVisibility(View.GONE);
                } else if (position == mNumberofFragments - 1) {
                    mNext.setVisibility(View.GONE);
                } else {
                    mPrevious.setVisibility(View.VISIBLE);
                    mNext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(getItem(-1), true);

            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(getItem(+ 1), true);

            }
        });
    }
    int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mRecipeStepPostion",mViewPager.getCurrentItem());
        outState.putLong("mVideoStepPostion",mVideoStepPostion);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState !=null){
            mRecipeStepPostion = savedInstanceState.getInt("mRecipeStepPostion");
            mVideoStepPostion = savedInstanceState.getLong("mVideoStepPostion");
        }
    }
}

