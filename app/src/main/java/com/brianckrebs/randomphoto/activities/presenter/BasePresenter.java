package com.brianckrebs.randomphoto.activities.presenter;

import com.brianckrebs.randomphoto.activities.BaseActivity;
import com.brianckrebs.randomphoto.activities.model.BaseModel;

/**
 * Created by BrianK on 9/6/2014.
 */
public abstract class BasePresenter {

    protected BaseModel model;
    protected BaseActivity activity;

    public BasePresenter(BaseActivity activity, BaseModel model) {
        this.activity = activity;
        this.model = model;
    }

    public abstract void start();

}
