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
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = {"/", "/new"}, method = RequestMethod.GET)
    public ModelAndView showAddBookForm() {
        ModelAndView modelAndView = new ModelAndView();
        Book book = new Book();
//        Link link = linkTo(BookController.class).slash(book).withRel("book");
        modelAndView.addObject("book", book);
        modelAndView.setViewName("book/new");
        return modelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView createBook(@Valid Book book, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("book/new");
        } else {
            Book bookExists = bookService.findBookByIsbn(book.getIsbn());
            if (bookExists != null) {
                result
                        .rejectValue("isbn", "error.isbn",
                                "There is already a book registered with this isbn");
                modelAndView.setViewName("book/new");
                return modelAndView;
            }
            bookService.saveBook(book);
            modelAndView.addObject("successMessage", "Book has been registered successfully");
            modelAndView.addObject("book", new Book());
            modelAndView.setViewName("admin/home");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView showBooks() {
        ModelAndView modelAndView = new ModelAndView();
        List<Book> allBooks = bookService.findAllBooks();
        modelAndView.addObject("allBooks", allBooks);
        modelAndView.setViewName("book/show");
        return modelAndView;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView showUpdateView(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Book book = bookService.findBookById(id);
        modelAndView.addObject("book", book);
        modelAndView.setViewName("book/update");
        return modelAndView;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ModelAndView updateBook(@PathVariable("id") long id, Book book, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("book/update");
        } else {
            bookService.updateBook(id, book);
            modelAndView.addObject("successMessage", "Book has been updated successfully");
            modelAndView.addObject("book", new Book());
            modelAndView.setViewName("admin/home");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteBook(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        bookService.deleteBook(id);
        modelAndView.addObject("successMessage", "Book has been successfully delete");
        modelAndView.setViewName("/admin/home");
        return modelAndView;
    }
}
