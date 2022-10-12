package com.berkay22demirel.readingisgood;

import com.berkay22demirel.readingisgood.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReadingIsGoodApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
        System.out.println("sadf");
    }

}
