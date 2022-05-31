package org.example.Lesha_REST.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "account_books", indexes = {@Index(name = "account_books_table_id_idx",columnList = "id", unique = true),
@Index(name = "account_books_table_account_id_idx", columnList = "account_id", unique = false)
})
@DynamicUpdate
public class AccountBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "amount")
    private int amount;

    public AccountBook(Account account, Book book, int amount) {
        this.account = account;
        this.book = book;
        this.amount = amount;
    }

    public AccountBook(long id, Account account, Book book, int amount) {
        this.id = id;
        this.account = account;
        this.book = book;
        this.amount = amount;
    }

    public AccountBook(){

    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonIgnore
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AccountBook{" +
                "id=" + id +
                ", account=" + account +
                ", book=" + book +
                ", amount=" + amount +
                '}';
    }
}
