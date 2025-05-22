package notification;

import book.Book;
import patron.Patron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationSystem {
    private Map<String, List<Patron>> bookReservations = new HashMap<>();
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void reserveBook(String isbn, Patron patron) {
        bookReservations.computeIfAbsent(isbn, k -> new ArrayList<>()).add(patron);
        notifyObservers("Book " + isbn + " reserved by " + patron.getName());
    }

    public List<Patron> getReservations(String isbn) {
        return bookReservations.getOrDefault(isbn, new ArrayList<>());
    }

    public void notifyBookAvailable(Book book) {
        List<Patron> waitingPatrons = getReservations(book.getIsbn());
        if (!waitingPatrons.isEmpty()) {
            Patron nextPatron = waitingPatrons.get(0);
            notifyObservers("Book " + book.getTitle() + " is now available for " + nextPatron.getName());
            waitingPatrons.remove(0);
        }
    }
}
