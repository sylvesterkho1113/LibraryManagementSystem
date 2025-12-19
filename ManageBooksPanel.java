// Group 09 (Teeple K)
// Library Management System
// 1211211485 Kho Wei Cong
// 1211207735 See Chwan Kai
// 1211208688 Tee Kian Hao
// 1211208756 Tee Chin Yean

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ManageBooksPanel extends JPanel {
    private JTable booksTable;
    private DefaultTableModel tableModel;
    private static final String BOOKS_FILE = "books.txt";

    public ManageBooksPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.ORANGE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        tableModel = new DefaultTableModel(new String[]{"Title", "Author", "ISBN", "Description", "Available"}, 0);
        booksTable = new JTable(tableModel);
        loadBooks();

        add(new JScrollPane(booksTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout()); // Use BorderLayout for buttonPanel

        // Add font style to button
        Font buttonFont = new Font("Courier", Font.BOLD, 14);

        JPanel leftButtonPanel = new JPanel(); // Panel for left buttons (Delete and Update)
        JButton deleteButton = new JButton("Delete Book");
        deleteButton.setFont(buttonFont); 
        deleteButton.addActionListener(this::deleteBook);
        leftButtonPanel.add(deleteButton);

        JButton updateButton = new JButton("Update Book");
        updateButton.setFont(buttonFont); 
        updateButton.addActionListener(this::openUpdateBookFrame);
        leftButtonPanel.add(updateButton);

        buttonPanel.add(leftButtonPanel, BorderLayout.LINE_START); // Align left buttons to the start

        // "Go Back" button aligned to the end
        JButton backButton = new JButton("Go Back");
        backButton.setFont(buttonFont); 
        backButton.addActionListener(e -> goBackToMainPanel());
        buttonPanel.add(backButton, BorderLayout.LINE_END);

        add(buttonPanel, BorderLayout.PAGE_END);
    }

    private void loadBooks() {
        List<String> books = FileUtil.readFromFile(BOOKS_FILE);
        for (String bookStr : books) {
            Book book = Book.fromString(bookStr);
            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getIsbn(), book.getDescription(), book.isAvailable()});
        }
    }

    private void deleteBook(ActionEvent e) {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.removeRow(selectedRow);
        saveBooks();
    }

    private void openUpdateBookFrame(ActionEvent e) {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String title = (String) tableModel.getValueAt(selectedRow, 0);
        String author = (String) tableModel.getValueAt(selectedRow, 1);
        String isbn = (String) tableModel.getValueAt(selectedRow, 2);
        String description = (String) tableModel.getValueAt(selectedRow, 3);
        boolean available = (boolean) tableModel.getValueAt(selectedRow, 4);

        JFrame updateFrame = new JFrame("Update Book");
        updateFrame.setSize(400, 300);
        updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateFrame.setLocationRelativeTo(this);

        JPanel updatePanel = new JPanel(new GridLayout(6, 2, 10, 10));
        updatePanel.setBackground(Color.ORANGE);
        updatePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField titleField = new JTextField(title);
        JTextField authorField = new JTextField(author);
        JLabel isbnLabel = new JLabel(isbn);  // Change JTextField to JLabel for ISBN
        JTextField descriptionField = new JTextField(description);
        JComboBox<String> availableBox = new JComboBox<>(new String[]{"true", "false"});
        availableBox.setSelectedItem(String.valueOf(available));

        updatePanel.add(new JLabel("Title:"));
        updatePanel.add(titleField);
        updatePanel.add(new JLabel("Author:"));
        updatePanel.add(authorField);
        updatePanel.add(new JLabel("ISBN:"));
        updatePanel.add(isbnLabel);  // Add the JLabel for ISBN
        updatePanel.add(new JLabel("Description:"));
        updatePanel.add(descriptionField);
        updatePanel.add(new JLabel("Available:"));
        updatePanel.add(availableBox);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(evt -> {
            String newTitle = titleField.getText().trim();
            String newAuthor = authorField.getText().trim();
            String newDescription = descriptionField.getText().trim();

            // Validate fields
            if (newTitle.isEmpty() || newAuthor.isEmpty() || newDescription.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title, Author, and Description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update the table model
            tableModel.setValueAt(newTitle, selectedRow, 0);
            tableModel.setValueAt(newAuthor, selectedRow, 1);
            tableModel.setValueAt(descriptionField.getText(), selectedRow, 3);
            tableModel.setValueAt(Boolean.parseBoolean((String) availableBox.getSelectedItem()), selectedRow, 4);

            saveBooks();
            updateFrame.dispose();
        });

        JPanel foralign = new JPanel();
        updatePanel.add(foralign); // Empty panel to align the save button
        updatePanel.add(saveButton);

        updateFrame.add(updatePanel);
        foralign.setBackground(Color.ORANGE);
        updateFrame.setVisible(true);
    }

    private void saveBooks() {
        List<String> books = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String title = (String) tableModel.getValueAt(i, 0);
            String author = (String) tableModel.getValueAt(i, 1);
            String isbn = (String) tableModel.getValueAt(i, 2);
            String description = (String) tableModel.getValueAt(i, 3);
            boolean available = (boolean) tableModel.getValueAt(i, 4);
            books.add(new Book(isbn, title, author, description, available).toString());
        }
        FileUtil.writeToFile(BOOKS_FILE, books);
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
