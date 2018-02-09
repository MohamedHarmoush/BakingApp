package com.harmoush.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.harmoush.bakingapp.Models.Ingradient;
import com.harmoush.bakingapp.Models.Recipe;
import com.harmoush.bakingapp.Models.Step;
import com.harmoush.bakingapp.R;
import com.harmoush.bakingapp.RecipeAdapter;
import com.harmoush.bakingapp.Widget.BakingAppWidget;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListner{

    @BindView(R.id.rv_recipes)
    RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private ArrayList<Recipe> mRecipes;
   // private int numberOfViews;
    private GridLayoutManager mgridLayoutManager;

    public static String BASIC_API_URL ="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipes = new ArrayList<>();
        ButterKnife.bind(this);
        mRecipeAdapter = new RecipeAdapter(mRecipes,this);
       // numberOfViews = calculateNoOfColumns(this);
        mgridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRecipeRecyclerView.setLayoutManager(mgridLayoutManager);
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);
        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(getString(R.string.mRecipes));
            mRecipeAdapter = new RecipeAdapter(mRecipes,this);
            mRecipeRecyclerView.setAdapter(mRecipeAdapter);
        } else {
            if (isNetworkAvailable())
                fetchDataFromInternet();
            else
                Snackbar.make(findViewById(R.id.layout), R.string.no_conn,Snackbar.LENGTH_LONG).show();
        }

    }
  /*  public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }
*/
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void fetchDataFromInternet() {
        mRecipes.clear();
        Ion.with(this)
                .load(BASIC_API_URL)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        // do stuff with the result or error
                        if (e == null) {
                            parseJsonArray(result);
                            mRecipeAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    public void parseJsonArray(JsonArray jsonArray){
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject recipeJsonObject = jsonArray.get(i).getAsJsonObject();
            String id,name,imageURL;
            ArrayList<Ingradient> ingradients = new ArrayList<>();
            ArrayList<Step> steps = new ArrayList<>();
            Integer servings;
            id = recipeJsonObject.get("id").getAsString().replace("\"","");
            name = recipeJsonObject.get("name").getAsString().replace("\"","");
            JsonArray ingradientsJasonArray = recipeJsonObject.getAsJsonArray("ingredients");
            for (int j = 0; j < ingradientsJasonArray.size(); j++){
                Ingradient ingredient = new Ingradient();
                JsonObject ingradientsJasonObject = ingradientsJasonArray.get(j).getAsJsonObject();
                ingredient.setQuantity(ingradientsJasonObject.get("quantity").getAsFloat());
                ingredient.setMeasure(ingradientsJasonObject.get("measure").getAsString().replace("\"",""));
                ingredient.setIngredient(ingradientsJasonObject.get("ingredient").getAsString().replace("\"",""));
                ingradients.add(ingredient);
            }
            JsonArray stepsJasonArray = recipeJsonObject.getAsJsonArray("steps");
            for (int j = 0; j < stepsJasonArray.size(); j++){
                Step step = new Step();
                JsonObject jsonObject = stepsJasonArray.get(j).getAsJsonObject();
                step.setId(jsonObject.get("id").getAsInt());
                step.setDescription(jsonObject.get("description").getAsString().replace("\"",""));
                step.setShortDescription(jsonObject.get("shortDescription").getAsString().replace("\"",""));
                if (jsonObject.has("videoURL")) {
                    step.setVideoURL(jsonObject.get("videoURL").getAsString().replace("\"",""));
                }else
                    step.setVideoURL("");
                if (jsonObject.has("thumbnailURL")) {
                    step.setThumbnailURL(jsonObject.get("thumbnailURL").getAsString().replace("\"",""));
                }else
                    step.setThumbnailURL("");

            }
            if (recipeJsonObject.has("servings")) {
                servings = recipeJsonObject.get("servings").getAsInt();
            }
            servings = -1;
            if (recipeJsonObject.has("image")) {
                imageURL = recipeJsonObject.get("image").getAsString().replace("\"","");
            }else
                imageURL ="";

            mRecipes.add(new Recipe(id,name,ingradients,steps,servings,imageURL));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.mRecipes), mRecipes);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRecipes  = savedInstanceState.getParcelableArrayList(getString(R.string.mRecipes));
    }

    @Override
    public void onListItemClickListener(int clikedItemIndex) {
        //ToDo :- complete this method.
        //new branch commit
        Snackbar.make(findViewById(R.id.layout),mRecipes.get(clikedItemIndex).getName(),Snackbar.LENGTH_LONG).show();
        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.FavoriteIngradients), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(getString(R.string.DesiredIngredients), getRecipeIngredients(clikedItemIndex));
        editor.apply();
        Intent intentwidget = new Intent(this, BakingAppWidget.class);
        intentwidget.setAction(BakingAppWidget.MY_WIDGET_UPDATE);
        sendBroadcast(intentwidget);
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("mRecipe", mRecipes.get(clikedItemIndex));
        startActivity(intent);
    }

    private String getRecipeIngredients(int position) {
        String ingredient = "";
        int mSize =  mRecipes.get(position).getIngredients().size();
        for (int i = 0; i < mSize; i++) {
            ingredient += String.valueOf(mRecipes.get(position).getIngredients().get(i).getQuantity()) + " "
                    + String.valueOf(mRecipes.get(position).getIngredients().get(i).getMeasure()) +
                    " " + String.valueOf(mRecipes.get(position).getIngredients().get(i).getIngredient());
            if(i != mSize - 1)
                ingredient +="\n";
        }
        return ingredient;
    }
}
