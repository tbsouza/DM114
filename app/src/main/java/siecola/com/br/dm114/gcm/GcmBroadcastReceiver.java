package siecola.com.br.dm114.gcm;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import siecola.com.br.dm114.MainActivity;
import siecola.com.br.dm114.R;
import siecola.com.br.dm114.models.OrderInfo;

public class GcmBroadcastReceiver extends BroadcastReceiver {

    private Context context;
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;



    @Override
    public void onReceive(Context context, Intent intent) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        this.context = context;
        String messageType = gcm.getMessageType(intent);

        if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            Bundle extras = intent.getExtras();
            Gson gson = new Gson();

            if (extras.containsKey("orderInfo")) {
                String strOrderInfo = extras.getString("orderInfo");
                if (strOrderInfo != null) {
                    OrderInfo orderInfo = gson.fromJson(strOrderInfo, OrderInfo.class);
                    sendNotification(orderInfo);
                }
            }
        }

        setResultCode(Activity.RESULT_OK);
    }


    private void sendNotification(OrderInfo orderInfo) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("orderInfo", orderInfo);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder mBuilder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_event_available_white_24dp)
                .setAutoCancel(true)
                .setContentTitle("DM114 - Vendas")
                .setStyle(new Notification.BigTextStyle().bigText("Pedido:" +
                        orderInfo.getId() + " - " + orderInfo.getStatus()))
                .setContentText("Pedido:" + orderInfo.getId() + " - " + orderInfo.getStatus());

        mBuilder.setContentIntent(contentIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder.setChannelId("1");
        }

        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }



}
