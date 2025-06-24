package branch;

import book.Book;

import java.util.*;

// Model classes
public class Branch {
    private String id;
    private String name;
    private String address;
    private List<Book> books;

    public Branch(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.books = new ArrayList<>();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<Book> getBooks() { return books; }

    public void addBook(Book book) {
        books.add(book);
        book.setBranch(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return Objects.equals(id, branch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
