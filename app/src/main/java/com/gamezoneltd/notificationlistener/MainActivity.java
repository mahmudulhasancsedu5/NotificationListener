package com.gamezoneltd.notificationlistener;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements NotificationListener{
    private String TAG = this.getClass().getSimpleName();
    private NotificationService notificationService;
    private NotificationServiceConnection notificationServiceConnection;
    private TextView textView;
    private Context context;
    private Button notificationAccessBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.notificationText);
        notificationAccessBtn = findViewById(R.id.notiAccessBtn);
//        notificationService = new NotificationService();
//        notificationService.setNotificationListener(this);

        notificationAccessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "NotificationAccess: button onClick() called");
                getNotificationAccessPermission();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: called");
        Intent serviceIntent = new Intent(this, NotificationService.class);
        serviceIntent.setAction("ui");
        notificationServiceConnection = new NotificationServiceConnection();
//        bindService(serviceIntent, notificationServiceConnection, BIND_AUTO_CREATE);
        boolean serviceStatus = bindService(serviceIntent,notificationServiceConnection, BIND_AUTO_CREATE);
        Log.i(TAG, "service status: " + serviceStatus);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onStart: called");

        if(notificationService != null) {
            notificationService.setNotificationListener(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(notificationServiceConnection);
    }

    @Override
    public void receiveNotification(NotificationInfo notificationInfo) {
        Log.i(TAG, notificationInfo.packageName+ ": Notification received");
        textView.append("\nNotification Message from: " + notificationInfo.packageName
                + "\ntitle: " + notificationInfo.title
                + "\ntext: " + notificationInfo.text + "\n info: " + notificationInfo.infoText);
    }

    @Override
    public void onListenerStatusChange() {

    }

    private void getNotificationAccessPermission(){
        Log.i(TAG,"getNotificationAccessPermission: called");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        ComponentName notificationListenerComp = new ComponentName(MainActivity.this, NotificationService.class);
        boolean notificationAccessGranted = notificationManager.isNotificationListenerAccessGranted(notificationListenerComp);

        if(notificationAccessGranted) {
            Log.i(TAG, "notification_access already granted");
        } else {
            Log.i(TAG, "Request for notification access permission");
            Intent showPermissionIntent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(showPermissionIntent);
        }
    }

    private class NotificationServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            notificationService = ((NotificationService.NotificationBinder)iBinder).getService();
            notificationService.setNotificationListener(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            notificationService = null;
        }
    }
}