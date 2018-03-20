package com.rrapps.SimpleSMS.ui.search;

import android.view.View;
import com.rrapps.SimpleSMS.R;
import com.rrapps.SimpleSMS.ui.base.ClickyViewHolder;
import com.rrapps.SimpleSMS.ui.base.SimpleSMSActivity;
import com.rrapps.SimpleSMS.ui.view.AvatarView;
import com.rrapps.SimpleSMS.ui.view.SimpleSMSTextView;

public class SearchViewHolder extends ClickyViewHolder<SearchData> {

    protected View root;
    protected AvatarView avatar;
    protected SimpleSMSTextView name;
    protected SimpleSMSTextView date;
    protected SimpleSMSTextView snippet;

    public SearchViewHolder(SimpleSMSActivity context, View view) {
        super(context, view);

        root = view;
        avatar = (AvatarView) view.findViewById(R.id.search_avatar);
        name = (SimpleSMSTextView) view.findViewById(R.id.search_name);
        date = (SimpleSMSTextView) view.findViewById(R.id.search_date);
        snippet = (SimpleSMSTextView) view.findViewById(R.id.search_snippet);
    }
}
