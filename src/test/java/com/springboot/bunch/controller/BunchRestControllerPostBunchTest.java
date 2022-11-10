package com.springboot.bunch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.springboot.bunch.payload.BunchDto;
import com.springboot.bunch.service.BunchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration
public class BunchRestControllerPostBunchTest {

    public static final String BUNCHES_URL = "/api/v1/bunches/";

    @Autowired
    WebApplicationContext wac;

    @MockBean
    private BunchService bunchService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin")
    public void testCreateValidBunch() throws Exception {
        BunchDto bunchDto = buildValidBunchDto();

        when(bunchService.createBunch(any(), anyString())).thenReturn(bunchDto);

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mockMvc.perform(post(BUNCHES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(bunchDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectWriter.writeValueAsString(bunchDto)));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testCreateInvalidBunch() throws Exception {
        BunchDto bunchDto = buildValidBunchDto();
        bunchDto.setEmail("invalidEmail");

        when(bunchService.createBunch(any(), anyString())).thenReturn(bunchDto);

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mockMvc.perform(post(BUNCHES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(bunchDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBunchNotAuth() throws Exception {
        BunchDto bunchDto = buildValidBunchDto();

        when(bunchService.createBunch(any(), anyString())).thenReturn(bunchDto);

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mockMvc.perform(post(BUNCHES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(bunchDto)))
                .andExpect(status().isUnauthorized());
    }

    private BunchDto buildValidBunchDto() {
        BunchDto bunchDto = new BunchDto();
        bunchDto.setTitle("dummyTitle");
        bunchDto.setSubtitle("dummySubtitle");
        bunchDto.setDescription("dummyDescription");
        bunchDto.setImageUrl("dummyImageUrl");
        bunchDto.setAddress("address");
        bunchDto.setEmail("validEmail@hotmail.com");
        return bunchDto;
    }
}