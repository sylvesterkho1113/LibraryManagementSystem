// Group 09 (Teeple K)
// Library Management System
// 1211211485 Kho Wei Cong
// 1211207735 See Chwan Kai
// 1211208688 Tee Kian Hao
// 1211208756 Tee Chin Yean

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List; 

public class ViewUsersPanel extends JPanel {
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private static final String USERS_FILE = "users.txt";

    public ViewUsersPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.ORANGE);

        tableModel = new DefaultTableModel(new String[]{"Name", "Phone", "Email"}, 0);
        usersTable = new JTable(tableModel);
        loadUsers();

        add(new JScrollPane(usersTable), BorderLayout.CENTER);

        // Add font style to button
        Font buttonFont = new Font("Courier", Font.BOLD, 14);

        JButton backButton = new JButton("Go Back");
        backButton.setFont(buttonFont); 
        backButton.addActionListener(e -> goBackToMainPanel());
        add(backButton, BorderLayout.SOUTH);
    }

    private void loadUsers() {
        List<String> users = FileUtil.readFromFile(USERS_FILE);
        for (String userStr : users) {
            User user = User.fromString(userStr);
            tableModel.addRow(new Object[]{user.getName(), user.getPhone(), user.getEmail()});
        }
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
            frame.setPreferredSize(new Dimension(400,400));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
        }
    }
}
