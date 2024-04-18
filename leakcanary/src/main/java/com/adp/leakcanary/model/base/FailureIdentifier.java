package com.adp.leakcanary.model.base;

import com.google.gson.annotations.SerializedName;

public class FailureIdentifier {

    @SerializedName("name")
    private String name;

    @SerializedName("value")
    private String value;

    @SerializedName("typeOf")
    private String typeOf;

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

    public String getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(String typeOf) {
        this.typeOf = typeOf;
    }
}
