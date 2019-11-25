package com.example.accessibility.support;

import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

/**Utility class to help handle AccessibilityService related operations.
        * <p/>
 **/

public class AccessibilityUtils {

    private static final String TAG = "ASUtil";

    public static void performAction(AccessibilityNodeInfo info, AccessAction accessAction) {
        switch (accessAction.actionType) {
            case AccessibilityNodeInfo.ACTION_CLICK:
                click(info);
                break;
            case AccessibilityNodeInfo.ACTION_COPY:
                copy(info);
                break;
            case AccessibilityNodeInfo.ACTION_PASTE:
                paste(info);
                break;
            case AccessibilityNodeInfo.ACTION_SET_TEXT:
                setText(info, accessAction.actionValue);
                break;
        }
        accessAction.performed = true;
    }

    public static AccessibilityNodeInfo findClickableNode(AccessibilityNodeInfo info) {
        if (info == null) {
            return null;
        }
        if (info.isClickable()) {
            return info;
        } else {
            return findClickableNode(info.getParent());
        }
    }

    public static void click(AccessibilityNodeInfo info) {
        AccessibilityNodeInfo clickableNode = findClickableNode(info);
        if (clickableNode != null) {
            clickableNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    public static void copy(AccessibilityNodeInfo info) {
        if (info != null) {
            info.performAction(AccessibilityNodeInfo.ACTION_COPY);
        }
    }

    public static void paste(AccessibilityNodeInfo info) {
        if (info != null) {
            info.performAction(AccessibilityNodeInfo.ACTION_PASTE);
        }
    }

    public static void setText(AccessibilityNodeInfo info, String text) {
        if (info != null && !TextUtils.isEmpty(text)) {
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    text);
            info.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        }
    }

    public static List<AccessibilityNodeInfo> findNodesByText(AccessibilityNodeInfo root, String text) {
        return root.findAccessibilityNodeInfosByText(text);
    }

    public static List<AccessibilityNodeInfo> findNodesByViewId(AccessibilityNodeInfo root,
                                                                String viewId) {
        return root.findAccessibilityNodeInfosByViewId(viewId);
    }

    public static AccessibilityNodeInfo findNodesByContentDesc(AccessibilityNodeInfo root,
                                                               String targetContentDesc) {
        if (root == null || TextUtils.isEmpty(targetContentDesc)) {
            return null;
        }

        CharSequence contentDesc = root.getContentDescription();
        Log.d(TAG, "content desc: " + contentDesc);

        if (!TextUtils.isEmpty(contentDesc) && contentDesc.equals(targetContentDesc)) {
            return root;
        } else {
            for (int i = 0; i < root.getChildCount(); i++) {
                AccessibilityNodeInfo childNode = root.getChild(i);
                AccessibilityNodeInfo result = findNodesByContentDesc(childNode, targetContentDesc);
                if (result != null) {
                    return result;
                }
            }
            return null;
        }
    }
}
