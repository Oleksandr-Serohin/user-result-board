package com.info.user.result.board;

import com.info.user.result.board.controller.UserResultController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private UserResultController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }
}