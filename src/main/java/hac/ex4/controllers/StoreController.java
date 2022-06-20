package hac.ex4.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hac.ex4.beans.Basket;
import hac.ex4.repos.Payments.Payment;
import hac.ex4.repos.Payments.PaymentService;
import hac.ex4.repos.book.Book;
import hac.ex4.repos.book.BookService;

/**
 * controller to manage the store actions
 */
@Controller
@RequestMapping("/store")
public class StoreController {


    @Autowired
    private BookService service;//the book repository


    @Autowired
    private PaymentService paymentService; //the payment repository

    @Resource(name = "basket")
    private Basket basket;//the basket

    /**
     * maps the route for the store landing page. adds the cart to manage
     * @param basket - the user's session basket
     * @param model - the view
     * @return - forwards to get the first books page
     */
    @GetMapping("")
    public String getStore(Basket basket, Model model) {
        model.addAttribute("quantitiy", basket.getTotalQuantity());
        return "forward:/store/page/1";
    }

    /**
     * a route to respond with cart content to client
     * @param model - the view
     * @return - the json content of the basket
     */
    @GetMapping("/getbasket")
    public ResponseEntity<List<Map<String, String>>> getBasket(Model model) {
        return ResponseEntity.status(200).body(this.basket.getBasketString());
    }


    /**
     * a route to add book to cart
     * @param id - the book's id
     * @param model - the view
     * @return - a json respond with status about operation
     */
    @PostMapping("/addbasket/{id}")
    public ResponseEntity<String> addToBasket(@PathVariable(name = "id") Long id, Model model) {
        Book book = service.getRepo().findById(id)//retrieve the book by id
                .orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
        if (book.getQuantity() <= 0) { //check if quantity is ok
            return ResponseEntity.status(400).body("The book is currently out of stock");
        }
        basket.addBook(book);
        return ResponseEntity.status(200).body(basket.getTotalQuantity().toString());
    }

    /**
     * a route to process payment by the client
     * @return - a respond with status and message about payment process
     */
    @PostMapping("/pay")
    public ResponseEntity<String> pay() {
        boolean approved = true; // to indicate if payment succeeded
        //iterate each cart item and check if quantity didnt update if yes update cart and deny request
        for (Map.Entry<Book, Integer> entry : basket.getCart().entrySet()) {
            Integer curQuantity = service.getRepo().findById(entry.getKey().getId())
                    .orElseThrow(() -> new IllegalArgumentException("ooops")).getQuantity();
            if (entry.getValue() > curQuantity) {
                approved = false;
                basket.getCart().put(entry.getKey(), curQuantity);

            }
        }
        if (!approved) {
            return ResponseEntity.status(400).body("The Stock has updated...Please try again");
        }
        Payment pay = new Payment();//initialize new payment and set details
        pay.setAmount(basket.getTotalPrice());
        pay.date(LocalDate.now());
        paymentService.getRepo().save(pay);
        //update each book's quantity
        for (Map.Entry<Book, Integer> entry : basket.getCart().entrySet()) {
            Book cur = service.getRepo().findById(entry.getKey().getId())
                    .orElseThrow(() -> new IllegalArgumentException("ooops"));
            cur.setQuantity(cur.getQuantity() - entry.getValue());
            service.getRepo().save(cur);
        }
        basket.emptyBasket();//clear the cart
        return ResponseEntity.status(200).body(basket.getTotalQuantity().toString());
    }

    /**
     * a route to delete an item from the cart
     * @param id - the book's id
     * @param model - the view
     * @return - respond with status and body
     */
    @PostMapping("/deletebasket/{id}")
    public ResponseEntity<String> deleteFromBasket(@PathVariable(name = "id") Long id, Model model) {
        Book book = service.getRepo().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
        basket.removeBook(book);
        return ResponseEntity.status(200).body(basket.getTotalQuantity().toString());
    }

}
