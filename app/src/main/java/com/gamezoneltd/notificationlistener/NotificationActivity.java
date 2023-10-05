package com.gamezoneltd.notificationlistener;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity implements NotificationListener{
    private String TAG = this.getClass().getSimpleName();
    private NotificationService notificationService;
    private TextView textView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.notificationText);
        notificationService = new NotificationService();
        notificationService.setNotificationListener(this);

//        Intent showPermissionIntent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
//        startActivity(showPermissionIntent);
        getNotificationAccessPermission();
//        testReadFirebase();

    }

//    private void testWriteFirebase(){
//        FirebaseStorageManager db = new FirebaseStorageManager(context);
//        db.writeTest();
//
//    }
//
//    private void testReadFirebase() {
//        FirebaseStorageManager db = new FirebaseStorageManager(context);
//        db.readTest();
//    }

    @Override
    public void receiveNotification(NotificationInfo notificationInfo) {
        Log.i(TAG, notificationInfo.packageName+ ": Notification received");
        textView.append(notificationInfo.packageName
                + "\n title: " + notificationInfo.title
                + "\n text: " + notificationInfo.text + "\n info: " + notificationInfo.infoText);
    }

    @Override
    public void onListenerStatusChange() {

    }

    private void getNotificationAccessPermission(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager.isNotificationPolicyAccessGranted()) {
            Log.i(TAG, "notification_access granted");
        } else {
            Log.i(TAG, "Request for notification access permission");
            Intent showPermissionIntent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(showPermissionIntent);
        }
    }
}