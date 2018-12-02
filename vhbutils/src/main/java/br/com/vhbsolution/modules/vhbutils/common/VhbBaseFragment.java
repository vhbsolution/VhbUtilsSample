package br.com.vhbsolution.modules.vhbutils.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Created by Victor Bitencourt - vtbitencourt on 7/1/2018.
 */
public abstract class VhbBaseFragment extends Fragment {

    protected FragmentManager mFragmentManager;

    abstract protected void initViews(View view);

    abstract protected void performFragmentTransition();


}