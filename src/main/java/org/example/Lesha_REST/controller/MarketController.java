package org.example.Lesha_REST.controller;

import org.example.Lesha_REST.model.Account;
import org.example.Lesha_REST.model.AccountBook;
import org.example.Lesha_REST.model.Product;
import org.example.Lesha_REST.service.GlobalService;
import org.example.Lesha_REST.utils.Loggers;
import org.example.Lesha_REST.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/market")
public class MarketController {

    @Autowired
    private GlobalService globalService;

    private final Logger logger = Loggers.getLogger(this.getClass().getName());

    @RequestMapping(method = RequestMethod.GET)
    public Map<?, ?> getBookList(){
        logger.log(Level.INFO, "GET /market");
        Map<String, Object> response = new HashMap<>();
        List<Product> products = globalService.getAllProducts();

        response.put("products", products);

        return response;
    }

    @RequestMapping(value = "/deal", method = RequestMethod.POST)
    public ResponseEntity<Map<?, ?>> deal(@RequestBody String body){
        logger.log(Level.INFO, "POST /market/deal");
        Map<?, ?> bodyMap = Utils.deserializeJsonOrNull(body);
        if(bodyMap == null){
            return Utils.logAndSendErrorResponse(logger, HttpStatus.BAD_REQUEST, "Bad request(Request parse error)");
        }
        long id = -1;
        int amount = -1;
        try {
            id = ((Number) bodyMap.get("id")).longValue();
            amount = ((Number) bodyMap.get("amount")).intValue();
        } catch (Exception e){
            return Utils.logAndSendErrorResponse(logger, HttpStatus.BAD_REQUEST, "Bad request(Cast error)");
        }

        Optional<Product> wrappedProduct = globalService.getProductById(id);
        if(wrappedProduct.isEmpty()){
            return Utils.logAndSendErrorResponse(logger, HttpStatus.BAD_REQUEST, "Product not found");
        }

        Product product = wrappedProduct.get();
        if(product.getAmount() < amount){
            return Utils.logAndSendErrorResponse(logger, HttpStatus.BAD_REQUEST, "Not enough product");
        }

        Optional<Account> wrappedAccount = globalService.getFirstAccount();
        if (wrappedAccount.isEmpty()) {
            return Utils.logAndSendErrorResponse(logger, HttpStatus.NOT_FOUND, "Account not found, load data first");
        }

        Account account = wrappedAccount.get();

        if(account.getMoney() < product.getPrice()*amount){
            return Utils.logAndSendErrorResponse(logger, HttpStatus.BAD_REQUEST, "Not enough money");
        }

        account.setMoney(account.getMoney()-product.getPrice()*amount);
        globalService.saveAccount(account);

        Optional<AccountBook> wrappedAccountBook = globalService.getAccountBookByAccountAndBook(account, product.getBook());
        AccountBook accountBook = wrappedAccountBook.orElseGet(() -> new AccountBook(account, product.getBook(), 0));
        accountBook.setAmount(accountBook.getAmount() + amount);
        globalService.saveAccountBook(accountBook);

        product.setAmount(product.getAmount()-amount);
        globalService.saveProduct(product);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "successful");

        return ResponseEntity.ok(response);
    }
}
