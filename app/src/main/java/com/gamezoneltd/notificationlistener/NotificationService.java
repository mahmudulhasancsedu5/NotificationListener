package com.gamezoneltd.notificationlistener;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationService extends NotificationListenerService {
    private NotificationBinder mBinder = new NotificationBinder();
    private String TAG = this.getClass().getSimpleName();
//    private FirebaseStorageManager firebaseStorageManager;
    Context context;
    static private NotificationListener listener;

    @Override
    public void onCreate() {
        Log.i(TAG," service started");
        super.onCreate();
        context = getApplicationContext();
//        firebaseStorageManager = new FirebaseStorageManager(context);
    }

    @Override
    public IBinder onBind(Intent intent) {
        if(intent.getAction() == "ui")
            return mBinder;
        return super.onBind(intent);
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.i(TAG,"onListenerConnected called");
        if(listener != null) listener.onListenerStatusChange();
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        Log.i(TAG, "onListenerDisconnected called");
        if(listener != null) listener.onListenerStatusChange();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.i(TAG, "onNotificationPosted invoked");
        Log.i(TAG, "notification from : " + sbn.getPackageName());

        String packageName = sbn.getPackageName();
        Bundle extras = sbn.getNotification().extras;
        NotificationInfo notificationInfo = new NotificationInfo(sbn);

//        firebaseStorageManager.writeToDatabase(notificationInfo);

        //send notification information to UI
        if(listener != null) listener.receiveNotification(notificationInfo);
    }

    public void setNotificationListener(NotificationListener listener){
        Log.i(TAG, "setNotificationListener called");
        NotificationService.listener = listener;
    }

    public void removeNotificationListener(){
        Log.i(TAG, "removeNotificationListener: remove notification listener");
        NotificationService.listener = null;
    }

    public class NotificationBinder extends Binder {
        public NotificationService getService() {
            Log.i(TAG, "getService: called");
            return NotificationService.this;
        }

    }
}
