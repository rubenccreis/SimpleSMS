package com.rrapps.SimpleSMS.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.rrapps.SimpleSMS.R;
import com.rrapps.SimpleSMS.transaction.NotificationManager;

public class MessageFailedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, R.string.toast_message_failure, Toast.LENGTH_LONG).show();
        NotificationManager.notifyFailed(context);
    }
}
