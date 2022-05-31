package org.example.Lesha_REST;

import org.example.Lesha_REST.model.Account;
import org.example.Lesha_REST.model.Book;
import org.example.Lesha_REST.model.Product;
import org.example.Lesha_REST.service.GlobalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
public class MarketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GlobalService globalService;

    Account account = new Account(1, 20000);

    Book book = new Book(1, "Test Book", "Test Author");

    Product product = new Product(1, book, 1000, 10);

    Product expensiveProduct = new Product(2, book, 1000000, 10);

    List<Product> productList = List.of(product, expensiveProduct);

    @Test
    public void marketListTest() throws Exception {
        Mockito.when(globalService.getAllProducts()).thenReturn(productList);

        this.mockMvc
                .perform(get("/market"))
                .andDo(print())
                .andExpect(content()
                        .string(containsString("{\"products\":[{\"id\":1,\"book\":{\"name\":\"Test Book\",\"author\":\"Test Author\"},\"price\":1000.0,\"amount\":10},{\"id\":2,\"book\":{\"name\":\"Test Book\",\"author\":\"Test Author\"},\"price\":1000000.0,\"amount\":10}]}")));
    }

    @Test
    public void marketDealBadJsonTest() throws Exception {
        this.mockMvc
                .perform(post("/market/deal").contentType(APPLICATION_JSON)
                        .content("{badJSON}"))
                .andDo(print())
                .andExpect(content()
                        .string(containsString("{\"msg\":\"Bad request(Request parse error)\",\"code\":\"400\",\"status\":\"error\"}")));
    }

    @Test
    public void marketDealBadValuesTest() throws Exception {
        this.mockMvc
                .perform(post("/market/deal").contentType(APPLICATION_JSON)
                        .content("{\"id\": \"badValue\", \"amount\": \"badValue\"}"))
                .andDo(print())
                .andExpect(content()
                        .string(containsString("{\"msg\":\"Bad request(Cast error)\",\"code\":\"400\",\"status\":\"error\"}")));
    }

    @Test
    public void marketDealBadProductId() throws Exception{
        Mockito.when(globalService.getProductById(1)).thenReturn(Optional.of(product));
        this.mockMvc
                .perform(post("/market/deal").contentType(APPLICATION_JSON)
                        .content("{\"id\": 100, \"amount\": 1}"))
                .andDo(print())
                .andExpect(content()
                        .string(containsString("{\"msg\":\"Product not found\",\"code\":\"400\",\"status\":\"error\"}")));
    }

    @Test
    public void marketDealBadProductAmount() throws Exception{
        Mockito.when(globalService.getProductById(1)).thenReturn(Optional.of(product));
        this.mockMvc
                .perform(post("/market/deal").contentType(APPLICATION_JSON)
                        .content("{\"id\": 1, \"amount\": 1000}"))
                .andDo(print())
                .andExpect(content()
                        .string(containsString("{\"msg\":\"Not enough product\",\"code\":\"400\",\"status\":\"error\"}")));
    }

    @Test
    public void marketDealAccountNotFound() throws Exception{
        Mockito.when(globalService.getFirstAccount()).thenReturn(Optional.empty());
        Mockito.when(globalService.getProductById(1)).thenReturn(Optional.of(product));
        this.mockMvc
                .perform(post("/market/deal").contentType(APPLICATION_JSON)
                        .content("{\"id\": 1, \"amount\": 1}"))
                .andDo(print())
                .andExpect(content()
                        .string(containsString("{\"msg\":\"Account not found, load data first\",\"code\":\"404\",\"status\":\"error\"}")));
    }

    @Test
    public void marketDealNotEnoughMoney() throws Exception{
        Mockito.when(globalService.getFirstAccount()).thenReturn(Optional.of(account));
        Mockito.when(globalService.getProductById(2)).thenReturn(Optional.of(expensiveProduct));

        this.mockMvc
                .perform(post("/market/deal").contentType(APPLICATION_JSON)
                        .content("{\"id\": 2, \"amount\": 1}"))
                .andDo(print())
                .andExpect(content()
                        .string(containsString("{\"msg\":\"Not enough money\",\"code\":\"400\",\"status\":\"error\"}")));
    }

    @Test
    public void marketDealSuccessful() throws Exception{
        Mockito.when(globalService.getFirstAccount()).thenReturn(Optional.of(account));
        Mockito.when(globalService.getProductById(1)).thenReturn(Optional.of(product));

        this.mockMvc
                .perform(post("/market/deal").contentType(APPLICATION_JSON)
                        .content("{\"id\": 1, \"amount\": 1}"))
                .andDo(print())
                .andExpect(content()
                        .string(containsString("{\"status\":\"successful\"}")));
    }
}
