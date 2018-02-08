package com.harmoush.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harmoush.bakingapp.IngradientAdapter;
import com.harmoush.bakingapp.Models.Ingradient;
import com.harmoush.bakingapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harmoush on 2/8/2018.
 */

public class IngradientFragmentActivity extends Fragment {
    ArrayList<Ingradient> mIngradients;
    @BindView(R.id.rv_ingradients) RecyclerView mIngradientRecyclerView;
    IngradientAdapter mIngredientAdapter;
    private GridLayoutManager mgridLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredent, container, false);
        ButterKnife.bind(this,rootView);
        Intent intent = getActivity().getIntent();

        if (intent.hasExtra("ingradient")) {
            mIngradients = intent.getParcelableArrayListExtra("ingradient");
        }
       /* if(getArguments() != null)
        {
            ingradients = (ArrayList<Ingradient>) getArguments().getSerializable("ing");
        }*/
        mgridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        mIngradientRecyclerView.setLayoutManager(mgridLayoutManager);
        mIngredientAdapter = new IngradientAdapter(mIngradients);
        mIngradientRecyclerView.setAdapter(mIngredientAdapter);
        mIngredientAdapter.notifyDataSetChanged();

        return rootView;
    }
}
