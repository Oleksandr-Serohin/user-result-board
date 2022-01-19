package com.info.user.result.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.user.result.board.controller.UserResultController;
import com.info.user.result.board.entity.UserResult;
import com.info.user.result.board.services.UserResultServices;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserResultController.class)
public class WebMockTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserResultServices userResultServices;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        UserResult user1 = new UserResult(1,1,1);
//        user1.setUser_id(1);
//        user1.setLevel_id(1);
//        user1.setResult(1);

        Mockito.when(userResultServices.setUserResult(user1)).thenReturn(AsyncResult.forValue(true));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/setinfo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }
}

