package com.harmoush.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Harmoush on 1/30/2018.
 */

public class Recipe implements Parcelable {
    String id;
    String name;
    ArrayList<Ingredient> ingredients;
    ArrayList<Step> steps;
    Integer servings;
    String imageURL;

    public Recipe() {
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    public ArrayList<Step> getSteps() {
        return steps;
    }
    public Integer getServings() {
        return servings;
    }
    public String getImageURL() {
        return imageURL;
    }


    public Recipe(String id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, Integer servings, String imageURL) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.imageURL = imageURL;
    }

    protected Recipe(Parcel in) {
        id = in.readString();
        name = in.readString();
        if (in.readByte() == 0x01) {
            ingredients = new ArrayList<Ingredient>();
            in.readList(ingredients, Ingredient.class.getClassLoader());
        } else {
            ingredients = null;
        }
        if (in.readByte() == 0x01) {
            steps = new ArrayList<Step>();
            in.readList(steps, Step.class.getClassLoader());
        } else {
            steps = null;
        }
        servings = in.readByte() == 0x00 ? null : in.readInt();
        imageURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients);
        }
        if (steps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(steps);
        }
        if (servings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(servings);
        }
        dest.writeString(imageURL);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}