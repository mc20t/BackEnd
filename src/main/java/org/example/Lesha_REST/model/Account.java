package org.example.Lesha_REST.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "accounts", indexes = {@Index(name = "accounts_table_id_idx",columnList = "id", unique = true)})
@DynamicUpdate
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "money")
    private double money;

    public Account(long id, double money){
        this.id = id;
        this.money = money;
    }

    public Account(double money) {
        this.money = money;
    }

    public Account() {}

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", money=" + money +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
