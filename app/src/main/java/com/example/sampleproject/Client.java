package com.example.sampleproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Client implements Parcelable {
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

    public static Creator<Client> getCREATOR() {
        return CREATOR;
    }

    String cid,cNm,uNm,uPs,uSt;
    public Client(String cid, String cNm, String uNm, String uPs, String uSt) {
        this.cid = cid;
        this.cNm = cNm;
        this.uNm = uNm;
        this.uPs = uPs;
        this.uSt = uSt;
    }

    protected Client(Parcel in) {
        cid = in.readString();
        cNm = in.readString();
        uNm = in.readString();
        uPs = in.readString();
        uSt = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

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
