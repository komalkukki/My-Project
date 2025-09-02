package EBookReader;

import java.util.*;

abstract class EBook {
    protected String title;
    protected String author;
    protected int totalPages;
    protected int currentPages;

    public EBook(String title, String author, int totalPages) {
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
        this.currentPages = 1;
    }

    public void open() {
        System.out.println("Opening book: " + title + " by " + author);
    }

    public void goToPage(int page) {
        if (page > 0 && page <= totalPages) {
            currentPages = page;
            System.out.println("Now on page: " + currentPages);
        } else {
            System.out.println("Invalid page number!");
        }
    }

    public void bookmarkPage() {
        System.out.println("Bookmarked Page: " + currentPages);
    }

    public abstract void displayFormat();
}

class PDF extends EBook {
    public PDF(String title, String author, int totalPages) {
        super(title, author, totalPages);
    }

    @Override
    public void displayFormat() {
        System.out.println("This is a PDF format EBook.");
    }
}

class EPUB extends EBook {
    public EPUB(String title, String author, int totalPages) {
        super(title, author, totalPages);
    }

    @Override
    public void displayFormat() {
        System.out.println("This is an EPUB format EBook.");
    }
}

interface Searchable {
    void search(String keyword);
}

class Reader implements Searchable {
    private String name;
    private List<EBook> history;
    private EBook currentBook;

    public Reader(String name) {
        this.name = name;
        this.history = new ArrayList<>();
    }

    public void openBook(EBook book) {
        this.currentBook = book;
        book.open();
        history.add(book);
    }

    public void bookmarkCurrentPage() {
        if (currentBook != null) {
            currentBook.bookmarkPage();
        } else {
            System.out.println("No book is currently open.");
        }
    }

    public void trackHistory() {
        System.out.println(name + "'s Reading History:");
        for (EBook book : history) {
            System.out.println(" - " + book.title);
        }
    }

    @Override
    public void search(String keyword) {
        if (currentBook != null) {
            System.out.println("Searching for '" + keyword + "' inside " + currentBook.title);
        } else {
            System.out.println("Open a book first to search!.");
        }
    }
}

class Library {
    private List<EBook> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(EBook book) {
        books.add(book);
    }

    public void showAllBooks() {
        System.out.println("Library Collection:");
        for (EBook book : books) {
            System.out.println(" - " + book.title + " by " + book.author);
        }
    }

    public EBook findBook(String title) {
        for (EBook book : books) {
            if (book.title.equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
}

class FileNotFoundException extends Exception {
    public FileNotFoundException(String message) {
        super(message);
    }
}

class UnsupportedFormatException extends Exception {
    public UnsupportedFormatException(String message) {
        super(message);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);

        System.out.println("Enter the title:");
        String name = s1.nextLine();

        System.out.println("Enter the Author NAME:");
        String author = s1.nextLine();

        System.out.println("Enter your Total Pages number:");
        int totalPage = s1.nextInt();

        Library l1 = new Library();

        EBook b1 = new PDF(name, author, totalPage);
        EBook b2 = new EPUB("Sample EPUB", "kukku", 150);

        l1.addBook(b1);
        l1.addBook(b2);

        l1.showAllBooks();


        Reader r1 = new Reader("kiya");
        r1.openBook(b1);
        b1.displayFormat();
        r1.search("Chapter 1");
        r1.bookmarkCurrentPage();
        r1.trackHistory();
    }
}