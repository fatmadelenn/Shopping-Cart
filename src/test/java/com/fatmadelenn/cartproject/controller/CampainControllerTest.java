package com.fatmadelenn.cartproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatmadelenn.cartproject.model.Category;
import com.fatmadelenn.cartproject.model.DiscountType;
import com.fatmadelenn.cartproject.model.dto.CampainDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CampainControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void createNewCampain() throws Exception {
        double discount = 20.0;
        double numberOfProducts = 2;
        DiscountType discountType = DiscountType.RATE;

        Category category = new Category("dress");

        CampainDTO campaign = new CampainDTO();
        campaign.setCategory(category);
        campaign.setDiscount(discount);
        campaign.setNumberOfProducts(numberOfProducts);
        campaign.setDiscountType(discountType);

        MvcResult mvcResult = mockMvc.perform(
                post("/api/campain")
                        .content(objectMapper.writeValueAsString(campaign))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(equalTo(200)));
    }

}
