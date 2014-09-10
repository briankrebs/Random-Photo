package com.brianckrebs.randomphoto.api;

import com.brianckrebs.randomphoto.model.Photo;
import com.brianckrebs.randomphoto.model.PhotoGenre;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by BrianK on 9/6/2014.
 */
public interface RandomPhotoApiService {

    @GET("/random")
    void fetchRandomDog(Callback<Photo> cb);

    @GET("/random")
    void fetchRandomBabyPig(Callback<Photo> cb);

    @GET("/random/kitten")
    void fetchRandomKitten(Callback<Photo> cb);

    @GET("/random/cat")
    void fetchRandomCat(Callback<Photo> cb);

}
