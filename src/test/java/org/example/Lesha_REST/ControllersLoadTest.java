package org.example.Lesha_REST;

import org.example.Lesha_REST.controller.AccountController;
import org.example.Lesha_REST.controller.MarketController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ControllersLoadTest {
    @Autowired
    private AccountController accountController;

    @Autowired
    private MarketController marketController;

    @Test
    public void contextLoads() throws Exception{
        assertThat(accountController).isNotNull();
        assertThat(marketController).isNotNull();
    }

}
