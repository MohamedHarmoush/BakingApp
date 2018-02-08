package com.harmoush.bakingapp.Activities;


import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.harmoush.bakingapp.Models.Recipe;
import com.harmoush.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    private boolean mTwoPane;
    @BindView(R.id.btn_ingradients)
    Button ingradientsButton;
    Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra("recipe");
        setTitle(mRecipe.getName());
        if(findViewById(R.id.fragment_details)!= null){
            mTwoPane = true;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            IngradientFragmentActivity fragment = new IngradientFragmentActivity() ;
            Intent in = new Intent();
            in.putExtra("ingradient", mRecipe.getIngredients());
            fragmentTransaction.replace(R.id.fragment_details, fragment);
            fragmentTransaction.commit();
        }else{
            mTwoPane = false;
        }
        ingradientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findViewById(R.id.fragment_details) != null) {
                    mTwoPane = true;
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    IngradientFragmentActivity fragment = new IngradientFragmentActivity();
                    Intent in = new Intent();
                    in.putParcelableArrayListExtra("ingradient",mRecipe.getIngredients());

                    fragmentTransaction.replace(R.id.fragment_details, fragment);
                    fragmentTransaction.commit();
                } else {
                    mTwoPane = false;
                    Intent in = new Intent(getApplicationContext(), IngradientActivity.class);
                    in.putParcelableArrayListExtra("ingradient",mRecipe.getIngredients());
                    startActivity(in);
                }
            }
        });
    }





}



