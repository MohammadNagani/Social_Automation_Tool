import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BulkContactSaver extends JFrame {
    private DefaultListModel<String> contactListModel;
    private JTextField prefixField;
    
    public BulkContactSaver() {
        setTitle("Bulk Contact Saver");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // File selection panel
        JPanel topPanel = new JPanel(new FlowLayout());
        JButton selectFileButton = new JButton("Select File");
        prefixField = new JTextField(10);
        topPanel.add(new JLabel("Prefix: "));
        topPanel.add(prefixField);
        topPanel.add(selectFileButton);
        add(topPanel, BorderLayout.NORTH);
        
        // Contact List
        contactListModel = new DefaultListModel<>();
        JList<String> contactList = new JList<>(contactListModel);
        JScrollPane scrollPane = new JScrollPane(contactList);
        add(scrollPane, BorderLayout.CENTER);
        
        // Save Contacts Button
        JButton saveContactsButton = new JButton("Save Contacts");
        add(saveContactsButton, BorderLayout.SOUTH);
        
        // File Selection Action
        selectFileButton.addActionListener(e -> loadContactsFromFile());
        
        // Save Contacts Action
        saveContactsButton.addActionListener(e -> saveContacts());
    }

    private void loadContactsFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                List<String> lines = Files.readAllLines(Paths.get(selectedFile.getAbsolutePath()));
                contactListModel.clear();
                String prefix = prefixField.getText().trim();
                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        contactListModel.addElement(prefix + line.trim());
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveContacts() {
        if (contactListModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No contacts to save!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Contacts saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BulkContactSaver().setVisible(true));
    }
}
