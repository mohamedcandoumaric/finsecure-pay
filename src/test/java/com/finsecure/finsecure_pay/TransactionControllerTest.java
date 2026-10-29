
package com.finsecure.finsecure_pay;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void validTransactionShouldBeApproved() throws Exception {
        mockMvc.perform(post("/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "transactionId": "TXN1001",
                      "amount": 150.50,
                      "currency": "SGD"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    void zeroAmountShouldBeRejected() throws Exception {
        mockMvc.perform(post("/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "transactionId": "TXN1002",
                      "amount": 0,
                      "currency": "SGD"
                    }
                    """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("REJECTED"));
    }

    @Test
    void unsupportedCurrencyShouldBeRejected() throws Exception {
        mockMvc.perform(post("/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "transactionId": "TXN1003",
                      "amount": 100,
                      "currency": "XYZ"
                    }
                    """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("REJECTED"));
    }
}
