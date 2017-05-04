package cn.xudaodao.android.adview.siderview;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 *
 */
public abstract class BaseSliderView {
    protected Context mContext;
    private Bundle mBundle;


    protected BaseSliderView(Context context) {
        mContext = context;
        mBundle = new Bundle();
    }

    public BaseSliderView setBundle(Bundle bundle) {
        mBundle = bundle;
        return this;
    }


    public Context getContext() {
        return mContext;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public abstract View getView();
}
