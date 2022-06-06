package hac.ex4.repos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @URL
    private String image;

    @PositiveOrZero(message = "Quantity must be equal or greater than zero")
    @NotNull(message = "Quantity is mandatory")
    private int quantity;

    @Positive(message = "Price must be greater than zero")
    @NotNull(message = "Price is mandatory")
    private double price;

    @PositiveOrZero(message = "Discount must be equal or greater than zero")
    @NotNull(message = "Discount is mandatory")
    private double discount;

    public Book() {
    }

    public Book(String name, String image, int quantity, double price, double discount) {
        this.name = name;
        this.image = image == null ? "../../../../resources/static/asserts/default_cover.jpg" : image;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}
