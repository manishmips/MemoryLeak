package com.adp.leakcanary.model.base;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Attributes implements Parcelable {
    @SerializedName("type")
    private String type;

    @SerializedName("Ar")
    private String Ar;

    @SerializedName("En")
    private String En;

    @SerializedName("action")
    private String action;

    @SerializedName("default")
    private boolean defaultHighlight;

    protected Attributes(Parcel in) {
        type = in.readString();
        Ar = in.readString();
        En = in.readString();
        action = in.readString();
        defaultHighlight = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(Ar);
        dest.writeString(En);
        dest.writeString(action);
        dest.writeByte((byte) (defaultHighlight ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
        @Override
        public Attributes createFromParcel(Parcel in) {
            return new Attributes(in);
        }

        @Override
        public Attributes[] newArray(int size) {
            return new Attributes[size];
        }
    };

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAr() {
        return this.Ar;
    }

    public void setAr(String Ar) {
        this.Ar = Ar;
    }

    public String getEn() {
        return this.En;
    }

    public void setEn(String En) {
        this.En = En;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean getDefaultHighlight() {
        return defaultHighlight;
    }

    public void setDefaultHighlight(boolean defaultHighlight) {
        this.defaultHighlight = defaultHighlight;
    }


}
