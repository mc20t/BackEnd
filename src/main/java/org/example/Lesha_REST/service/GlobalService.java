package org.example.Lesha_REST.service;

import org.example.Lesha_REST.model.Account;
import org.example.Lesha_REST.model.AccountBook;
import org.example.Lesha_REST.model.Book;
import org.example.Lesha_REST.model.Product;
import org.example.Lesha_REST.repository.AccountBookRepository;
import org.example.Lesha_REST.repository.AccountRepository;
import org.example.Lesha_REST.repository.BookRepository;
import org.example.Lesha_REST.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class GlobalService {
    @Autowired
    private AccountBookRepository accountBookRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ProductRepository productRepository;

    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    public Optional<Account> getAccountById(long id){
        return accountRepository.getAccountById(id);
    }

    public Optional<Account> getFirstAccount(){
        return accountRepository.findFirstBy();
    }

    public void saveBook(Book book){
        bookRepository.save(book);
    }

    public Optional<Book> getBookById(long id){
        return bookRepository.getBookById(id);
    }

    public void saveAccountBook(AccountBook accountBook){
        accountBookRepository.save(accountBook);
    }

    public Optional<AccountBook> getAccountBookById(long id){
        return accountBookRepository.getAccountBookById(id);
    }

    public Optional<AccountBook> getAccountBookByAccountAndBook(Account account, Book book){
        return accountBookRepository.getAccountBookByAccountAndBook(account, book);
    }

    public List<AccountBook> getAccountBooksByAccount(Account account){
        return accountBookRepository.getAccountBooksByAccount(account);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public Optional<Product> getProductById(long id){
        return productRepository.getProductById(id);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void saveAllProducts(List<Product> productList){
        productRepository.saveAll(productList);
    }


}
