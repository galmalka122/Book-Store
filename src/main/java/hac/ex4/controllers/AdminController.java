package hac.ex4.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hac.ex4.repos.book.Book;
import hac.ex4.repos.book.BookRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringtrimmer);
    }

    @Autowired
    public BookRepository repo;

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
        model.addAttribute("books", getRepo().findAll());
        return "admin";
    }

    @GetMapping("")
    public String main(Book book, Model model) {
        model.addAttribute("books", getRepo().findAll());
        return "admin";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    /** Login form. */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
