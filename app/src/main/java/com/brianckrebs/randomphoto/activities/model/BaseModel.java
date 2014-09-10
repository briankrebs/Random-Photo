package com.brianckrebs.randomphoto.activities.model;

import retrofit.RetrofitError;

/**
 * Created by BrianK on 9/6/2014.
 */
public abstract class BaseModel {

    //events
    public static class ApiErrorEvent {

        private RetrofitError retrofitError;

        public ApiErrorEvent(RetrofitError retrofitError) {
            this.retrofitError = retrofitError;
        }

        public RetrofitError getRetrofitError() {
            return retrofitError;
        }
    }
}
