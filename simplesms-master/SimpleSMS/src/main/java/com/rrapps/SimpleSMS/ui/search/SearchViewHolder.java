package com.rrapps.SimpleSMS.ui.search;

import android.view.View;
import com.rrapps.SimpleSMS.R;
import com.rrapps.SimpleSMS.ui.base.ClickyViewHolder;
import com.rrapps.SimpleSMS.ui.base.SimpleSMSActivity;
import com.rrapps.SimpleSMS.ui.view.AvatarView;
import com.rrapps.SimpleSMS.ui.view.QKTextView;

public class SearchViewHolder extends ClickyViewHolder<SearchData> {

    protected View root;
    protected AvatarView avatar;
    protected QKTextView name;
    protected QKTextView date;
    protected QKTextView snippet;

    public SearchViewHolder(SimpleSMSActivity context, View view) {
        super(context, view);

        root = view;
        avatar = (AvatarView) view.findViewById(R.id.search_avatar);
        name = (QKTextView) view.findViewById(R.id.search_name);
        date = (QKTextView) view.findViewById(R.id.search_date);
        snippet = (QKTextView) view.findViewById(R.id.search_snippet);
    }
}
