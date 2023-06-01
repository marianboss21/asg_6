package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    private BookController bookController;

    @Before
    public void setUp() {
        bookController = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book("Title", "Author", 2021, 10);

        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Title\",\"author\":\"Author\",\"publicationYear\":2021,\"quantity\":10}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publicationYear").value(2021))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(10));

        verify(bookService, times(1)).createBook(any(Book.class));
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book("Title", "Author", 2021, 10);
        Long bookId = 1L;

        when(bookService.getBookById(bookId)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", bookId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publicationYear").value(2021))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(10));

        verify(bookService, times(1)).getBookById(bookId);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Book book1 = new Book("Title1", "Author1", 2021, 10);
        Book book2 = new Book("Title2", "Author2", 2022, 5);
        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("Author1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].publicationYear").value(2021))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Title2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Author2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].publicationYear").value(2022))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].quantity").value(5));

        verify(bookService, times(1)).getAllBooks();
    }

}
