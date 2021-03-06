package com.harmoush.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Harmoush on 1/30/2018.
 */

public class Ingradient implements Parcelable {
    private Float quantity;
    private String measure;
    private String ingradient;

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingradient = ingredient;
    }

    public Float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingradient;
    }

    public Ingradient(){}
    protected Ingradient(Parcel in) {
        quantity = in.readByte() == 0x00 ? null : in.readFloat();
        measure = in.readString();
        ingradient = in.readString();
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
        dest.writeString(ingradient);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingradient> CREATOR = new Parcelable.Creator<Ingradient>() {
        @Override
        public Ingradient createFromParcel(Parcel in) {
            return new Ingradient(in);
        }

        @Override
        public Ingradient[] newArray(int size) {
            return new Ingradient[size];
        }
    };
}