package com.bitlove.fetlife.notification;

import com.bitlove.fetlife.FetLifeApplication;

import org.json.JSONObject;

public class UnknownNotification extends OneSignalNotification {

    public UnknownNotification(String message, String launchUrl, JSONObject additionalData, String id) {
        super(message,launchUrl,additionalData,id);
    }

    @Override
    public void process(FetLifeApplication fetLifeApplication) {
        //Do nothing
    }

    @Override
    public void onClick(FetLifeApplication fetLifeApplication) {

    }
}
