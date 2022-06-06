package hac.ex4.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hac.ex4.repos.Book;
import hac.ex4.repos.BookRepository;

@Controller
public class BookController {

    @Autowired
    public BookRepository repo;

    private static final String REPONAME = "books";

    private BookRepository getRepo() {
        return repo;
    }

    @GetMapping("/")
    public String main(Book book, Model model) {
        model.addAttribute(REPONAME, getRepo().findAll());
        return "index";
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

}
