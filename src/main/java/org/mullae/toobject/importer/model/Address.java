package org.mullae.toobject.importer.model;

/**
 * Created by yoe21c on 2014. 1. 17..
 */
public class Address {
    private String postNumber;
    private String fullAddress;

    public Address(String postNumber, String fullAddress) {
        this.postNumber = postNumber;
        this.fullAddress = fullAddress;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
