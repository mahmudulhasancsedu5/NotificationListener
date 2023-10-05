package com.gamezoneltd.notificationlistener;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;

//import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class NotificationInfo {
//    @Exclude
    public String TAG = this.getClass().getSimpleName();
    public String packageName;
    public String title;
    public String text;
    public String subText;
    public String infoText;
    public String iconPath;

    public NotificationInfo(){

    }
    public NotificationInfo(StatusBarNotification sbn){
        this.packageName = sbn.getPackageName();
        Notification notification = sbn.getNotification();
        if(notification != null) {
            Bundle extras = notification.extras;
            this.title = extras.getString("android.title");
            this.text = extras.getString("android.text");
            this.subText = extras.getString("android.subText");
            this.infoText = extras.getString("android.infoText");
        } else {
            Log.i(TAG,  " Notification is null");
        }

    }

//    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("packageName", packageName);
        result.put("title", title);
        result.put("text", text);
        result.put("subText", subText);
        result.put("infoText", infoText);

        return result;
    }


}
