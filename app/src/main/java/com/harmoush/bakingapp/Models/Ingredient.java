package com.harmoush.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Harmoush on 1/30/2018.
 */

public class Ingredient implements Parcelable {
    private Float quantity;
    private String measure;
    private String ingredient;

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public Float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public Ingredient(){}
    protected Ingredient(Parcel in) {
        quantity = in.readByte() == 0x00 ? null : in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (quantity == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(quantity);
        }
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}