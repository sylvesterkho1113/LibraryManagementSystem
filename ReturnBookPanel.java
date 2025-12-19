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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Date;

public class ReturnBookPanel extends JPanel {
    private JComboBox<String> userComboBox;
    private JComboBox<String> isbnComboBox;
    private JLabel bookTitleLabel;
    private static final String BOOKS_FILE = "books.txt";
    private static final String RECORDS_FILE = "records.txt";
    private static final String USERS_FILE = "users.txt";

    public ReturnBookPanel() {
        setLayout(new GridLayout(4, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.ORANGE);

        // Initialize User ComboBox and populate with user names from users.txt
        add(new JLabel("User Name:"));
        userComboBox = new JComboBox<>();
        populateUserComboBox();
        userComboBox.addActionListener(this::userSelected);
        add(userComboBox);

        // Initialize ISBN ComboBox (populated dynamically after user selection)
        add(new JLabel("ISBN:"));
        isbnComboBox = new JComboBox<>();
        isbnComboBox.setEnabled(false); // Initially disabled until user is selected
        isbnComboBox.addActionListener(this::isbnSelected);
        add(isbnComboBox);

        // Display book title label
        add(new JLabel("Book Title:"));
        bookTitleLabel = new JLabel();
        add(bookTitleLabel);

        // Add font style to button
        Font buttonFont = new Font("Courier", Font.BOLD, 14);

        // Return Book button
        JButton returnButton = new JButton("Return Book");
        returnButton.setFont(buttonFont); 
        returnButton.addActionListener(this::returnBook);
        add(returnButton);

        // Back to Main Panel button
        JButton backButton = new JButton("Back to Main Panel");
        backButton.setFont(buttonFont); 
        backButton.addActionListener(e -> goBackToMainPanel());
        add(backButton);
    }

    private void populateUserComboBox() {
        List<String> users = FileUtil.readFromFile(USERS_FILE);
        for (String user : users) {
            String[] parts = user.split(",");
            if (parts.length >= 1) { // Ensure there is at least a user name
                userComboBox.addItem(parts[0].trim()); // Add only the user's name to the ComboBox
            }
        }
    }

    private void userSelected(ActionEvent e) {
        isbnComboBox.removeAllItems(); // Clear existing items
        String selectedUser = (String) userComboBox.getSelectedItem();
        if (selectedUser != null) {
            List<String> records = FileUtil.readFromFile(RECORDS_FILE);
            for (String record : records) {
                String[] parts = record.split(",");
                if (parts.length >= 2 && parts[0].equals(selectedUser) && parts[3].isEmpty()) {
                    isbnComboBox.addItem(parts[1].trim()); // Add ISBN of books borrowed by selected user
                }
            }
            isbnComboBox.setEnabled(true); // Enable ISBN ComboBox after user selection
        } else {
            isbnComboBox.setEnabled(false); // Disable ISBN ComboBox if no user selected
        }
        bookTitleLabel.setText(""); // Reset book title label
    }

    private void isbnSelected(ActionEvent e) {
        String selectedISBN = (String) isbnComboBox.getSelectedItem();
        if (selectedISBN != null) {
            List<String> books = FileUtil.readFromFile(BOOKS_FILE);
            for (String bookStr : books) {
                Book book = Book.fromString(bookStr);
                if (book.getIsbn().trim().equals(selectedISBN.trim())) { // Trim to handle whitespace
                    bookTitleLabel.setText(book.getTitle());
                    return;
                }
            }
        }
        // If ISBN is not found or null, clear the book title label
        bookTitleLabel.setText("");
    }

    private void returnBook(ActionEvent e) {
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
            if (book.getIsbn().equals(isbn)) {
                book.setAvailable(true); // Mark book as available
                books.set(i, book.toString());
                bookFound = true;
                break;
            }
        }

        if (!bookFound) {
            JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FileUtil.writeToFile(BOOKS_FILE, books);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String returnDate = sdf.format(new Date());

        List<String> records = FileUtil.readFromFile(RECORDS_FILE);
        String fine = "0";
        for (int i = 0; i < records.size(); i++) {
            String[] parts = records.get(i).split(",");
            if (parts[0].equals(userName) && parts[1].equals(isbn) && parts[3].isEmpty()) {
                fine = calculateFine(parts[2]);
                records.set(i, parts[0] + "," + parts[1] + "," + parts[2] + "," + returnDate + "," + fine);
                break;
            }
        }

        FileUtil.writeToFile(RECORDS_FILE, records);

        JOptionPane.showMessageDialog(this, "Book returned successfully, fine = RM"+fine, "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private String calculateFine(String borrowDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate borrowDate = LocalDate.parse(borrowDateStr, formatter);
        LocalDate currentDate = LocalDate.now();
        
        long daysBetween = ChronoUnit.DAYS.between(borrowDate, currentDate);
        long fine = 0;
        
        if (daysBetween > 30) {
            fine = 30 + ((daysBetween - 30) / 30) * 50;
        }
        
        return String.valueOf(fine);
    }

    private void clearFields() {
        userComboBox.setSelectedIndex(0);
        isbnComboBox.setSelectedIndex(0);
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
