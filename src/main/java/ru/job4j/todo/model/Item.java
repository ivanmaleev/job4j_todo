package ru.job4j.todo.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "item")
public class Item {
    private int id;
    private String description;
    private Timestamp created;
    private boolean done;

    public Item(int id, String description, boolean done) {
        this.id = id;
        this.description = description;
        created = Timestamp.from(Instant.now());
        this.done = done;
    }

    public Item(int id, String description, Timestamp created,  boolean done) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public Item() {
        created = Timestamp.from(Instant.now());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
