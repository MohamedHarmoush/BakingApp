package com.harmoush.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.harmoush.bakingapp.models.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harmoush on 2/7/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private Context context;
    private ArrayList<Recipe>  mRecipes;
    final private ListItemClickListner mListItemClickListner;

    public RecipeAdapter(ArrayList<Recipe> mRecipes, ListItemClickListner mListItemClickListner) {
        this.mRecipes = mRecipes;
        this.mListItemClickListner = mListItemClickListner;
    }

    public interface ListItemClickListner {
        void onListItemClickListener(int clikedItemIndex);
    }
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rootView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(rootView);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final Recipe recipe = mRecipes.get(position);
        holder.recipeName.setText(recipe.getName());
        int x =recipe.getServings();
        if(recipe.getServings()!= -1)
            holder.servings.setText("servings : "+recipe.getServings());
        else
            holder.servings.setVisibility(View.INVISIBLE);
        if(recipe.getImageURL()!=null && recipe.getImageURL()!="")
            Glide.with(context).load(recipe.getImageURL()).into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return (mRecipes!=null) ? mRecipes.size(): 0 ;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_recipe_name)
        TextView recipeName;
        @BindView(R.id.iv_recipe)
        ImageView recipeImage;
        @BindView(R.id.tv_servings)
        TextView servings;
        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mListItemClickListner.onListItemClickListener(pos);
        }
    }
}
