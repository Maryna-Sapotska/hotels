package org.example.hotels.controller;

import org.example.hotels.entity.Address;
import org.example.hotels.entity.Contacts;
import org.example.hotels.entity.Hotel;
import org.example.hotels.mapper.HotelMapper;
import org.example.hotels.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class SearchControllerTest {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelMapper hotelMapper;

    private MockMvc mockMvc;

    private SearchController searchController;

    private Hotel hotel1;
    private Hotel hotel2;

    @BeforeEach
    void setup() {
        // Инициализируем контроллер вручную
        searchController = new SearchController(hotelService, hotelMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();

        // Создаем тестовые отели
        hotel1 = new Hotel();
        hotel1.setName("Hilton Minsk");
        hotel1.setBrand("Hilton");
        hotel1.setDescription("Luxury hotel in Minsk");
        Address address1 = new Address();
        address1.setCity("Minsk");
        address1.setCountry("Belarus");
        address1.setStreet("Lenina");
        address1.setHouseNumber(10);
        Contacts contact1 = new Contacts();
        contact1.setPhone("+375441234567");
        contact1.setEmail("test@example.com");
        hotel1.setContacts(contact1);
        hotel1.setAddress(address1);
        hotel1.setAmenities(List.of("Free WiFi", "Free parking"));
        hotelService.create(hotel1);

        hotel2 = new Hotel();
        hotel2.setName("Marriott Moscow");
        hotel2.setBrand("Marriott");
        hotel2.setDescription("Business hotel in Moscow");
        Address address2 = new Address();
        address2.setCity("Moscow");
        address2.setCountry("Russia");
        address2.setStreet("Tverskaya");
        address2.setHouseNumber(5);
        Contacts contact2 = new Contacts();
        contact2.setPhone("+74951234567");
        contact2.setEmail("test2@example.com");
        hotel2.setContacts(contact2);
        hotel2.setAddress(address2);
        hotel2.setAmenities(List.of("Free WiFi", "Fitness center"));
        hotelService.create(hotel2);
    }

    @Test
    void testSearchByCity() throws Exception {
        mockMvc.perform(get("/search")
                        .param("city", "Minsk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].address").value(org.hamcrest.Matchers.containsString("Minsk")));
    }

    @Test
    void testSearchByBrand() throws Exception {
        mockMvc.perform(get("/search")
                        .param("brand", "Marriott")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").isNotEmpty())
                .andExpect(jsonPath("$[0].address").isNotEmpty());
    }

    @Test
    void testSearchByAmenities() throws Exception {
        mockMvc.perform(get("/search")
                        .param("amenities", "Free WiFi")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[1].name").exists());
    }

    @Test
    void testSearchWithoutParams() throws Exception {
        mockMvc.perform(get("/search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[1].name").exists());
    }
}