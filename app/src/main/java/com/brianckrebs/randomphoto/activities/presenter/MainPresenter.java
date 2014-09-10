package com.brianckrebs.randomphoto.activities.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.brianckrebs.randomphoto.R;
import com.brianckrebs.randomphoto.activities.BaseActivity;
import com.brianckrebs.randomphoto.activities.MainActivity;
import com.brianckrebs.randomphoto.activities.model.BaseModel;
import com.brianckrebs.randomphoto.activities.model.MainModel;
import com.brianckrebs.randomphoto.adapters.NavListAdapter;
import com.brianckrebs.randomphoto.api.RandomPhotoApi;
import com.brianckrebs.randomphoto.model.Photo;
import com.brianckrebs.randomphoto.model.PhotoGenre;
import com.brianckrebs.randomphoto.utils.ImageHelper;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by BrianK on 9/6/2014.
 */
public class MainPresenter extends BasePresenter {

    public MainPresenter(BaseActivity activity, BaseModel model) {
        super(activity, model);
    }

    private MainActivity getActivity() {
        return (MainActivity)activity;
    }

    private MainModel getModel() {
        return (MainModel)model;
    }

    @Override
    public void start() {
        //init nav
        ArrayList<PhotoGenre> navItems = new ArrayList<PhotoGenre>();
        for(PhotoGenre item : PhotoGenre.values()) {
            navItems.add(item);
        }
        //set adapter for nav
        NavListAdapter adapter = new NavListAdapter(activity.getApplicationContext(), navItems);
        getActivity().getNav().setAdapter(adapter);
        getActivity().getNav().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectNavItem(position);
            }
        });

        selectNavItem(0);
    }

    private void selectNavItem(int position) {
        getActivity().getNav().setItemChecked(position, true);
        getActivity().getDrawerLayout().closeDrawer(getActivity().getNav());
        PhotoGenre genre = PhotoGenre.values()[position];
        getModel().setCurrentGenre(genre);
        //reset the api since the endpoint has changed
        RandomPhotoApi.setApiService(null);
        fetchNewPhoto();
    }


    private void fetchNewPhoto() {
        clearPhoto();
        activity.showLoadingDialog();
        getModel().fetchPhoto(getModel().getCurrentGenre());
    }

    private void clearPhoto() {
        //view
        ImageView mainImage = getActivity().getMainImage();
        if(mainImage!=null) {
            mainImage.setImageDrawable(null);
        }

        //model
        getModel().setCurrentPhoto(null);
    }

    private void updatePhoto(Photo photo) {
        ImageView mainImage = ((MainActivity)activity).getMainImage();
        if(photo!=null && mainImage!=null) {
            Picasso.with(activity.getBaseContext())
                    .load(photo.source)
                    .into(mainImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            //hide loading dialog
                            activity.hideLoadingDialog();
                        }

                        @Override
                        public void onError() {
                            activity.hideLoadingDialog();
                            showUnableToDisplayImageError();
                        }
                    });
        }
        else {
            activity.hideLoadingDialog();
            showUnableToDisplayImageError();
        }
    }

    private void showUnableToDisplayImageError() {
        activity.showAlertDialog(activity.getString(R.string.error_display_image_title), activity.getString(R.string.error_display_image_message), activity.getString(R.string.ok), UnableToDisplayImageClickEvent.class);
    }

    public void onNextClick() {
        fetchNewPhoto();
    }

    public void onShareClick() {
        activity.showLoadingDialog();

        //need to do this in async task as getting the bitmap uri involves saving the photo locally to the phone
        new AsyncTask<ImageView, Void, Uri>() {
            @Override
            protected Uri doInBackground(ImageView... imageViews) {
                Uri bmpUri = ImageHelper.getLocalBitmapUri(imageViews[0]);
                return bmpUri;
            }

            @Override
            protected void onPostExecute(Uri uri) {
                super.onPostExecute(uri);
                onShareImageReady(uri);
            }
        }.execute(((MainActivity)activity).getMainImage());
    }

    private void onShareImageReady(Uri uri) {
        activity.hideLoadingDialog();

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        if(uri!=null) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
        }
        activity.startActivity(Intent.createChooser(shareIntent, activity.getResources().getText(R.string.share_send_to)));
    }

    @Subscribe
    public void onFetchPhotoSuccess(MainModel.FetchPhotoSuccessEvent event) {
        Photo photo = ((MainModel)model).getCurrentPhoto();
        updatePhoto(photo);
    }

    @Subscribe
    public void onUnableToDisplayImageErrorClick(UnableToDisplayImageClickEvent event) {
        fetchNewPhoto();
    }

    @Subscribe
    public void onApiErrorEvent(BaseModel.ApiErrorEvent event) {
        activity.hideLoadingDialog();
        //FIXME - this is not user friendly error messaging
        activity.showAlertDialog("", event.getRetrofitError().getMessage(), activity.getString(R.string.ok), null);
    }

    //events
    public static class UnableToDisplayImageClickEvent {
        public UnableToDisplayImageClickEvent() {
        }
    };
}
