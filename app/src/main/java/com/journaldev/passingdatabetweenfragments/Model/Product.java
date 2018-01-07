package com.journaldev.passingdatabetweenfragments.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Product implements Parcelable {

    private String id;
    private String name;
    private String model;
    private List<Price> price;
    private String ostatok;
    private String mPrice;
    private String choosedCount;
    private String type;

    public Product(String id, String name, String model, List<Price> price, String ostatok, String mPrice, String choosedCount, String type) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.price = new ArrayList<>();
        this.ostatok = ostatok;
        this.mPrice = mPrice;
        this.choosedCount = choosedCount;
        this.type = type;
    }

    public Product(String id, String name, String model, List<Price> price, String ostatok, String mPrice, String choosedCount) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.price = new ArrayList<>();
        this.ostatok = ostatok;
        this.mPrice = mPrice;
        this.choosedCount = choosedCount;
    }

    public Product(String id, String name, String model, String ostatok, String mPrice, String choosedCount) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.ostatok = ostatok;
        this.mPrice = mPrice;
        this.choosedCount = choosedCount;
    }

    public Product() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChoosedCount() {
        return choosedCount;
    }

    public void setChoosedCount(String choosedCount) {
        this.choosedCount = choosedCount;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Price> getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price.add(price);
    }
    public void getNumPrice(String price) {

    }

    public String getOstatok() {
        return ostatok;
    }

    public void setOstatok(String ostatok) {
        this.ostatok = ostatok;
    }

    @Override
    public String toString() {
        return id + ":" + name + "\n" + model + "\n" + ostatok;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(model);
        parcel.writeString(ostatok);
        parcel.writeList(price);
        parcel.writeString(mPrice);
    }
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        // распаковываем объект из Parcel
        @Override
        public Product createFromParcel(Parcel parcel) {
            return new Product(parcel);
        }
        @Override
        public Product[] newArray(int i) {
            return new Product[i];
        }
    };
    // конструктор, считывающий данные из Parcel
    private Product(Parcel parcel) {
        id = parcel.readString();
        name = parcel.readString();
        model = parcel.readString();
        ostatok = parcel.readString();
        parcel.readList(price, Price.class.getClassLoader());
        mPrice = parcel.readString();
    }
}
