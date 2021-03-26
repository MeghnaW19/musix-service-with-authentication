package com.stackroute.usertrackservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

public class Image {


    @Id
    private int imageId;

    @JsonProperty("text")
    private String imageUrl;

    @JsonProperty("size")
    private String imageSpec;

    public Image() {
    }

    public Image(int imageId, String imageUrl, String imageSpec) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.imageSpec = imageSpec;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageSpec() {
        return imageSpec;
    }

    public void setImageSpec(String imageSpec) {
        this.imageSpec = imageSpec;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageSpec='" + imageSpec + '\'' +
                '}';
    }
}
