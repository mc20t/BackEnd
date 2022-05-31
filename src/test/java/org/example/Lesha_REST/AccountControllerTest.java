package org.example.Lesha_REST;

import org.example.Lesha_REST.model.Account;
import org.example.Lesha_REST.service.GlobalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GlobalService globalService;

    Account account = new Account(1, 20000);

    @Test
    public void getAccountTest() throws Exception {
        Mockito.when(globalService.getFirstAccount()).thenReturn(Optional.ofNullable(account));
        this.mockMvc
                .perform(get("/account"))
                .andDo(print())
                .andExpect(content().string(containsString("{\"books\":[],\"balance\":20000.0}")));
    }

}
