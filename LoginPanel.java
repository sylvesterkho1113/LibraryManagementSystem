// Group 09 (Teeple K)
// Library Management System
// 1211211485 Kho Wei Cong
// 1211207735 See Chwan Kai
// 1211208688 Tee Kian Hao
// 1211208756 Tee Chin Yean

import javax.swing.*;
import java.awt.*;
import java.lang.NullPointerException;
import java.lang.Exception;

public class LoginPanel extends JPanel {
    // Define textfield and passwordfield
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel(JFrame frame) {
        setLayout(new BorderLayout());  // Use BorderLayout
        setBackground(Color.WHITE);    // Set background color to orange

        try {
            ImageIcon logoIcon = new ImageIcon("logo.png"); // Main logo
            JLabel logoLabel = new JLabel(logoIcon);
            logoLabel.setHorizontalAlignment(JLabel.CENTER);  // Center align the logo
            add(logoLabel, BorderLayout.NORTH); // Align logo to north of border
        } catch (Exception e) {
            showErrorDialog("Error loading logo: " + e.getMessage()); // Show error if logo cannot load
        }

        // Panel for login fields and button
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        loginPanel.setBackground(Color.ORANGE);  

        try {
            // Username label, field, and add to loginpanel
            loginPanel.add(new JLabel("Username:"));
            usernameField = new JTextField();
            loginPanel.add(usernameField);

            // Password label, field, and add to loginpanel
            loginPanel.add(new JLabel("Password:"));
            passwordField = new JPasswordField();
            loginPanel.add(passwordField);

            // Add font style to button
            Font buttonFont = new Font("Courier", Font.BOLD, 14);
        
            // Login button and add to loginpanel
            JButton loginButton = new JButton("Login");
            loginButton.setFont(buttonFont); 
            loginButton.addActionListener(e -> login(frame)); //event listener to call login() function
            loginPanel.add(loginButton);

            // Align loginpanel to center
            add(loginPanel, BorderLayout.CENTER);
        } catch (NullPointerException e) {
            // Error exception handling
            showErrorDialog("A component is not initialized properly: " + e.getMessage());
        } 
    }

    // Function used when user click sign in
    private void login(JFrame frame) {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Default admin username and password to "admin" and "admin123"
            if (username.equals("admin") && password.equals("admin123")) {
                // Remove current panel and redirect to main menu
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new MainPanel(frame));
                frame.setPreferredSize(new Dimension(470, 470));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.revalidate();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NullPointerException e) {
            showErrorDialog("Error during login: Missing username or password field. " + e.getMessage());
        } 
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}