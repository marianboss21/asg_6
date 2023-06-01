package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    @Before
    public void setUp() {
    
        bookService = new BookService(bookRepository);
    }

    @Test
    public void testCreateBook() {
        Book book = new Book("Title", "Author", 2021, 10);
        book.setQuantity(0);

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.createBook(book);

        verify(bookRepository, times(1)).save(any(Book.class));
        assertEquals(book, createdBook);
        assertEquals(0, createdBook.getQuantity());
    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book("Title", "Author", 2021, 10);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Book retrievedBook = bookService.getBookById(bookId);

        verify(bookRepository, times(1)).findById(bookId);
        assertEquals(book, retrievedBook);
    }

    @Test(expected = NotFoundException.class)
    public void testGetBookByIdNotFound() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        bookService.getBookById(bookId);
    }

    @Test
    public void testUpdateBook() {
        Long bookId = 1L;
        Book existingBook = new Book("Title1", "Author1", 2021, 10);
        Book updatedBook = new Book("Title2", "Author2", 2022, 5);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book modifiedBook = bookService.updateBook(bookId, updatedBook);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(any(Book.class));
        assertEquals(updatedBook.getTitle(), modifiedBook.getTitle());
        assertEquals(updatedBook.getAuthor(), modifiedBook.getAuthor());
        assertEquals(updatedBook.getPublicationYear(), modifiedBook.getPublicationYear());
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        Book book = new Book("Title", "Author", 2021, 10);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book("Title1", "Author1", 2021, 10);
        Book book2 = new Book("Title2", "Author2", 2022, 5);
        List<Book> books = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> retrievedBooks = bookService.getAllBooks();

        verify(bookRepository, times(1)).findAll();
        assertEquals(books, retrievedBooks);
    }

    @Test
    public void testIncreaseQuantity() {
        Long bookId = 1L;
        Book book = new Book("Title", "Author", 2021, 10);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.increaseQuantity(bookId, 5);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(any(Book.class));
        assertEquals(15, book.getQuantity());
    }

    @Test(expected = InsufficientQuantityException.class)
    public void testDecreaseQuantityInsufficientQuantity() {
        Long bookId = 1L;
        Book book = new Book("Title", "Author", 2021, 10);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        bookService.decreaseQuantity(bookId, 15);
    }

    @Test
    public void testDecreaseQuantity() throws InsufficientQuantityException {
        Long bookId = 1L;
        Book book = new Book("Title", "Author", 2021, 10);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.decreaseQuantity(bookId, 5);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(any(Book.class));
        assertEquals(5, book.getQuantity());
    }
}

