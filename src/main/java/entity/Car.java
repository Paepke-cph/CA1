package entity;
/*
 * author benjaminp
 * version 1.0
 */

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


@Entity
@Table(name = "Car")
@NamedNativeQuery(name = "Car.Truncate", query = "TRUNCATE TABLE Car")
@NamedQueries({
    @NamedQuery(name = "Car.deleteAllRows", query = "DELETE FROM Car"),
    @NamedQuery(name = "Car.getAll", query = "SELECT c FROM Car c")
})
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int year;
    private String make;
    private String model;
    private int price;

    private String owner;

    public Car() {}
    public Car(int year, String make, String model, int price, String owner) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.price = price;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
