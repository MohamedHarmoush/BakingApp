package com.harmoush.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harmoush.bakingapp.Models.Ingradient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harmoush on 2/7/2018.
 */

public class IngradientAdapter extends RecyclerView.Adapter<IngradientAdapter.IngradientViewHolder> {
    private Context context;
    private ArrayList<Ingradient> mIngradients;

    public IngradientAdapter(ArrayList<Ingradient> mIngradients) {
        this.mIngradients = mIngradients;
    }

    @Override
    public IngradientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rootView = LayoutInflater.from(context).inflate(R.layout.ingredient_list_item,parent,false);
        IngradientViewHolder ingradientViewHolder = new IngradientViewHolder(rootView);
        return ingradientViewHolder;
    }

    @Override
    public void onBindViewHolder(IngradientViewHolder holder, int position) {
        final Ingradient ingredient = mIngradients.get(position);
       holder.ingradient.setText(ingredient.getQuantity()+" "+ingredient.getMeasure()+" "+ingredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        return (mIngradients!=null) ? mIngradients.size(): 0 ;
    }

    public class IngradientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ingradient)
        TextView ingradient;
        public IngradientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
