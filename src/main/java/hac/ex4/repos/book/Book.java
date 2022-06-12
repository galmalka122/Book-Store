package hac.ex4.repos.book;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

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

    public Book(Long id, String name, String image, Integer quantity, Double price, Double discount) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return this.quantity;
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
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @PrePersist
    void preInsert() {
        if (this.image == null || this.image.equals("")) {
            this.image = "/asserts/default_cover.jpg";
        }
    }
}
