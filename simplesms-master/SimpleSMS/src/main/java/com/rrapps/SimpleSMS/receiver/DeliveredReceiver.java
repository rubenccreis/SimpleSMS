package com.rrapps.SimpleSMS.receiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.rrapps.SimpleSMS.R;
import com.rrapps.SimpleSMS.ui.settings.SettingsFragment;

public class DeliveredReceiver extends com.rrapps.SimpleSMS.mmssms.DeliveredReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        switch (getResultCode()) {
            case Activity.RESULT_OK:
                if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SettingsFragment.DELIVERY_TOAST, true)) {
                    Toast.makeText(context, context.getString(R.string.message_delivered) + ": " + intent.getStringExtra("contact_name"), Toast.LENGTH_LONG).show();
                }

                if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SettingsFragment.DELIVERY_VIBRATE, true)) {
                    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                }
                break;

            case Activity.RESULT_CANCELED:
                Toast.makeText(context, R.string.message_not_delivered, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
