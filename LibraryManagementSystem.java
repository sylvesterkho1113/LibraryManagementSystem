// Group 09 (Teeple K)
// Library Management System
// 1211211485 Kho Wei Cong
// 1211207735 See Chwan Kai
// 1211208688 Tee Kian Hao
// 1211208756 Tee Chin Yean

import javax.swing.*;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Library Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new LoginPanel(frame)); // Redirct to login panel
            frame.pack();
            frame.setSize(400, 350); // Set frame size
            frame.setLocationRelativeTo(null); // Center the frame to screen
            frame.setVisible(true);
        });
    }
}