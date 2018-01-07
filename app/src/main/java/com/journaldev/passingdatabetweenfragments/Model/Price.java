package com.journaldev.passingdatabetweenfragments.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Евгений on 05.10.2017.
 */

public class Price implements Parcelable {
    private String name;
    private String value;

    public Price(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Price() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "name: " + getName() + ", value: " + getValue();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(value);
    }
    public static final Parcelable.Creator<Price> CREATOR = new Parcelable.Creator<Price>() {
        // распаковываем объект из Parcel
        @Override
        public Price createFromParcel(Parcel parcel) {
            return new Price(parcel);
        }
        @Override
        public Price[] newArray(int i) {
            return new Price[i];
        }
    };
    // конструктор, считывающий данные из Parcel
    private Price(Parcel parcel) {
        name = parcel.readString();
        value = parcel.readString();
    }
}
