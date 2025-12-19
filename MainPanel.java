// Group 09 (Teeple K)
// Library Management System
// 1211211485 Kho Wei Cong
// 1211207735 See Chwan Kai
// 1211208688 Tee Kian Hao
// 1211208756 Tee Chin Yean

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    static JMenuBar mb; // JMenuBar
    static JMenu b,u,borrow,exit; // JMenu
    static JMenuItem m1, m2, m3, m4, m5, m6, m7,m8; // Menu items
    public MainPanel(JFrame frame) {

        // ------------------ MENU BAR ------------------
        // create a menubar
        mb = new JMenuBar();
 
        // create book menu
        b = new JMenu("Books");
        // create menuitems
        m1 = new JMenuItem("Add Book");
        m1.addActionListener(e -> switchPanel(frame, new AddBookPanel(), new Dimension(400, 400)));
        m2 = new JMenuItem("Manage Book");
        m2.addActionListener(e -> switchPanel(frame, new ManageBooksPanel(), new Dimension(800, 600)));
 
        // add menu items to menu
        b.add(m1);
        b.add(m2);
 
        // add menu to menu bar
        mb.add(b);

        // create user menu
        u = new JMenu("Users");
        // create menuitems
        m3 = new JMenuItem("Add User");
        m3.addActionListener(e -> switchPanel(frame, new AddUserPanel(), new Dimension(400, 400)));
        m4 = new JMenuItem("View Users");
        m4.addActionListener(e -> switchPanel(frame, new ViewUsersPanel(), new Dimension(800, 600)));
 
        // add menu items to menu
        u.add(m3);
        u.add(m4);

        // add menu to menu bar
        mb.add(u);

        // create operation menu
        borrow = new JMenu("Operation");
        // create menuitems
        m5 = new JMenuItem("Borrow Book");
        m5.addActionListener(e -> switchPanel(frame, new IssueBookPanel(), new Dimension(400, 200)));
        m6 = new JMenuItem("Return Book");
        m6.addActionListener(e -> switchPanel(frame, new ReturnBookPanel(), new Dimension(400, 200)));
        m7 = new JMenuItem("Issued History");
        m7.addActionListener(e -> switchPanel(frame, new IssuedBooksPanel(), new Dimension(800, 600)));
 
        // add menu items to menu
        borrow.add(m5);
        borrow.add(m6);
        borrow.add(m7);
 
        // add menu to menu bar
        mb.add(borrow);

        exit = new JMenu("Exit");
        m8 = new JMenuItem("Exit");
        m8.addActionListener(e -> exit());
        exit.add(m8);
        mb.add(exit);

        // add menubar to frame
        frame.setJMenuBar(mb);

        // ------------------ MENU BAR ------------------

        setBackground(Color.ORANGE); 
        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font buttonFont = new Font("Calibri", Font.BOLD, 14);

        // Declare image icon
        ImageIcon addBookIcon = new ImageIcon("addbook.png");
        ImageIcon manageBooksIcon = new ImageIcon("book.png");
        ImageIcon addUserIcon = new ImageIcon("adduser.png");
        ImageIcon viewUsersIcon = new ImageIcon("usericon.png");
        ImageIcon BookRecordIcon = new ImageIcon("recordicon.png");

        // add books button
        JButton addBookButton = new JButton("Add Book",addBookIcon);
        addBookButton.setFont(buttonFont); 
        addBookButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        addBookButton.setHorizontalTextPosition(SwingConstants.CENTER);
        addBookButton.addActionListener(e -> switchPanel(frame, new AddBookPanel(), new Dimension(400, 400)));
        add(addBookButton);

        // manage books function
        JButton manageBooksButton = new JButton("Manage Books",manageBooksIcon);
        manageBooksButton.setFont(buttonFont); 
        manageBooksButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        manageBooksButton.setHorizontalTextPosition(SwingConstants.CENTER);
        manageBooksButton.addActionListener(e -> switchPanel(frame, new ManageBooksPanel(), new Dimension(800, 600)));
        add(manageBooksButton);

        // add user button
        JButton addUserButton = new JButton("Add User",addUserIcon);
        addUserButton.setFont(buttonFont); 
        addUserButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        addUserButton.setHorizontalTextPosition(SwingConstants.CENTER);
        addUserButton.addActionListener(e -> switchPanel(frame, new AddUserPanel(), new Dimension(400, 400)));
        add(addUserButton);

        // view users button
        JButton viewUsersButton = new JButton("View Users",viewUsersIcon);
        viewUsersButton.setFont(buttonFont); 
        viewUsersButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        viewUsersButton.setHorizontalTextPosition(SwingConstants.CENTER);
        viewUsersButton.addActionListener(e -> switchPanel(frame, new ViewUsersPanel(), new Dimension(800, 600)));
        add(viewUsersButton);

        // Issue/borrow books function
        JButton issueBookButton = new JButton("Issue Book",BookRecordIcon);
        issueBookButton.setFont(buttonFont); 
        issueBookButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        issueBookButton.setHorizontalTextPosition(SwingConstants.CENTER);
        issueBookButton.addActionListener(e -> switchPanel(frame, new IssueBookPanel(), new Dimension(400, 200)));
        add(issueBookButton);

        // Return book function
        JButton returnBookButton = new JButton("Return Book",BookRecordIcon);
        returnBookButton.setFont(buttonFont); 
        returnBookButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        returnBookButton.setHorizontalTextPosition(SwingConstants.CENTER);
        returnBookButton.addActionListener(e -> switchPanel(frame, new ReturnBookPanel(), new Dimension(400, 200)));
        add(returnBookButton);

        // Issued books history
        JButton issuedBooksButton = new JButton("Issued History",BookRecordIcon);
        issuedBooksButton.setFont(buttonFont); 
        issuedBooksButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        issuedBooksButton.setHorizontalTextPosition(SwingConstants.CENTER);
        issuedBooksButton.addActionListener(e -> switchPanel(frame, new IssuedBooksPanel(), new Dimension(800, 600)));
        add(issuedBooksButton);
    }

    // Function to jump to different panel
    private void switchPanel(JFrame frame, JPanel panel, Dimension preferredSize) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.setPreferredSize(preferredSize); // Set size based on different function
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    private void exit(){
        System.exit(0);
    }
}
