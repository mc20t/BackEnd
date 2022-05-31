package org.example.Lesha_REST.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Lesha_REST.model.Account;
import org.example.Lesha_REST.model.Book;
import org.example.Lesha_REST.model.Product;
import org.example.Lesha_REST.service.GlobalService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBDataLoader {
    public static void loadData(GlobalService globalService, String path, Object overwrite) {
        // реализацию данного метода можно было сделать красивее

        Logger logger = Loggers.getLogger(DBDataLoader.class.getName());

        Optional<Account> firstAccount = globalService.getFirstAccount();

        if (firstAccount.isPresent() && (overwrite == null || !(boolean) overwrite)) {
            logger.log(Level.INFO,"Data already loaded. You can overwrite it with -force flag");
            return;
        }

        File jsonFile = new File(path);
        if (!jsonFile.exists() || !jsonFile.isFile()) {
            logger.log(Level.WARNING, "Init data" + path + " file not found");
            return;
        }
        System.out.println();
        logger.log(Level.INFO,"Loading data...");

        ObjectMapper mapper = new ObjectMapper();

        Map<?, ?> data;
        try {
            data = mapper.readValue(jsonFile, Map.class);
        } catch (IOException e) {
            logger.log(Level.WARNING,"Error loading data: " + e);
            return;
        }

        Account account = null;
        List<Product> productList = new ArrayList<>();

        try {
            for (Map.Entry<?, ?> entry : data.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (key.equals("account")) {
                    double money = Double.parseDouble(String.valueOf(((Map<?, ?>) (value)).get("money")));
                    account = new Account(money);
                }
                if (key.equals("books")) {
                    for (Map<?, ?> productMap : (List<Map<?, ?>>) (value)) {
                        String bookAuthor = (String) productMap.get("author");
                        String bookName = (String) productMap.get("name");
                        double bookPrice = ((Number) productMap.get("price")).doubleValue();
                        int bookAmount = ((Number) productMap.get("amount")).intValue();
                        productList.add(new Product(new Book(bookName, bookAuthor), bookPrice, bookAmount));
                    }
                }
            }
            globalService.saveAccount(account);
            globalService.saveAllProducts(productList);
            logger.log(Level.INFO,"Data Successfully Loaded");
        } catch (Exception e) {
            logger.log(Level.WARNING,"Error loading data: " + e);
        }

    }
}
