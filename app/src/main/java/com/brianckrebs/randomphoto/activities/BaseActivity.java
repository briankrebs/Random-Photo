package com.brianckrebs.randomphoto.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;

import com.brianckrebs.randomphoto.R;
import com.brianckrebs.randomphoto.activities.presenter.BasePresenter;
import com.brianckrebs.randomphoto.utils.BusProvider;

/**
 * Created by BrianK on 9/6/2014.
 */
public abstract class BaseActivity extends FragmentActivity {

    protected BasePresenter presenter;
    protected ProgressDialog loadingDialog;

    @Override
    protected void onPause() {
        super.onPause();
        if(presenter!=null) {
            BusProvider.unregister(presenter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(presenter!=null) {
            BusProvider.register(presenter);
        }
    }

    public void showLoadingDialog() {
        if(loadingDialog==null) {
            loadingDialog = ProgressDialog.show(this, "", getString(R.string.loading), true, false);
        }
        else {
            if(!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }
    }

    public void hideLoadingDialog() {
        if(loadingDialog!=null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public void showAlertDialog(String title, String message, String positiveBtnCopy, final Class positiveEventType) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveBtnCopy, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(positiveEventType!=null) {
                            try {
                                BusProvider.post(positiveEventType.newInstance());
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .show();
    }

}
