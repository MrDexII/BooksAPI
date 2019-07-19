package com.andrzej.bookDatabase.Service;

import com.andrzej.bookDatabase.Model.Book;
import com.andrzej.bookDatabase.Repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bookService")
public class BookService {


    private BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public void saveBook(Book book) {
        repository.save(book);
    }

    public void updateBook(long id, Book book) {
        book.setId(id);
        repository.save(book);
    }

    public Book findBookById(long id) {
        return repository.findById(id);
    }
    public Book findBookByIsbn(String isbn){
        return repository.findByIsbn(isbn);
    }

    public void deleteBook(long id) {
        repository.deleteById(id);
    }

    public List<Book> findAllBooks() {
        return repository.findAll();
    }
}
