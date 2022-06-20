package hac.ex4.repos.book;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * a class to implement a book entity
 */
@Entity
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // the book id

    @NotBlank(message = "Name is mandatory")
    private String name; // the book name

    private String image; // the cover image

    @PositiveOrZero(message = "Quantity must be equal or greater than zero")
    @NotNull(message = "Quantity is mandatory")
    private Integer quantity; // the book quantity

    @Positive(message = "Price must be greater than zero")
    @NotNull(message = "Price is mandatory")
    private Double price; // the book price

    @PositiveOrZero(message = "Discount must be equal or greater than zero")
    @NotNull(message = "Discount is mandatory")
    private Double discount; // the book discount

    public Book() {
    }

    public Book(Long id, String name, String image, Integer quantity, Double price, Double discount) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }


    /**
     * @return - the book id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * update the book id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return - the book name
     */
    public String getName() {
        return this.name;
    }

    /**
     * update the book name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return - the book cover
     */
    public String getImage() {
        return this.image;
    }

    /**
     * update the book cover
     */
    public void setImage(String image) {
        if (image == null || image.equals("")) {
            this.image = "/resources/asserts/default_cover.jpg";
        } else
            this.image = image;
    }

    /**
     * @return - the book quantity
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * update the book quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return - the book price
     */
    public Double getPrice() {
        return this.price;
    }

    /**
     * update the book price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return - the book discount
     */
    public Double getDiscount() {
        return this.discount;
    }

    /**
     * @return - the book price after discount
     */
    public Double getPriceAfterDiscount() {
        BigDecimal bd = BigDecimal.valueOf(((this.price * (100 - this.discount)) / 100));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * update the book discount
     */
    public void setDiscount(Double discount) {
        this.discount = discount;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Book)) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(name, book.name) && Objects.equals(image, book.image)
                && Objects.equals(quantity, book.quantity) && Objects.equals(price, book.price)
                && Objects.equals(discount, book.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, quantity, price, discount);
    }

    @PrePersist
    /**
     * check if the image value is empty and initialize it with default image
     */
    void preInsert() {
        if (this.image == null || this.image.equals("")) {
            this.image = "/resources/asserts/default_cover.jpg";
        }
    }
}
