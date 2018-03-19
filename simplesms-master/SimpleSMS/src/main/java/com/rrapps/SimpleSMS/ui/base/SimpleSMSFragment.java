package com.rrapps.SimpleSMS.ui.base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import com.rrapps.SimpleSMS.SimpleSMSApp;
import com.rrapps.SimpleSMS.common.LiveViewManager;
import com.rrapps.SimpleSMS.enums.SimpleSMSPreference;
import com.rrapps.SimpleSMS.ui.ThemeManager;
import com.squareup.leakcanary.RefWatcher;
import icepick.Icepick;

public class SimpleSMSFragment extends Fragment {

    protected SimpleSMSActivity mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (SimpleSMSActivity) activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LiveViewManager.registerView(SimpleSMSPreference.BACKGROUND, this, key -> {
            if (getView() != null) {
                getView().setBackgroundColor(ThemeManager.getBackgroundColor());
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = SimpleSMSApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
