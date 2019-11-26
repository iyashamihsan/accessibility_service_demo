package com.example.accessibility.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.accessibility.support.AccessAction;

import java.util.List;

public class AppAccessibilityService extends AccessibilityService {

    private final AccessibilityServiceInfo info = new AccessibilityServiceInfo();
    private static final String TAG = "MyAccessibilityService";
    private static final String TAGEVENTS = "TAGEVENTS";
    private String currntApplicationPackage = "";

    private String pkgName;
    private List<AccessAction> accessActionList;

    //private WindowPositionController windowController;
    private WindowManager windowManager;
    private boolean showWindow = false;

    //Getting events
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

        Log.d(TAG, "onAccessibilityEvent");

        AccessibilityNodeInfo node = getRootInActiveWindow();
        if(node != null) {
            for(int i = 0; i < node.getChildCount(); i++){
                AccessibilityNodeInfo childNode = node.getChild(i);
                if(childNode != null && childNode.getText() != null && childNode.getText().toString().equals("CLAIM NOW")){
                    Log.i("JZW", "-----getText->"+childNode.getText()+"---getContentDescription-->"+childNode.getContentDescription() );
                }
            }
        }

        final String sourcePackageName = (String) accessibilityEvent.getPackageName();
        currntApplicationPackage = sourcePackageName;
        Log.d(TAG, "sourcePackageName:" + sourcePackageName);
        Log.d(TAG, "parcelable:" + accessibilityEvent.getText().toString());

        Log.d(TAG,""+ accessibilityEvent.getSource());

        final int eventType = accessibilityEvent.getEventType();
        String eventText = null;
        switch(eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "Clicked: ";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "Focused: ";
                break;
        }

        eventText = eventText + accessibilityEvent.getContentDescription();

        Log.d("EVENTEXT", "event :"+ eventText);


        logViewHierarchy(accessibilityEvent.getSource(),0);

        int i = accessibilityEvent.getEventType();

        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED){

            Log.d(TAG, "onAccessibilityEvent: type_view_clicked");
        }

        //windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

//        Log.d("RSC", "count: " + accessibilityEvent.getSource().getViewIdResourceName());
        /*if (accessibilityEvent.getPackageName() == null || !(accessibilityEvent.getPackageName().equals("com.bsb.hike") || !(accessibilityEvent.getPackageName().equals("com.whatsapp") || accessibilityEvent.getPackageName().equals("com.facebook.orca") || accessibilityEvent.getPackageName().equals("com.twitter.android") || accessibilityEvent.getPackageName().equals("com.facebook.katana") || accessibilityEvent.getPackageName().equals("com.facebook.lite"))))
            showWindow = false;*/

        //logViewHierarchy(accessibilityEvent.get,0);

        if (accessibilityEvent != null && accessibilityEvent.getSource() != null && accessibilityEvent.getSource().getChildCount() != 0) {
            int count = accessibilityEvent.getSource().getChildCount();

            Log.d(TAG, "count: " + count);


            Log.d(TAG, "count: " + accessibilityEvent.getSource().getViewIdResourceName());
            Log.d(TAG, "count: " + count);

        }
       /* if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            Log.d(TAGEVENTS, "TYPE_VIEW_TEXT_CHANGED");
            if (windowController == null)
                windowController = new WindowPositionController(windowManager, getApplicationContext());
            showWindow = true;
            windowController.notifyDatasetChanged(accessibilityEvent.getText().toString(), currntApplicationPackage);
        } else if(accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            Log.d(TAGEVENTS, "TYPE_WINDOW_STATE_CHANGED:"+accessibilityEvent.getContentDescription());

            if (accessibilityEvent.getPackageName().equals("com.whatsapp") && (accessibilityEvent.getContentDescription() == null || !accessibilityEvent.getContentDescription().equals("Type a message")))
                showWindow = false;
            if (accessibilityEvent.getPackageName().equals("com.facebook.katana") && (accessibilityEvent.getText().toString().equals("[What's on your mind?]") || accessibilityEvent.getText().toString().equals("[Search]")))
                showWindow = false;
            if (accessibilityEvent.getPackageName().equals("com.twitter.android") && (accessibilityEvent.getText().toString().equals("[What\u2019s happening?]") || accessibilityEvent.getText().toString().equals("[Search Twitter]")))
                showWindow = false;
            if (accessibilityEvent.getContentDescription()!=null && (accessibilityEvent.getContentDescription().toString().equals("Textbox in chat thread")))
                showWindow = true;*/


            //remove window when keyboard closed or user moved from chatting to other things
            /*if (windowController != null && !showWindow)

               windowController.onDestroy();*/

    }
    //interrupt service
    @Override
    public void onInterrupt() {

    }
    // to register for which accessibilty service you need to register this service for
    @Override
    public void onServiceConnected() {

        // Set the type of events that this service wants to listen to.
        //Others won't be passed to this service.
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;

        this.setServiceInfo(info);

        Log.d("JAZZ","service connected");
    }

    public static void logViewHierarchy(AccessibilityNodeInfo nodeInfo, final int depth) {

        if (nodeInfo == null) return;

        List<AccessibilityNodeInfo> nodeInfo1 = nodeInfo.findAccessibilityNodeInfosByViewId("menu_ic_buysim_dailyreward");
        List<AccessibilityNodeInfo> nodeInfo2 = nodeInfo.findAccessibilityNodeInfosByViewId("menu_lbl_buysim_dailyreward");
        //List<AccessibilityNodeInfo> nodeInfo3 = nodeInfo.findAccessibilityNodeInfosByViewId("search_holder");

        for (AccessibilityNodeInfo node: nodeInfo1){

            Log.d(TAG, "Node1: "+ node.getViewIdResourceName() + " " + node.getContentDescription());
        }

        for (AccessibilityNodeInfo node: nodeInfo2){

            Log.d(TAG, "Node2: "+ node.getViewIdResourceName() + " " + node.getContentDescription());
        }

        /*for (AccessibilityNodeInfo node: nodeInfo3){

            Log.d("FIND", "Node3: "+ node.getViewIdResourceName() + " " + node.getContentDescription());
        }*/


        String spacerString = "";

        for (int i = 0; i < depth; ++i) {
            spacerString += '-';
        }
        //Log the info you care about here... I choose classname and view resource name, because they are simple, but interesting.
        Log.d("TAG", spacerString + nodeInfo.getClassName() + " " + nodeInfo.getViewIdResourceName() + " " +nodeInfo.getContentDescription());

        if (nodeInfo.getParent() != null) {

            Log.d("TAG", spacerString + nodeInfo.getClassName() + " " + nodeInfo.getViewIdResourceName());

            if (nodeInfo.getParent().getChildCount() == 30){

                if (nodeInfo.getParent().getChild(29).getChildCount() > 0){

                    if (nodeInfo.getParent().getChild(29).getChild(0).getText().toString().equals("Daily Rewards")){
                        nodeInfo.getParent().getChild(29).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }

            }
            else if (nodeInfo.getText() != null && nodeInfo.getText().toString().contains("Day")){

                nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Log.d("DAY","<-- node parent child count: "+ nodeInfo.getParent().getChildCount()  +"-->"+nodeInfo.getText().toString());
            }
            else if (nodeInfo.getText() != null && nodeInfo.getText().toString().equals("CLAIM NOW")) {

                //<string name="claim_it_now">Claim it Now!</string>
                //    <string name="claim_now">Claim Now</string>

                Log.d("CLAIM",nodeInfo.getText().toString());
            }


            Log.d(TAG, "logViewHierarchy: after click Image");

        }


            final AccessibilityNodeInfo s = nodeInfo;
            new Handler().postDelayed(new Runnable() {
                List<AccessibilityNodeInfo> list = s.findAccessibilityNodeInfosByViewId("menu_ic_buysim_dailyreward");
                @Override
                public void run() {
                    for (final AccessibilityNodeInfo view : list) {
                        Log.d(TAG, "run: view"+view.getContentDescription());
                        view.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }, 500);

            for (int i = 0; i < nodeInfo.getChildCount(); ++i) {
                logViewHierarchy(nodeInfo.getChild(i), depth + 1);
            }
    }

    /**
     * Check if Accessibility Service is enabled.
     *
     * @param mContext
     * @return <code>true</code> if Accessibility Service is ON, otherwise <code>false</code>
     */
    public static boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        //your package /   accesibility service path/class
        final String service = "com.my.newproject3/com.my.newproject3.services.AppAccessibilityService";

        boolean accessibilityFound = false;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILIY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();

                    Log.v(TAG, "-------------- > accessabilityService :: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILIY IS DISABLED***");
        }

        return accessibilityFound;
    }

}
