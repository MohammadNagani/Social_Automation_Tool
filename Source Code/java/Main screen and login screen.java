import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Screen");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));
        
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        
        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(new JLabel());
        frame.add(loginButton);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                if (username.equals("admin") && password.equals("password")) {
                    JOptionPane.showMessageDialog(frame, "Login Successful");
                    frame.dispose();
                    new MainScreen();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Credentials");
                }
            }
        });
        
        frame.setVisible(true);
    }
}

class MainScreen {
    public MainScreen() {
        JFrame frame = new JFrame("Main Screen");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));
        
        JButton bulkContactButton = new JButton("Bulk Contact Saver");
        JButton autoResponderButton = new JButton("Auto-Responder");
        JButton numScraperButton = new JButton("Number Scraper");
        JButton smsBroadButton = new JButton("SMS Broadcaster");
        
        frame.add(bulkContactButton);
        frame.add(autoResponderButton);
        frame.add(numScraperButton);
        frame.add(smsBroadButton);
        
        frame.setVisible(true);
    }
}
