package com.rrapps.SimpleSMS.ui.settings;

import android.app.FragmentManager;
import android.os.Bundle;
import com.rrapps.SimpleSMS.R;
import com.rrapps.SimpleSMS.ui.base.SimpleSMSSwipeBackActivity;

public class SettingsActivity extends SimpleSMSSwipeBackActivity {

    private SettingsFragment mSettingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getFragmentManager();
        mSettingsFragment = (SettingsFragment) fm.findFragmentByTag(SettingsFragment.TAG);
        if (mSettingsFragment == null) {
            mSettingsFragment = SettingsFragment.newInstance(R.xml.settings_main);
            fm.beginTransaction()
                    .replace(R.id.content_frame, mSettingsFragment, SettingsFragment.TAG)
                    .commit();
        } else {
            fm.beginTransaction()
                    .show(mSettingsFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
