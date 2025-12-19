// Group 09 (Teeple K)
// Library Management System
// 1211211485 Kho Wei Cong
// 1211207735 See Chwan Kai
// 1211208688 Tee Kian Hao
// 1211208756 Tee Chin Yean

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String description;
    private boolean available;

    public Book(String isbn, String title, String author, String description, boolean available) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.description = description;
        this.available = available;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return isbn + "," + title + "," + author + "," + description + "," + available;
    }

    public static Book fromString(String str) {
        String[] parts = str.split(",");
        return new Book(parts[0], parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4]));
    }
}
