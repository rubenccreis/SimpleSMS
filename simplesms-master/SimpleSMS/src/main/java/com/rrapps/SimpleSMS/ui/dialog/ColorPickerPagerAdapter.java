package com.rrapps.SimpleSMS.ui.dialog;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.rrapps.SimpleSMS.R;
import com.rrapps.SimpleSMS.ui.base.SimpleSMSActivity;

public class ColorPickerPagerAdapter extends PagerAdapter {

    private SimpleSMSActivity mContext;

    public ColorPickerPagerAdapter(SimpleSMSActivity context) {
        mContext = context;
    }

    public Object instantiateItem(ViewGroup collection, int position) {
        int resId = 0;
        switch (position) {
            case 0:
                resId = R.id.palette;
                break;

            case 1:
                resId = R.id.rgb;
                break;
        }

        return collection.findViewById(resId);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int resId = 0;
        switch (position) {
            case 0:
                resId = R.string.title_palette;
                break;

            case 1:
                resId = R.string.title_rgb;
                break;
        }

        return mContext.getString(resId);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }
}
