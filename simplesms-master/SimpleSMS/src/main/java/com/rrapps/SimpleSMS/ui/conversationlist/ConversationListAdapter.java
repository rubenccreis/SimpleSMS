package com.rrapps.SimpleSMS.ui.conversationlist;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rrapps.SimpleSMS.R;
import com.rrapps.SimpleSMS.common.ConversationPrefsHelper;
import com.rrapps.SimpleSMS.common.FontManager;
import com.rrapps.SimpleSMS.common.LiveViewManager;
import com.rrapps.SimpleSMS.common.SimpleSMSPreferences;
import com.rrapps.SimpleSMS.common.emoji.EmojiRegistry;
import com.rrapps.SimpleSMS.common.utils.DateFormatter;
import com.rrapps.SimpleSMS.data.Contact;
import com.rrapps.SimpleSMS.data.Conversation;
import com.rrapps.SimpleSMS.enums.SimpleSMSPreference;
import com.rrapps.SimpleSMS.ui.ThemeManager;
import com.rrapps.SimpleSMS.ui.base.SimpleSMSActivity;
import com.rrapps.SimpleSMS.ui.base.RecyclerCursorAdapter;
import com.rrapps.SimpleSMS.ui.settings.SettingsFragment;

public class ConversationListAdapter extends RecyclerCursorAdapter<ConversationListViewHolder, Conversation> {


    private final SharedPreferences mPrefs;

    public ConversationListAdapter(SimpleSMSActivity context) {
        super(context);
        mPrefs = mContext.getPrefs();
    }

    protected Conversation getItem(int position) {
        mCursor.moveToPosition(position);
        return Conversation.from(mContext, mCursor);
    }

    @Override
    public ConversationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_conversation, null);

        ConversationListViewHolder holder = new ConversationListViewHolder(mContext, view);
        holder.mutedView.setImageResource(R.drawable.ic_notifications_muted);
        holder.unreadView.setImageResource(R.drawable.ic_unread_indicator);
        holder.errorIndicator.setImageResource(R.drawable.ic_error);

        LiveViewManager.registerView(SimpleSMSPreference.THEME, this, key -> {
            holder.mutedView.setColorFilter(ThemeManager.getColor());
            holder.unreadView.setColorFilter(ThemeManager.getColor());
            holder.errorIndicator.setColorFilter(ThemeManager.getColor());
        });

        LiveViewManager.registerView(SimpleSMSPreference.BACKGROUND, this, key -> {
            holder.root.setBackgroundDrawable(ThemeManager.getRippleBackground());
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ConversationListViewHolder holder, int position) {
        final Conversation conversation = getItem(position);

        holder.mData = conversation;
        holder.mContext = mContext;
        holder.mClickListener = mItemClickListener;
        holder.root.setOnClickListener(holder);
        holder.root.setOnLongClickListener(holder);

        holder.mutedView.setVisibility(new ConversationPrefsHelper(mContext, conversation.getThreadId())
                .getNotificationsEnabled() ? View.GONE : View.VISIBLE);

        holder.errorIndicator.setVisibility(conversation.hasError() ? View.VISIBLE : View.GONE);

        final boolean hasUnreadMessages = conversation.hasUnreadMessages();
        if (hasUnreadMessages) {
            holder.unreadView.setVisibility(View.VISIBLE);
            holder.snippetView.setTextColor(ThemeManager.getTextOnBackgroundPrimary());
            holder.dateView.setTextColor(ThemeManager.getColor());
            holder.fromView.setType(FontManager.TEXT_TYPE_PRIMARY_BOLD);
            holder.snippetView.setMaxLines(5);
        } else {
            holder.unreadView.setVisibility(View.GONE);
            holder.snippetView.setTextColor(ThemeManager.getTextOnBackgroundSecondary());
            holder.dateView.setTextColor(ThemeManager.getTextOnBackgroundSecondary());
            holder.fromView.setType(FontManager.TEXT_TYPE_PRIMARY);
            holder.snippetView.setMaxLines(1);
        }

        LiveViewManager.registerView(SimpleSMSPreference.THEME, this, key -> {
            holder.dateView.setTextColor(hasUnreadMessages ? ThemeManager.getColor() : ThemeManager.getTextOnBackgroundSecondary());
        });

        if (isInMultiSelectMode()) {
            holder.mSelected.setVisibility(View.VISIBLE);
            if (isSelected(conversation.getThreadId())) {
                holder.mSelected.setImageResource(R.drawable.ic_selected);
                holder.mSelected.setColorFilter(ThemeManager.getColor());
                holder.mSelected.setAlpha(1f);
            } else {
                holder.mSelected.setImageResource(R.drawable.ic_unselected);
                holder.mSelected.setColorFilter(ThemeManager.getTextOnBackgroundSecondary());
                holder.mSelected.setAlpha(0.5f);
            }
        } else {
            holder.mSelected.setVisibility(View.GONE);
        }

        LiveViewManager.registerView(SimpleSMSPreference.HIDE_AVATAR_CONVERSATIONS, this, key -> {
            holder.mAvatarView.setVisibility(SimpleSMSPreferences.getBoolean(SimpleSMSPreference.HIDE_AVATAR_CONVERSATIONS) ? View.GONE : View.VISIBLE);
        });

        // Date
        holder.dateView.setText(DateFormatter.getConversationTimestamp(mContext, conversation.getDate()));

        // Subject
        String emojiSnippet = conversation.getSnippet();
        if (mPrefs.getBoolean(SettingsFragment.AUTO_EMOJI, false)) {
            emojiSnippet = EmojiRegistry.parseEmojis(emojiSnippet);
        }
        holder.snippetView.setText(emojiSnippet);

        Contact.addListener(holder);

        // Update the avatar and name
        holder.onUpdate(conversation.getRecipients().size() == 1 ? conversation.getRecipients().get(0) : null);
    }
}
