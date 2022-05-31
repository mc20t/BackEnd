package org.example.Lesha_REST.controller;

import org.example.Lesha_REST.model.Account;
import org.example.Lesha_REST.model.AccountBook;
import org.example.Lesha_REST.service.GlobalService;
import org.example.Lesha_REST.utils.Loggers;
import org.example.Lesha_REST.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private GlobalService globalService;

    private final Logger logger = Loggers.getLogger(this.getClass().getName());

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Map<?, ?>> getAccount() {
        logger.log(Level.INFO, "GET /account");
        Optional<Account> wrappedAccount = globalService.getFirstAccount();
        if (wrappedAccount.isEmpty()) {
            return Utils.logAndSendErrorResponse(logger, HttpStatus.NOT_FOUND, "Account not found, load data first");
        }

        Account account = wrappedAccount.get();

        List<AccountBook> books = globalService.getAccountBooksByAccount(account);

        Map<String, Object> response = new HashMap<>();
        response.put("balance", account.getMoney());
        response.put("books", books);

        return ResponseEntity.ok(response);
    }

}
