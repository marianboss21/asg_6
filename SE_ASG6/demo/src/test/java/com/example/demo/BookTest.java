package com.example.demo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book("Title", "Author", 2021, 10);
    }

    @Test
    public void testGettersAndSetters() {
        String newTitle = "New Title";
        String newAuthor = "New Author";
        int newPublicationYear = 2022;
        int newQuantity = 20;

        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setPublicationYear(newPublicationYear);
        book.setQuantity(newQuantity);

        assertEquals(newTitle, book.getTitle());
        assertEquals(newAuthor, book.getAuthor());
        assertEquals(newPublicationYear, book.getPublicationYear());
        assertEquals(newQuantity, book.getQuantity());
    }

    @Test
    public void testEqualsAndHashCode() {
        Book sameBook = new Book("Title", "Author", 2021, 10);
        Book differentBook = new Book("Different Title", "Different Author", 2022, 20);

        assertEquals(book, sameBook);
        assertNotEquals(book, differentBook);
        assertEquals(book.hashCode(), sameBook.hashCode());
        assertNotEquals(book.hashCode(), differentBook.hashCode());
    }
}
