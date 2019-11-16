package com.example.expo.blogapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemInfo implements Parcelable {

    private String mTotalPrice;
    private String mCurrencyCode;

    public ItemInfo(double totalPrice, String currencyCode) {
        mTotalPrice = Double.toString(totalPrice);
        mCurrencyCode = currencyCode;
    }

    private ItemInfo(Parcel parcel) {
        mTotalPrice = parcel.readString();
        mCurrencyCode = parcel.readString();
    }

    public String getTotalPrice() {
        return mTotalPrice;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTotalPrice);
        dest.writeString(mCurrencyCode);
    }

    public static final Creator<ItemInfo> CREATOR = new Creator<ItemInfo>() {
        @Override
        public ItemInfo createFromParcel(Parcel parcel) {
            return new ItemInfo(parcel);
        }

        @Override
        public ItemInfo[] newArray(int size) {
            return new ItemInfo[size];
        }
    };

}
