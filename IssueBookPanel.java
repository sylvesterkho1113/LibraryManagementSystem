// Group 09 (Teeple K)
// Library Management System
// 1211211485 Kho Wei Cong
// 1211207735 See Chwan Kai
// 1211208688 Tee Kian Hao
// 1211208756 Tee Chin Yean

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IssueBookPanel extends JPanel {
    private JComboBox<String> isbnComboBox;
    private JLabel bookTitleLabel;
    private JComboBox<String> userComboBox;
    private static final String BOOKS_FILE = "books.txt";
    private static final String USERS_FILE = "users.txt";
    private static final String RECORDS_FILE = "records.txt";

    public IssueBookPanel() {
        setLayout(new GridLayout(4, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.ORANGE);

        // Initialize ISBN ComboBox and populate with available books
        add(new JLabel("ISBN:"));
        isbnComboBox = new JComboBox<>();
        populateISBNComboBox();
        isbnComboBox.addActionListener(this::isbnSelected);
        add(isbnComboBox);

        // Display book title label
        add(new JLabel("Book Title:"));
        bookTitleLabel = new JLabel();
        add(bookTitleLabel);

        // Initialize User ComboBox and populate with users
        add(new JLabel("User Name:"));
        userComboBox = new JComboBox<>();
        populateUserComboBox();
        add(userComboBox);

        // Add font style to button
        Font buttonFont = new Font("Courier", Font.BOLD, 14);

        // Issue Book button
        JButton issueButton = new JButton("Issue Book");
        issueButton.setFont(buttonFont); 
        issueButton.addActionListener(this::issueBook);
        add(issueButton);

        // Back to Main Panel button
        JButton backButton = new JButton("Back to Main Panel");
        backButton.setFont(buttonFont); 
        backButton.addActionListener(e -> goBackToMainPanel());
        add(backButton);
    }

    private void populateISBNComboBox() {
        List<String> books = FileUtil.readFromFile(BOOKS_FILE);
        for (String bookStr : books) {
            Book book = Book.fromString(bookStr);
            if (book.isAvailable()) {
                isbnComboBox.addItem(book.getIsbn());
            }
        }
    }

    private void isbnSelected(ActionEvent e) {
        String selectedISBN = (String) isbnComboBox.getSelectedItem();
        if (selectedISBN != null) {
            List<String> books = FileUtil.readFromFile(BOOKS_FILE);
            for (String bookStr : books) {
                Book book = Book.fromString(bookStr);
                if (book.getIsbn().equals(selectedISBN)) {
                    bookTitleLabel.setText(book.getTitle());
                    return;
                }
            }
        }
    }

    private void populateUserComboBox() {
        List<String> users = FileUtil.readFromFile(USERS_FILE);
        for (String userStr : users) {
            String[] parts = userStr.split(",");
            if (parts.length >= 1) {
                userComboBox.addItem(parts[0]); 
            }
        }
    }

    private void issueBook(ActionEvent e) {
        String isbn = (String) isbnComboBox.getSelectedItem();
        String userName = (String) userComboBox.getSelectedItem();

        if (isbn == null || userName == null) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> books = FileUtil.readFromFile(BOOKS_FILE);
        boolean bookFound = false;
        for (int i = 0; i < books.size(); i++) {
            Book book = Book.fromString(books.get(i));
            if (book.getIsbn().equals(isbn) && book.isAvailable()) {
                book.setAvailable(false);
                books.set(i, book.toString());
                bookFound = true;
                break;
            }
        }

        if (!bookFound) {
            JOptionPane.showMessageDialog(this, "Book not available or not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FileUtil.writeToFile(BOOKS_FILE, books);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String borrowDate = sdf.format(new Date());
        String record = userName + "," + isbn + "," + borrowDate + "," + "" + "," + "0";
        List<String> records = FileUtil.readFromFile(RECORDS_FILE);
        records.add(record);
        FileUtil.writeToFile(RECORDS_FILE, records);

        JOptionPane.showMessageDialog(this, "Book issued successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void clearFields() {
        isbnComboBox.setSelectedIndex(0);
        userComboBox.setSelectedIndex(0);
        bookTitleLabel.setText("");
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
            frame.setPreferredSize(new Dimension(400, 400));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
        }
    }
}
