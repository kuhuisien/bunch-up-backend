package com.springboot.bunch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.springboot.bunch.controller.BunchController;
import com.springboot.bunch.payload.BunchDto;
import com.springboot.bunch.payload.BunchResponse;
import com.springboot.bunch.service.BunchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { BunchController.class }, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class BunchRestControllerMvcTest {

    public static final String BUNCHES_URL = "/api/v1/bunches/";
    public static final int BUNCH_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BunchService bunchService;


    @Test
    public void testFindAll() throws Exception {
        BunchResponse bunchResponse = new BunchResponse();
        bunchResponse.setPageNo(1);
        bunchResponse.setPageSize(10);
        bunchResponse.setTotalPages(3);

        when(bunchService.getAllBunches(anyInt(), anyInt(), any(), any(), any()))
                .thenReturn(bunchResponse);

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mockMvc.perform(get(BUNCHES_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(bunchResponse)));
    }

    @Test
    public void testFindById() throws Exception {
        BunchDto bunchDto = buildBunchDto();

        when(bunchService.getBunchById(BUNCH_ID)).thenReturn(bunchDto);

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mockMvc.perform(get(BUNCHES_URL + BUNCH_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(bunchDto)));
    }

    private BunchDto buildBunchDto() {
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
