package book;

// Factory Pattern for creating different types of books
interface BookFactory {
    Book createBook(String title, String author, String isbn, int publicationYear);
}
