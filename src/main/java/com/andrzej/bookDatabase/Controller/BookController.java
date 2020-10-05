package com.andrzej.bookDatabase.Controller;

import com.andrzej.bookDatabase.Model.Book;
import com.andrzej.bookDatabase.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //SHOW VIEW
    @RequestMapping(value = {"/", "/new"}, method = RequestMethod.GET)
    public ModelAndView showAddBookForm() {
        return bookService.showAddBookForm();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView showUpdateView(@PathVariable("id") long id) {
        return bookService.showUpdateView(id);
    }

    //CRUD
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView createBook(@Valid Book book, BindingResult result) {
        return bookService.saveBook(book, result);
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView showBooks() {
        return bookService.findAllBooks();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ModelAndView updateBook(@PathVariable("id") long id, Book book, BindingResult result) {
        return bookService.updateBook(id, book, result);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteBook(@PathVariable("id") long id) {
        return bookService.deleteBook(id);
    }
}
