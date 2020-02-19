package entity;
/*
 * author benjaminp
 * version 1.0
 */

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "Joke")
@NamedNativeQuery(name = "Joke.Truncate", query = "TRUNCATE TABLE Joke")
@NamedQueries({
    @NamedQuery(name = "Joke.deleteAllRows", query = "DELETE FROM Joke"),
    @NamedQuery(name = "Joke.getCount", query = "SELECT count(j) FROM Joke j"),
    @NamedQuery(name = "Joke.getAll", query = "SELECT j FROM Joke j")
})
public class Joke implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String reference;
    private String type;

    public Joke() {
    }

    public Joke(String text, String reference, String type) {
        this.text = text;
        this.reference = reference;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
