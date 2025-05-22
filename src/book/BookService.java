package book;

import branch.Branch;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BookService {
    private List<Book> books;
    private static final Logger logger = Logger.getLogger(BookService.class.getName());

    public BookService() {
        this.books = new ArrayList<>();
    }

    public boolean addBook(Book book, Branch branch) {
        if (books.contains(book)) {
            logger.warning("Book with ISBN " + book.getIsbn() + " already exists.");
            return false;
        }
        books.add(book);
        branch.addBook(book);
        logger.info("Added book: " + book + " to branch: " + branch.getName());
        return true;
    }

    public boolean removeBook(String isbn) {
        Book bookToRemove = findBookByIsbn(isbn);
        if (bookToRemove != null) {
            if (bookToRemove.getBranch() != null) {
                bookToRemove.getBranch().removeBook(bookToRemove);
            }
            books.remove(bookToRemove);
            logger.info("Removed book: " + bookToRemove);
            return true;
        }
        logger.warning("Book with ISBN " + isbn + " not found.");
        return false;
    }

    public boolean updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(updatedBook.getIsbn())) {
                // Preserve the branch relationship
                Branch branch = books.get(i).getBranch();
                if (branch != null) {
                    branch.removeBook(books.get(i));
                    branch.addBook(updatedBook);
                }
                books.set(i, updatedBook);
                logger.info("Updated book: " + updatedBook);
                return true;
            }
        }
        logger.warning("Book with ISBN " + updatedBook.getIsbn() + " not found.");
        return false;
    }

    public Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Book> findBooksByTitle(String title) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByAuthor(String author) {
        return books.stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByBranch(Branch branch) {
        return books.stream()
                .filter(b -> b.getBranch() != null && b.getBranch().equals(branch))
                .collect(Collectors.toList());
    }
}