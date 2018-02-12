package com.harmoush.bakingapp.activities;


import android.app.Activity;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.harmoush.bakingapp.models.Recipe;
import com.harmoush.bakingapp.models.Step;
import com.harmoush.bakingapp.R;
import com.harmoush.bakingapp.StepAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements StepAdapter.ListItemClickListner{

    private boolean mTwoPane;
    @BindView(R.id.btn_ingradients)
    Button ingradientsButton;
    Recipe mRecipe;
    @BindView(R.id.rv_steps)
    RecyclerView mRecyclerViewSteps;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Step> mSteps;
    private StepAdapter mStepAdapter;
    private int clikedItemIndex;
    private boolean btnClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        clikedItemIndex = -1;
        if(savedInstanceState!=null){
            mRecipe = savedInstanceState.getParcelable("mRecipe");
            clikedItemIndex = savedInstanceState.getInt("clikedItemIndex");
            btnClicked =savedInstanceState.getBoolean("btnClicked",false);
        }
      else{
            Intent intent = getIntent();
            mRecipe = intent.getParcelableExtra("mRecipe");
            btnClicked =false;
      }
        setTitle(mRecipe.getName());
        mSteps = mRecipe.getSteps();
        gridLayoutManager = new GridLayoutManager(this,1, LinearLayoutManager.VERTICAL,false);
        mStepAdapter = new StepAdapter(mSteps,this);
        mRecyclerViewSteps.setLayoutManager(gridLayoutManager);
        mRecyclerViewSteps.setAdapter(mStepAdapter);
        mStepAdapter.notifyDataSetChanged();

        if(findViewById(R.id.fragment_details)!= null){
            mTwoPane = true;
            setTwoPane(btnClicked);
        }else{
            mTwoPane = false;
        }
        ingradientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findViewById(R.id.fragment_details) != null) {
                    mTwoPane = true;
                    setTwoPane(btnClicked);
                } else {
                    mTwoPane = false;
                    Intent in = new Intent(getApplicationContext(), IngradientActivity.class);
                    in.putParcelableArrayListExtra("mIngradients",mRecipe.getIngredients());
                    startActivity(in);
                }
            }
        });
    }

    private void setTwoPane(boolean btnClicked){
        Bundle bundle = new Bundle();
        if(btnClicked){
            StepFragmentActivity fragment = new StepFragmentActivity() ;
            bundle.putParcelable("mRecipe",mRecipe);
            bundle.putInt("mPosition",clikedItemIndex);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_details, fragment)
                    .commit();
        }else{
            IngradientFragmentActivity fragment = new IngradientFragmentActivity() ;
            bundle.putParcelableArrayList("mIngradients",mRecipe.getIngredients());
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_details, fragment)
                    .commit();
        }


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("mRecipe",mRecipe);
        if(mTwoPane){
            outState.putInt("clikedItemIndex",clikedItemIndex);
            if(clikedItemIndex !=-1)
                outState.putBoolean("btnClicked",true);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRecipe = savedInstanceState.getParcelable("mRecipe");
        clikedItemIndex = savedInstanceState.getInt("clikedItemIndex");
        btnClicked = savedInstanceState.getBoolean("btnClicked");
    }

    @Override
    public void onListItemClickListener(int clikedItemIndex) {
        Snackbar.make(findViewById(R.id.step_list_item_layout),mSteps.get(clikedItemIndex).getShortDescription(),Snackbar.LENGTH_LONG).show();
        this.clikedItemIndex = clikedItemIndex;
        if(findViewById(R.id.fragment_details)!=null)
        {
            mTwoPane = true ;
            StepFragmentActivity fragment = new StepFragmentActivity() ;
            Bundle bundle = new Bundle();
            bundle.putParcelable("mRecipe",mRecipe);
            bundle.putInt("mPosition",clikedItemIndex);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_details, fragment)//.addToBackStack("back")
                    .commit();
        }
        else
        {
            mTwoPane = false ;
            Intent intent= new Intent(this,StepActivity.class);
            intent.putExtra("mRecipe",mRecipe);
            intent.putExtra("mPosition",clikedItemIndex);
            intent.putExtra("mSize", mRecipe.getSteps().size());
           // startActivity(intent);
            startActivityForResult(intent,1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && data!= null) {
                    mRecipe = data.getParcelableExtra("mRecipe");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}