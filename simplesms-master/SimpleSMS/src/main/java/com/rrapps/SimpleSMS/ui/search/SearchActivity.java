package com.rrapps.SimpleSMS.ui.search;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import com.rrapps.SimpleSMS.R;
import com.rrapps.SimpleSMS.ui.base.SimpleSMSSwipeBackActivity;

public class SearchActivity extends SimpleSMSSwipeBackActivity {

    private SearchFragment mSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_search);

        FragmentManager fm = getFragmentManager();
        mSearchFragment = (SearchFragment) fm.findFragmentByTag(SearchFragment.TAG);
        if (mSearchFragment == null) {
            mSearchFragment = new SearchFragment();
        }

        fm.beginTransaction()
                .replace(R.id.content_frame, mSearchFragment, SearchFragment.TAG)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.search, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
