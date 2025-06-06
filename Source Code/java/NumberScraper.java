import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberScraperService extends AccessibilityService {
    private static final String FILE_PATH = "/storage/emulated/0/Download/extracted_numbers.txt";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            String message = event.getText().toString();
            extractAndSaveNumbers(message);
        }
    }

    private void extractAndSaveNumbers(String message) {
        Pattern pattern = Pattern.compile("\\b\\d{10,13}\\b");
        Matcher matcher = pattern.matcher(message);
        StringBuilder numbers = new StringBuilder();
        
        while (matcher.find()) {
            numbers.append(matcher.group()).append("\n");
        }
        
        if (numbers.length() > 0) {
            saveToFile(numbers.toString());
        }
    }

    private void saveToFile(String data) {
        try {
            File file = new File(FILE_PATH);
            FileWriter writer = new FileWriter(file, true);
            writer.write(data);
            writer.close();
            Toast.makeText(this, "Numbers saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInterrupt() {}
}
