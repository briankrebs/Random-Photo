package com.brianckrebs.randomphoto.activities.model;

import android.util.Log;

import com.brianckrebs.randomphoto.api.RandomPhotoApi;
import com.brianckrebs.randomphoto.api.RandomPhotoApiService;
import com.brianckrebs.randomphoto.model.Photo;
import com.brianckrebs.randomphoto.model.PhotoGenre;
import com.brianckrebs.randomphoto.utils.BusProvider;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by BrianK on 9/6/2014.
 */
public class MainModel extends BaseModel {

    private static final String TAG = MainModel.class.getCanonicalName();

    private RandomPhotoApiService api;
    private PhotoGenre currentGenre;
    private Photo currentPhoto;

    private Callback<Photo> photoCallback = new Callback<Photo>() {

        @Override
        public void success(Photo photo, Response response) {
            currentPhoto = photo;
            BusProvider.getInstance().post(new FetchPhotoSuccessEvent());
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e(TAG, error.getMessage());
            BusProvider.post(new ApiErrorEvent(error));
        }
    };

    public MainModel() {
    }

    public PhotoGenre getCurrentGenre() {
        return currentGenre;
    }

    public void setCurrentGenre(PhotoGenre currentGenre) {
        this.currentGenre = currentGenre;
    }

    public Photo getCurrentPhoto() {
        return currentPhoto;
    }

    public void setCurrentPhoto(Photo currentPhoto) {
        this.currentPhoto = currentPhoto;
    }

    public void fetchPhoto(PhotoGenre photoGenre) {
        api = RandomPhotoApi.getApiService(photoGenre);
        switch(photoGenre) {
            case DOG:
                api.fetchRandomDog(photoCallback);
                break;
//            case BABY_PIG:
//                api.fetchRandomBabyPig(photoCallback);
//                break;
//            case CAT:
//                api.fetchRandomCat(photoCallback);
//                break;
            case KITTEN:
                api.fetchRandomKitten(photoCallback);
                break;
        }
    }

    public class FetchPhotoSuccessEvent{};
}
