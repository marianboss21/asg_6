package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {
        book.setQuantity(0);
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id));
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book existingBook = getBookById(id);

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPublicationYear(updatedBook.getPublicationYear());

        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        Book existingBook = getBookById(id);

        bookRepository.delete(existingBook);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void increaseQuantity(Long id, int quantity) {
        Book book = getBookById(id);

        book.setQuantity(book.getQuantity() + quantity);
        book.setAvailableQuantity(book.getAvailableQuantity() + quantity);

        bookRepository.save(book);
    }

    public void decreaseQuantity(Long id, int quantity) throws InsufficientQuantityException {
        Book book = getBookById(id);

        if (book.getQuantity() < quantity) {
            throw new InsufficientQuantityException("Insufficient quantity available for book with ID: " + id);
        }

        book.setQuantity(book.getQuantity() - quantity);
        book.setAvailableQuantity(book.getAvailableQuantity() - quantity);

        bookRepository.save(book);
    }

    public void placeOrder(Long bookId, int quantity) {
        Book book = getBookById(bookId);

        if (book.getAvailableQuantity() < quantity) {
            throw new InsufficientQuantityException("Insufficient quantity available for book with ID: " + bookId);
        }

        book.setAvailableQuantity(book.getAvailableQuantity() - quantity);

        bookRepository.save(book);
    }
}
