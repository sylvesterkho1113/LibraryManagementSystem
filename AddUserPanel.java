// Group 09 (Teeple K)
// Library Management System
// 1211211485 Kho Wei Cong
// 1211207735 See Chwan Kai
// 1211208688 Tee Kian Hao
// 1211208756 Tee Chin Yean

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AddUserPanel extends JPanel {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private static final String USERS_FILE = "users.txt";

    public AddUserPanel() {
        setLayout(new GridLayout(4, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.ORANGE);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        // Add font style to button
        Font buttonFont = new Font("Courier", Font.BOLD, 14);

        JButton addButton = new JButton("Add User");
        addButton.setFont(buttonFont); 
        addButton.addActionListener(this::addUser);
        add(addButton);

        JButton backButton = new JButton("Back to Main Panel");
        backButton.setFont(buttonFont); 
        backButton.addActionListener(e -> goBackToMainPanel());
        add(backButton);
    }

    private void addUser(ActionEvent e) {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        // Check if is empty
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name, Phone, and Email cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate name uniqueness
        if (!isNameUnique(name)) {
            JOptionPane.showMessageDialog(this, "Name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate phone number format and length
        if (!isValidPhoneNumber(phone)) {
            JOptionPane.showMessageDialog(this, "Phone number must be numeric and between 10-11 digits.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // All validations passed, add the user
        List<String> users = FileUtil.readFromFile(USERS_FILE);
        users.add(new User(name, phone, email).toString());
        FileUtil.writeToFile(USERS_FILE, users);

        JOptionPane.showMessageDialog(this, "User added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private boolean isNameUnique(String name) {
        List<String> users = FileUtil.readFromFile(USERS_FILE);
        for (String userStr : users) {
            User user = User.fromString(userStr);
            if (user.getName().equalsIgnoreCase(name)) {
                return false; // Name already exists
            }
        }
        return true; // Name is unique
    }

    private boolean isValidPhoneNumber(String phone) {
        // Check if phone number is numeric and between 10-11 digits
        return phone.matches("\\d{10,11}");
    }

    private boolean isValidEmail(String email) {
        // Check if email format is valid
        return email.matches("[\\w.-]+@\\w+\\.[\\w.-]+");
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    private void goBackToMainPanel() {
        // Find the parent JFrame and switch its content to MainPanel
        Container parent = this.getParent();
        while (parent != null && !(parent instanceof JFrame)) {
            parent = parent.getParent();
        }
        if (parent instanceof JFrame) {
            JFrame frame = (JFrame) parent;
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new MainPanel(frame));
            frame.revalidate();
            frame.repaint();
        }
    }
}
