package com.br.weatherapp.presenter;

import com.br.weatherapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.inlocomedia.android.engagement.InLocoEngagement;
import com.inlocomedia.android.engagement.request.PushProvider;
import com.inlocomedia.android.engagement.PushMessage;
import com.inlocomedia.android.engagement.request.FirebasePushProvider;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Getting the Notification Data
        final Map<String, String> data = remoteMessage.getData();

        if (data != null) {
            // Decoding the notification data HashMap
            final PushMessage pushContent = InLocoEngagement.decodeReceivedMessage(this, data);

            if (pushContent != null) {
                // Presenting the notification
                InLocoEngagement.presentNotification(
                        this, // Context
                        pushContent,  // The notification message hash
                        R.drawable.ic_notification, // The notification icon drawable resource to display on the status bar. Put your own icon here. You can also use R.drawable.ic_notification for testing.
                        1111111  // Optional: The notification identifier
                );
            } else {
                // It's your regular message. Do as you used to do.
            }
        }
    }

    //This method should only be overriden if you are using Firebase Cloud Messaging 17.1.0 or greater. If you are using a lower version, override the onTokenRefresh of the FirebaseInstanceIdService
    @Override
    public void onNewToken(String firebaseToken) {
        if (firebaseToken != null && !firebaseToken.isEmpty()) {
            final PushProvider pushProvider = new FirebasePushProvider.Builder()
                    .setFirebaseToken(firebaseToken)
                    .build();
            InLocoEngagement.setPushProvider(this, pushProvider);
        }
    }
}
