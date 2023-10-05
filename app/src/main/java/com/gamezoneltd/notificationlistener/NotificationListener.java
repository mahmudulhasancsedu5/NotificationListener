package com.gamezoneltd.notificationlistener;

public interface NotificationListener {
    void receiveNotification(NotificationInfo notificationInfo);
    void onListenerStatusChange();
}
