package com.rrapps.SimpleSMS.ui.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.MultiAutoCompleteTextView;
import com.android.ex.chips.BaseRecipientAdapter;
import com.android.ex.chips.RecipientEditTextView;
import com.rrapps.SimpleSMS.common.FontManager;
import com.rrapps.SimpleSMS.common.LiveViewManager;
import com.rrapps.SimpleSMS.enums.SimpleSMSPreference;
import com.rrapps.SimpleSMS.ui.ThemeManager;
import com.rrapps.SimpleSMS.ui.base.SimpleSMSActivity;
import com.rrapps.SimpleSMS.ui.settings.SettingsFragment;

public class AutoCompleteContactView extends RecipientEditTextView {
    public static final String TAG = "AutoCompleteContactView";

    private SimpleSMSActivity mContext;
    private BaseRecipientAdapter mAdapter;

    public AutoCompleteContactView(Context context) {
        this(context, null);
        if (!isInEditMode()) {
            init(context);
        }
    }

    public AutoCompleteContactView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context);
        }
    }

    private void init(Context context) {
        mContext = (SimpleSMSActivity) context;

        mAdapter = new BaseRecipientAdapter(BaseRecipientAdapter.QUERY_TYPE_PHONE, getContext());

        setThreshold(1);
        setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        setAdapter(mAdapter);
        setOnItemClickListener(this);

        LiveViewManager.registerView(SimpleSMSPreference.FONT_FAMILY, this, key -> {
            setTypeface(FontManager.getFont(mContext));
        });

        LiveViewManager.registerView(SimpleSMSPreference.FONT_WEIGHT, this, key -> {
            setTypeface(FontManager.getFont(mContext));
        });

        LiveViewManager.registerView(SimpleSMSPreference.FONT_SIZE, this, key -> {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, FontManager.getTextSize(mContext, FontManager.TEXT_TYPE_PRIMARY));
        });

        LiveViewManager.registerView(SimpleSMSPreference.BACKGROUND, this, key -> {
            setTextColor(ThemeManager.getTextOnBackgroundPrimary());
            setHintTextColor(ThemeManager.getTextOnBackgroundSecondary());
        });

        LiveViewManager.registerView(SimpleSMSPreference.MOBILE_ONLY, this, key -> {
            if (mAdapter != null) {
                SharedPreferences prefs1 = mContext.getPrefs();
                mAdapter.setShowMobileOnly(prefs1.getBoolean(SettingsFragment.MOBILE_ONLY, false));
            }
        });
    }
}
