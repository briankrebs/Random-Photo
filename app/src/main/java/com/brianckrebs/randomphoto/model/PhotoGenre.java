package com.brianckrebs.randomphoto.model;

import com.brianckrebs.randomphoto.R;

/**
 * Created by BrianK on 9/6/2014.
 */
public enum PhotoGenre {

    DOG(R.string.title_dog, "https://nijikokun-thedogapi.p.mashape.com"),
//    BABY_PIG(R.string.title_baby_pig, "https://piglets.p.mashape.com"),
//    CAT(R.string.title_cat, "https://nijikokun-random-cats.p.mashape.com"),
    KITTEN(R.string.title_kitten, "https://nijikokun-random-cats.p.mashape.com");

    private int uiCopyResId;
    private String endpoint;

    PhotoGenre(int uiCopyResId, String endpoint) {
        this.uiCopyResId = uiCopyResId;
        this.endpoint = endpoint;
    }

    public int getUiCopyResId() {
        return uiCopyResId;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
