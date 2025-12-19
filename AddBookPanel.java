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

public class AddBookPanel extends JPanel {
    // Define textfield
    private JTextField titleField;
    private JTextField authorField;
    private JTextField isbnField;
    private JTextField descriptionField;
    private static final String BOOKS_FILE = "books.txt";

    public AddBookPanel() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.ORANGE);

        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Author:"));
        authorField = new JTextField();
        add(authorField);

        add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        add(isbnField);

        add(new JLabel("Description:"));
        descriptionField = new JTextField();
        add(descriptionField);

        Font buttonFont = new Font("Courier", Font.BOLD, 14);

        JButton addButton = new JButton("Add Book");
        addButton.setFont(buttonFont);
        addButton.addActionListener(this::addBook);
        add(addButton);

        JButton backButton = new JButton("Back to Main Panel");
        backButton.setFont(buttonFont);
        backButton.addActionListener(e -> goBackToMainPanel());
        add(backButton);
    }

    private void addBook(ActionEvent e) {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String isbn = isbnField.getText().trim();
        String description = descriptionField.getText().trim();

        // Check if any field is empty
        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if ISBN is already in use
        if (!isISBNUnique(isbn)) {
            JOptionPane.showMessageDialog(this, "ISBN already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // All validations passed, add the book
        List<String> books = FileUtil.readFromFile(BOOKS_FILE);
        books.add(new Book(isbn, title, author, description, true).toString());
        FileUtil.writeToFile(BOOKS_FILE, books);

        JOptionPane.showMessageDialog(this, "Book added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private boolean isISBNUnique(String isbn) {
        List<String> books = FileUtil.readFromFile(BOOKS_FILE);
        for (String bookStr : books) {
            Book book = Book.fromString(bookStr);
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                return false; // ISBN already exists
            }
        }
        return true; // ISBN is unique
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        descriptionField.setText("");
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
            frame.revalidate();
            frame.repaint();
        }
    }
}
