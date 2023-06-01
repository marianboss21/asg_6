package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookService.updateBook(id, updatedBook);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/{id}/increase-quantity")
    public void increaseQuantity(@PathVariable Long id, @RequestParam int quantity) {
        bookService.increaseQuantity(id, quantity);
    }

    @PostMapping("/{id}/decrease-quantity")
    public void decreaseQuantity(@PathVariable Long id, @RequestParam int quantity) throws InsufficientQuantityException {
        bookService.decreaseQuantity(id, quantity);
    }

    @PostMapping("/{id}/orders") // New endpoint to place book orders
    public void placeOrder(@PathVariable Long id, @RequestParam int quantity) {
        bookService.placeOrder(id, quantity);
    }
}
