package com.example.messageautomation;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class WhatsappAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            // Look for WhatsApp window and UI elements
            AccessibilityNodeInfo rootNode = getRootInActiveWindow();
            if (rootNode != null) {
                // Check for the "Send" button in the WhatsApp UI
                performSendButtonClick(rootNode);
            }
        }
    }

    @Override
    public void onInterrupt() {
        // Handle interruptions (such as when service is stopped)
    }

    private void performSendButtonClick(AccessibilityNodeInfo rootNode) {
        // Iterate through the nodes to find the "Send" button by its content description or text
        for (int i = 0; i < rootNode.getChildCount(); i++) {
            AccessibilityNodeInfo node = rootNode.getChild(i);
            if (node != null && node.getText() != null) {
                // Check if the text of the button is "Send"
                if (node.getText().toString().equals("Send")) {
                    // Perform click action
                    node.performAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId());
                    break;
                }
            }
        }
    }
}
