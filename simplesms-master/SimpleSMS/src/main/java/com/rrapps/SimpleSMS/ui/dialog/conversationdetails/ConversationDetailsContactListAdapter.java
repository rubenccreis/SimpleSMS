package com.rrapps.SimpleSMS.ui.dialog.conversationdetails;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.rrapps.SimpleSMS.R;
import com.rrapps.SimpleSMS.data.Contact;
import com.rrapps.SimpleSMS.data.ContactList;
import com.rrapps.SimpleSMS.ui.view.AvatarView;
import com.rrapps.SimpleSMS.ui.view.SimpleSMSTextView;

public class ConversationDetailsContactListAdapter extends ArrayAdapter {

    private ContactList mContacts;

    public ConversationDetailsContactListAdapter(Context context, ContactList contacts) {
        super(context, R.layout.list_item_recipient);
        mContacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.list_item_recipient, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = mContacts.get(position);

        holder.name.setText(contact.getName());
        holder.address.setText(contact.getNumber());
        holder.avatar.setImageDrawable(contact.getAvatar(getContext(), null));
        holder.avatar.setContactName(contact.getName());
        if (contact.existsInDatabase()) {
            holder.avatar.assignContactUri(contact.getUri());
        } else {
            holder.avatar.assignContactFromPhone(contact.getNumber(), true);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    static class ViewHolder {
        AvatarView avatar;
        SimpleSMSTextView name;
        SimpleSMSTextView address;

        public ViewHolder(View view) {
            avatar = (AvatarView) view.findViewById(R.id.avatar);
            name = (SimpleSMSTextView) view.findViewById(R.id.name);
            address = (SimpleSMSTextView) view.findViewById(R.id.address);
        }
    }
}
