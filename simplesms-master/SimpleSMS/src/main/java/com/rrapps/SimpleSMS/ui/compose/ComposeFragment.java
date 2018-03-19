package com.rrapps.SimpleSMS.ui.compose;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.android.ex.chips.recipientchip.DrawableRecipientChip;
import com.rrapps.SimpleSMS.R;
import com.rrapps.SimpleSMS.common.utils.KeyboardUtils;
import com.rrapps.SimpleSMS.common.utils.PhoneNumberUtils;
import com.rrapps.SimpleSMS.interfaces.ActivityLauncher;
import com.rrapps.SimpleSMS.interfaces.RecipientProvider;
import com.rrapps.SimpleSMS.mmssms.Utils;
import com.rrapps.SimpleSMS.ui.base.SimpleSMSFragment;
import com.rrapps.SimpleSMS.ui.messagelist.MessageListActivity;
import com.rrapps.SimpleSMS.ui.view.AutoCompleteContactView;
import com.rrapps.SimpleSMS.ui.view.ComposeView;
import com.rrapps.SimpleSMS.ui.view.StarredContactsView;

public class ComposeFragment extends SimpleSMSFragment implements ActivityLauncher, RecipientProvider,
        ComposeView.OnSendListener, AdapterView.OnItemClickListener {

    public static final String TAG = "ComposeFragment";

    private AutoCompleteContactView mRecipients;
    private ComposeView mComposeView;
    private StarredContactsView mStarredContactsView;

    public ComposeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_compose, container, false);

        mRecipients = view.findViewById(R.id.compose_recipients);
        mRecipients.setOnItemClickListener(this);

        mComposeView = view.findViewById(R.id.compose_view);
        mComposeView.onOpenConversation(null, null);
        mComposeView.setActivityLauncher(this);
        mComposeView.setRecipientProvider(this);
        mComposeView.setOnSendListener(this);
        mComposeView.setLabel("Compose");

        mStarredContactsView = view.findViewById(R.id.starred_contacts);
        mStarredContactsView.setComposeScreenViews(mRecipients, mComposeView);

        new Handler().postDelayed(() -> KeyboardUtils.showAndFocus(mContext, mRecipients), 100);

        return view;
    }

    @Override
    public void onSend(String[] recipients, String body) {
        long threadId = Utils.getOrCreateThreadId(mContext, recipients[0]);
        if (threadId != 0) {
            mContext.finish();
            MessageListActivity.launch(mContext, threadId, -1, null, true);
        } else {
            mContext.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mComposeView != null) {
            mComposeView.saveDraft();
        }
    }

    /**
     * @return the addresses of all the contacts in the AutoCompleteContactsView.
     */
    @Override
    public String[] getRecipientAddresses() {
        DrawableRecipientChip[] chips = mRecipients.getRecipients();
        String[] addresses = new String[chips.length];

        for (int i = 0; i < chips.length; i++) {
            addresses[i] = PhoneNumberUtils.stripSeparators(chips[i].getEntry().getDestination());
        }

        return addresses;
    }

    /**
     * Photo Selection result
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (!mComposeView.onActivityResult(requestCode, resultCode, data)) {
            // Wasn't handled by ComposeView
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mRecipients.onItemClick(parent, view, position, id);
        mStarredContactsView.collapse();
        mComposeView.requestReplyTextFocus();
    }

    public boolean isReplyTextEmpty() {
        if (mComposeView != null) {
            return mComposeView.isReplyTextEmpty();
        }
        return true;
    }
}
