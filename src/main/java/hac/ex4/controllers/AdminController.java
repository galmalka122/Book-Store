package hac.ex4.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hac.ex4.repos.Payments.Payment;
import hac.ex4.repos.Payments.PaymentService;
import hac.ex4.repos.book.Book;
import hac.ex4.repos.book.BookService;

/**
 * a route to mange admin section
 * id client have no authority he's transferred to the login page
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * trims all form's inputs before submitting
     * @param binder - binds the form inputs
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringtrimmer);
    }

    @Autowired
    private BookService service;//the book repository

    @Autowired
    private PaymentService paymentService;//the payment repository

    /**
     * the landing page for the admin section.
     * @param model - the view
     * @return - the landing page of the admin section
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping("")
    public String main(Model model) {
        return "redirect:/admin/page/1";
    }

    /**
     * @return - the login form
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * @return - the login form with errors elements
     */
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    /**
     * a route to add books to the store by the admin
     * @param book - binding a new entity
     * @param model - the view
     * @return - the form to add book
     */
    @GetMapping("/addbook")
    public String getBookForm(Book book, Model model) {
        return "add-book";
    }

    /**
     * a route to post new books by admin
     * @param book - binding a new entity
     * @param result - the inputs validation
     * @param model - the view
     * @return - the form to with errors if validation fails else the admin landing page
     */
    @PostMapping("/addbook")
    public String addBook(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors())
            return "redirect:/error";

        service.getRepo().save(book);
        model.addAttribute("books", service.getRepo().findAll());
        return "redirect:/admin";

    }

    /**
     * a route to update existing book by the admin
     * @param id - the book id
     * @param model - the view
     * @return the page of the book update form
     */
    @GetMapping("/update/{id}")
    public String getUpdateBookForm(@PathVariable("id") Long id, Model model) {
        Book book = service.getRepo().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
        model.addAttribute("book", book);
        return "update-book";
    }

    /**
     * @param id - the book id
     * @param book - the book with validation
     * @param result - the form validation
     * @param model - the view
     * @return - the form to with errors if validation fails else the admin landing page
     */
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") Long id, @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "update-book";
        }

        service.getRepo().save(book);
        return "redirect:/admin";
    }

    /**
     * a route to delete existing book by the admin
     * @param id - the book id
     * @param model - the view
     * @return - the page of the book delete validation
     */
    @GetMapping("/delete/{id}")
    public String getDeleteModal(@PathVariable("id") Long id, Model model) {

        Book book = service.getRepo()
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "delete-book";
    }

    /**
     * a route to post the delete operation
     * @param id - the book id
     * @param model - the view
     * @return - the admin page
     */
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, Model model) {

        Book book = service.getRepo()
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid book Id:" + id));
        service.getRepo().delete(book);
        return "redirect:/admin";
    }

    /**
     * a route to get all payments by clients
     * @param model - the view
     * @return - the page with payments table
     */
    @GetMapping("/payments")
    public String getPayments(Model model) {
        Double total = 0.;
        List<Payment> payments = paymentService.getRepo().findAll();
        for (Payment pay : payments) {
            total += pay.getAmount();
        }
        model.addAttribute("listProducts", payments);
        model.addAttribute("total", total);
        return "payments";
    }

}
