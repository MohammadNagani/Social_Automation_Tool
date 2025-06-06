import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.content.SharedPreferences;
import android.util.Log;

public class AutoResponderService extends AccessibilityService {
    private static final String TAG = "AutoResponder";
    
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            AccessibilityNodeInfo rootNode = getRootInActiveWindow();
            if (rootNode != null) {
                checkForMessages(rootNode);
            }
        }
    }
    
    private void checkForMessages(AccessibilityNodeInfo node) {
        if (node == null) return;
        
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null) {
                CharSequence text = child.getText();
                if (text != null) {
                    String message = text.toString();
                    Log.d(TAG, "Received message: " + message);
                    
                    if (shouldAutoRespond(message)) {
                        sendAutoReply(node, "Auto-response message");
                    }
                }
                checkForMessages(child);
            }
        }
    }
    
    private boolean shouldAutoRespond(String message) {
        SharedPreferences prefs = getSharedPreferences("AutoResponderPrefs", MODE_PRIVATE);
        String keyword = prefs.getString("keyword", "hello");
        return message.toLowerCase().contains(keyword.toLowerCase());
    }
    
    private void sendAutoReply(AccessibilityNodeInfo node, String reply) {
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null && "android.widget.EditText".equals(child.getClassName())) {
                child.setText(reply);
                
                AccessibilityNodeInfo sendButton = findSendButton(node);
                if (sendButton != null) {
                    sendButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
                break;
            }
        }
    }
    
    private AccessibilityNodeInfo findSendButton(AccessibilityNodeInfo node) {
        if (node == null) return null;
        
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null) {
                if ("android.widget.Button".equals(child.getClassName()) && child.isClickable()) {
                    return child;
                }
                AccessibilityNodeInfo found = findSendButton(child);
                if (found != null) return found;
            }
        }
        return null;
    }
    
    @Override
    public void onInterrupt() {
        Log.d(TAG, "AutoResponder interrupted");
    }
    
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        setServiceInfo(info);
        Log.d(TAG, "AutoResponder service started");
    }
}
