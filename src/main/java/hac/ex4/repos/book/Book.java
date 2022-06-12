package hac.ex4.repos.book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @URL
    private String image;

    @PositiveOrZero(message = "Quantity must be equal or greater than zero")
    @NotNull(message = "Quantity is mandatory")
    private Integer quantity;

    @Positive(message = "Price must be greater than zero")
    @NotNull(message = "Price is mandatory")
    private Double price;

    @PositiveOrZero(message = "Discount must be equal or greater than zero")
    @NotNull(message = "Discount is mandatory")
    private Double discount;

    public Book() {
    }

    public Book(String name, String image, Integer quantity, Double price, Double discount) {
        this.name = name;
        this.image = image == null ? "../../../../resources/static/asserts/default_cover.jpg" : image;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

}
