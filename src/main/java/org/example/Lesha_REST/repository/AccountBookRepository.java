package org.example.Lesha_REST.repository;

import org.example.Lesha_REST.model.Account;
import org.example.Lesha_REST.model.AccountBook;
import org.example.Lesha_REST.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {
    List<AccountBook> getAccountBooksByAccount(Account account);
    Optional<AccountBook> getAccountBookById(long id);

    Optional<AccountBook> getAccountBookByAccountAndBook(Account account, Book book);
}
