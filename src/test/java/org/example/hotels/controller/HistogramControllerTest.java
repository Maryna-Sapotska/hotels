package org.example.hotels.controller;

import org.example.hotels.entity.Address;
import org.example.hotels.entity.Hotel;
import org.example.hotels.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class HistogramControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private HotelRepository hotelRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        hotelRepository.deleteAll();

        Address addr1 = new Address();
        addr1.setCity("Minsk");
        addr1.setCountry("Belarus");

        Address addr2 = new Address();
        addr2.setCity("Moscow");
        addr2.setCountry("Russia");

        Address addr3 = new Address();
        addr3.setCity("Moscow");
        addr3.setCountry("Russia");

        Hotel hotel1 = new Hotel();
        hotel1.setName("Hotel1");
        hotel1.setBrand("BrandA");
        hotel1.setAddress(addr1);
        hotel1.setAmenities(List.of("Free WiFi", "Free parking"));

        Hotel hotel2 = new Hotel();
        hotel2.setName("Hotel2");
        hotel2.setBrand("BrandB");
        hotel2.setAddress(addr2);
        hotel2.setAmenities(List.of("Free WiFi", "Non-smoking rooms"));

        Hotel hotel3 = new Hotel();
        hotel3.setName("Hotel3");
        hotel3.setBrand("BrandB");
        hotel3.setAddress(addr3);
        hotel3.setAmenities(List.of("Free WiFi", "Fitness center"));

        hotelRepository.saveAll(List.of(hotel1, hotel2, hotel3));
    }

    @Test
    void testHistogramCity() throws Exception {
        mockMvc.perform(get("/histogram/city").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Minsk").value(1))
                .andExpect(jsonPath("$.Moscow").value(2));
    }

    @Test
    void testHistogramCountry() throws Exception {
        mockMvc.perform(get("/histogram/country").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Belarus").value(1))
                .andExpect(jsonPath("$.Russia").value(2));
    }

    @Test
    void testHistogramBrand() throws Exception {
        mockMvc.perform(get("/histogram/brand").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.BrandA").value(1))
                .andExpect(jsonPath("$.BrandB").value(2));
    }

    @Test
    void testHistogramAmenities() throws Exception {
        mockMvc.perform(get("/histogram/amenities").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Free WiFi']").value(3))
                .andExpect(jsonPath("$.['Free parking']").value(1))
                .andExpect(jsonPath("$.['Non-smoking rooms']").value(1))
                .andExpect(jsonPath("$.['Fitness center']").value(1));
    }

    @Test
    void testInvalidParam() throws Exception {
        mockMvc.perform(get("/histogram/invalid").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Invalid histogram parameter"));
    }
}