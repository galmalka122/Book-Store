package hac.ex4.repos.Payments;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Payment implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Positive
    private Double amount;

    @CreationTimestamp
    private LocalDate date;

    public Payment() {
    }

    public Payment(Double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Payment amount(Double amount) {
        setAmount(amount);
        return this;
    }

    public Payment date(LocalDate date) {
        setDate(date);
        return this;
    }

}
