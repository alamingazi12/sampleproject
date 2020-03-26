package com.example.sampleproject;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    String cid;
    String cNm;

    public User(String cid, String cNm, String uNm, String uPs, String uSt) {
        this.cid = cid;
        this.cNm = cNm;
        this.uNm = uNm;
        this.uPs = uPs;
        this.uSt = uSt;
    }

    String uNm;
    String uPs;

    protected User(Parcel in) {
        cid = in.readString();
        cNm = in.readString();
        uNm = in.readString();
        uPs = in.readString();
        uSt = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getCid() {
        return cid;
    }

    public String getcNm() {
        return cNm;
    }

    public String getuNm() {
        return uNm;
    }

    public String getuPs() {
        return uPs;
    }

    public String getuSt() {
        return uSt;
    }

    String uSt;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cid);
        parcel.writeString(cNm);
        parcel.writeString(uNm);
        parcel.writeString(uPs);
        parcel.writeString(uSt);
    }
}
