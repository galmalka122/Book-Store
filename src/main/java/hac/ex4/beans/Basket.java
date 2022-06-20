package hac.ex4.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import hac.ex4.repos.book.Book;

/**
 * a class to manage a payment entity
 */
@Component(value = "basket")
public class Basket implements Serializable {


    private Map<Book, Integer> cart;//the cart list
    private Double totalPrice;//the total price of the cart
    private Integer totalQuantity;//the total items in cart

    /**
     * init the cart
     */
    public Basket() {
        this.cart = new HashMap<>();
        this.totalPrice = 0.;
        this.totalQuantity = 0;
    }

    /**
     * add a book to cart and update the content
     * @param book - the book
     */
    public void addBook(Book book) {
        if (!this.cart.containsKey(book)) { //check if this is the first copy
            this.cart.put(book, 1);
        } else
            this.cart.put(book, this.cart.get(book) + 1);
        this.totalPrice += book.getPriceAfterDiscount();
        this.totalQuantity += 1;
    }

    /**
     * remove a book to cart and update the content
     * @param book - the book
     */
    public void removeBook(Book book) {
        if (this.cart.containsKey(book)) {
            this.totalPrice -= cart.get(book) * book.getPriceAfterDiscount();
            this.totalQuantity -= cart.get(book);
            this.cart.remove(book);
        }
    }

    /**
     * get the cart
     * @return - the cart
     */
    public Map<Book, Integer> getCart() {
        return this.cart;
    }

    /**
     * get the total price
     * @return - the total price
     */
    public Double getTotalPrice() {
        return this.totalPrice;
    }

    /**
     * get the total quantity
     * @return - the total quantity
     */
    public Integer getTotalQuantity() {
        return this.totalQuantity;
    }

    /**
     * clears all items from the basket
     */
    public void emptyBasket() {
        this.cart.clear();
    }

    /**
     * initializes a json respond to client
     * @return - json with cart content
     */
    public List<Map<String, String>> getBasketString() {
        List<Map<String, String>> basketString = new ArrayList<>();
        //appends each book details in the cart
        for (Map.Entry<Book, Integer> entry : this.cart.entrySet()) {
            Map<String, String> cur = new HashMap<>();
            cur.put("name", entry.getKey().getName());
            cur.put("price", Double.toString(entry.getKey().getPriceAfterDiscount() * entry.getValue()));
            cur.put("quantity", entry.getValue().toString());
            cur.put("id", entry.getKey().getId().toString());
            basketString.add(cur);
        }
        return basketString;
    }
}
