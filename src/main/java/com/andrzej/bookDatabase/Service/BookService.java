package com.andrzej.bookDatabase.Service;

import com.andrzej.bookDatabase.Model.Book;
import com.andrzej.bookDatabase.Repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service()
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public ModelAndView saveBook(Book book, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("book/new");
        } else {
            Book bookExists = repository.findByIsbn(book.getIsbn());
            if (bookExists != null) {
                result
                        .rejectValue("isbn", "error.isbn",
                                "There is already a book registered with this isbn");
                modelAndView.setViewName("book/new");
                return modelAndView;
            }
            repository.save(book);
            modelAndView.addObject("successMessage", "Book has been registered successfully");
            modelAndView.addObject("book", new Book());
            modelAndView.setViewName("admin/home");
        }
        return modelAndView;
    }

    public ModelAndView updateBook(long id, Book book, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("book/update");
        } else {
            book.setId(id);
            repository.save(book);
            modelAndView.addObject("successMessage", "Book has been updated successfully");
            modelAndView.addObject("book", new Book());
            modelAndView.setViewName("admin/home");
        }
        return modelAndView;
    }

    public ModelAndView deleteBook(long id) {
        ModelAndView modelAndView = new ModelAndView();
        repository.deleteById(id);
        modelAndView.addObject("successMessage", "Book has been successfully delete");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    public ModelAndView findAllBooks() {
        ModelAndView modelAndView = new ModelAndView();
        List<Book> allBooks = repository.findAll();
        modelAndView.addObject("allBooks", allBooks);
        modelAndView.setViewName("book/show");
        return modelAndView;
    }

    public ModelAndView showAddBookForm() {
        ModelAndView modelAndView = new ModelAndView();
        Book book = new Book();
//        Link link = linkTo(BookController.class).slash(book).withRel("book");
        modelAndView.addObject("book", book);
        modelAndView.setViewName("book/new");
        return modelAndView;
    }

    public ModelAndView showUpdateView(long id) {
        ModelAndView modelAndView = new ModelAndView();
        Book book = repository.findById(id);
        modelAndView.addObject("book", book);
        modelAndView.setViewName("book/update");
        return modelAndView;
    }
}
