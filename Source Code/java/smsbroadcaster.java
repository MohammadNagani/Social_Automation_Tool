import android.telephony.SmsManager;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;

public class SmsBroadcaster {
    private static final String FILE_PATH = "/storage/emulated/0/Download/extracted_numbers.txt";
    private Context context;

    public SmsBroadcaster(Context context) {
        this.context = context;
    }

    public void sendBulkSMS(String message) {
        List<String> numbers = readNumbersFromFile();
        SmsManager smsManager = SmsManager.getDefault();
        for (String number : numbers) {
            smsManager.sendTextMessage(number, null, message, null, null);
        }
        Toast.makeText(context, "Messages Sent!", Toast.LENGTH_SHORT).show();
    }

    private List<String> readNumbersFromFile() {
        List<String> numbers = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                numbers.add(line.trim());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }
}
