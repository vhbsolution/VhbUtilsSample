package br.com.vhbsolution.modules.vhbutils.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import br.com.vhbsolution.modules.vhbutils.R;
import br.com.vhbsolution.modules.vhbutils.interfaces.IVhbBaseActivity;


/**
 * Created by Victor Bitencourt - vtbitencourt on 6/3/2018.
 */
public abstract class VhbBaseActivity extends AppCompatActivity implements IVhbBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeViews();
        super.onCreate(savedInstanceState);

    }

    protected FragmentManager mFragmentManager;
    protected ProgressBar mProgressBar;
    protected View mLayoutView;
    protected View mContainer;


    protected void showSnackbar(String message) {
        Snackbar.make(mContainer,
                message,
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    protected void showToast(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }



    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mLayoutView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        mLayoutView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLayoutView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            }
        });

        mProgressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        mProgressBar.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            }
        });

    }

    public void beforeViews() { }


   abstract protected void initViews();


    @Override
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void performNextActivity(Intent intent, @Nullable Integer requestCode) {
        if (intent != null && requestCode != null) {
            startActivityForResult(intent, requestCode);
            performActivityDefaultAnimationIn();
        } else {
            startActivity(intent);
            performActivityDefaultAnimationIn();
        }
    }

    public void performActivityDefaultAnimationIn() {
        if (!performCustomAnimationIn())
            overridePendingTransition(R.anim.vhbutils_anim_slide_right_in, R.anim.vhbutils_anim_slide_right_out);
    }

    public void performActivityDefaultAnimationOut() {
        if (!performCustomAnimationOut())
            overridePendingTransition(R.anim.vhbutils_anim_slide_right_back_in, R.anim.vhbutils_anim_slide_right_back_out);
    }


    @Override
    public boolean performCustomAnimationIn() {
        return false;
    }

    @Override
    public boolean performCustomAnimationOut() {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        performActivityDefaultAnimationOut();
    }

}