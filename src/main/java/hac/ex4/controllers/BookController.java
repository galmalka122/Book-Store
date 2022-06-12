package hac.ex4.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hac.ex4.repos.book.Book;
import hac.ex4.repos.book.BookRepository;

@Controller
public class BookController {

    @Autowired
    public BookRepository repo;

    private static final String REPONAME = "books";

    private BookRepository getRepo() {
        return repo;
    }

    @GetMapping("/addbook")
    public String getBookForm(Book book, Model model) {
        return "add-book";
    }

    @PostMapping("/addbook")
    public String addBook(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors())
            return "add-book";

        getRepo().save(book);
        model.addAttribute(REPONAME, getRepo().findAll());
        return "index";
    }

    @GetMapping("/updatebook/{id}")
    public String getUpdateBookForm(@PathVariable("id") Long id, Model model) {
        Book book = getRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));

        model.addAttribute("book", book);

        return "update-book";
    }

    @PostMapping("/updatebook/{id}")
    public String updateBook(@PathVariable("id") Long id, @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "update-book";
        }

        getRepo().save(book);
        model.addAttribute(REPONAME, getRepo().findAll());
        return "index";
    }

}
