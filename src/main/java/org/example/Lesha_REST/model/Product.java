package org.example.Lesha_REST.model;


import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "products", indexes = {@Index(name = "products_table_id_idx", columnList = "id", unique = true)})
@DynamicUpdate
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "price")
    private double price;

    @Column(name = "amount")
    private int amount;

    public Product(long id, Book book, double price, int amount) {
        this.id = id;
        this.book = book;
        this.price = price;
        this.amount = amount;
    }

    public Product(long id, long bookID, String bookName, String bookAuthor, double price, int amount){
        this(id, new Book(bookID, bookName, bookAuthor), price, amount);
    }

    public Product(Book book, double price, int amount) {
        this.book = book;
        this.price = price;
        this.amount = amount;
    }

    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", book=" + book +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
