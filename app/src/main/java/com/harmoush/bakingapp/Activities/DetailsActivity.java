package com.harmoush.bakingapp.Activities;


import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.harmoush.bakingapp.Models.Recipe;
import com.harmoush.bakingapp.Models.Step;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        if(savedInstanceState!=null)
            mRecipe = savedInstanceState.getParcelable("mRecipe");
        else{
            Intent intent = getIntent();
            mRecipe = intent.getParcelableExtra("mRecipe");
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
            setTwoPane();
        }else{
            mTwoPane = false;
        }
        ingradientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findViewById(R.id.fragment_details) != null) {
                    mTwoPane = true;
                    setTwoPane();
                } else {
                    mTwoPane = false;
                    Intent in = new Intent(getApplicationContext(), IngradientActivity.class);
                    in.putParcelableArrayListExtra("mIngradients",mRecipe.getIngredients());
                    startActivity(in);
                }
            }
        });
    }

    private void setTwoPane(){
        IngradientFragmentActivity fragment = new IngradientFragmentActivity() ;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("mIngradients",mRecipe.getIngredients());
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_details, fragment)
                .commit();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("mRecipe",mRecipe);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRecipe = savedInstanceState.getParcelable("mRecipe");
    }

    @Override
    public void onListItemClickListener(int clikedItemIndex) {
      Snackbar.make(findViewById(R.id.step_list_item_layout),mSteps.get(clikedItemIndex).getShortDescription(),Snackbar.LENGTH_LONG).show();
        if(findViewById(R.id.fragment_details)!=null)
        {
            mTwoPane = true ;
            StepFragmentActivity fragment = new StepFragmentActivity() ;
            Bundle bundle = new Bundle();
            bundle.putParcelable("mRecipe",mRecipe);
            bundle.putInt("mPosition",clikedItemIndex);
            //bundle.putParcelableArrayList("mSize",mRecipe.getIngredients());
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_details, fragment)
                    .commit();
        }
        else
        {
            mTwoPane = false ;
            Intent intent= new Intent(this,StepActivity.class);
            intent.putExtra("mRecipe",mRecipe);
            intent.putExtra("mPosition",clikedItemIndex);
            intent.putExtra("mSize", mRecipe.getSteps().size());
            startActivity(intent);
        }

    }
}



