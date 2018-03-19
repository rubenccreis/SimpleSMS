package com.rrapps.SimpleSMS.service;

import com.rrapps.SimpleSMS.mmssms.Message;
import com.rrapps.SimpleSMS.mmssms.Transaction;
import com.rrapps.SimpleSMS.data.ConversationLegacy;
import com.rrapps.SimpleSMS.transaction.NotificationManager;
import com.rrapps.SimpleSMS.transaction.SmsHelper;
import com.pushbullet.android.extension.MessagingExtension;
import com.rrapps.SimpleSMS.ui.popup.SimpleSMSReplyActivity;

public class PushbulletService extends MessagingExtension {
    private final String TAG = "PushbulletService";

    @Override
    protected void onMessageReceived(String conversationIden, String body) {
        long threadId = Long.parseLong(conversationIden);
        ConversationLegacy conversation = new ConversationLegacy(getApplicationContext(), threadId);

        Transaction sendTransaction = new Transaction(getApplicationContext(), SmsHelper.getSendSettings(getApplicationContext()));
        Message message = new com.rrapps.SimpleSMS.mmssms.Message(body, conversation.getAddress());
        message.setType(com.rrapps.SimpleSMS.mmssms.Message.TYPE_SMSMMS);
        sendTransaction.sendNewMessage(message, conversation.getThreadId());

        SimpleSMSReplyActivity.dismiss(conversation.getThreadId());

        NotificationManager.update(getApplicationContext());
    }

    @Override
    protected void onConversationDismissed(String conversationIden) {
        long threadId = Long.parseLong(conversationIden);
        ConversationLegacy conversation = new ConversationLegacy(getApplicationContext(), threadId);
        conversation.markRead();
    }

}
