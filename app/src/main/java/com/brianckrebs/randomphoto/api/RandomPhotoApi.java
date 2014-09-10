package com.brianckrebs.randomphoto.api;

import com.brianckrebs.randomphoto.model.PhotoGenre;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by BrianK on 9/6/2014.
 */
public final class RandomPhotoApi {

    private static RandomPhotoApiService apiService;

    public static RandomPhotoApiService getApiService(PhotoGenre photoGenre) {
        if(apiService == null) {
            apiService = new RestAdapter.Builder()
                    .setEndpoint(photoGenre.getEndpoint())
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("X-Mashape-Key", "uyz2hG9Z9YmshAjiYplryKzsqnpwp1cdfsWjsn0goNZNe3Erdn");
                        }
                    })
                    .build()
                    .create(RandomPhotoApiService.class);
        }
        return apiService;
    }

    public static void setApiService(RandomPhotoApiService apiService) {
        RandomPhotoApi.apiService = apiService;
    }
}
