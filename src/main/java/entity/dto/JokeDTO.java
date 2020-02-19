package entity.dto;

import entity.Joke;

public class JokeDTO {
    private Long id;
    private String text;
    private String reference;
    private String type;

    public JokeDTO(Joke joke) {
        this.id = joke.getId();
        this.text = joke.getText();
        this.reference = joke.getReference();
        this.type = joke.getType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
