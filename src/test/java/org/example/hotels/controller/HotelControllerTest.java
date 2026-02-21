package org.example.hotels.controller;

import org.example.hotels.dto.AddressDTO;
import org.example.hotels.dto.ContactsDTO;
import org.example.hotels.dto.HotelCreateDTO;
import org.example.hotels.entity.Address;
import org.example.hotels.entity.Contacts;
import org.example.hotels.entity.Hotel;
import org.example.hotels.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@Transactional
class HotelControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private HotelRepository hotelRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Hotel hotel1;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        hotelRepository.deleteAll();

        hotel1 = new Hotel();
        hotel1.setName("Hilton Minsk");
        hotel1.setBrand("Hilton");
        hotel1.setDescription("Luxury hotel in Minsk");

        Address address = new Address();
        address.setHouseNumber(10);
        address.setStreet("Lenina");
        address.setCity("Minsk");
        address.setCountry("Belarus");
        address.setPostCode("220000");
        hotel1.setAddress(address);

        Contacts contacts = new Contacts();
        contacts.setPhone("+375291234567");
        contacts.setEmail("hilton@hotel.com");
        hotel1.setContacts(contacts);

        hotelRepository.save(hotel1);
    }

    @Test
    void testGetAllHotels() throws Exception {
        mockMvc.perform(get("/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Hilton Minsk")));
    }

    @Test
    void testGetHotelById() throws Exception {
        mockMvc.perform(get("/hotels/{id}", hotel1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Hilton Minsk")));
    }

    @Test
    void testCreateHotel() throws Exception {
        HotelCreateDTO dto = new HotelCreateDTO();
        dto.setName("Marriott Moscow");
        dto.setBrand("Marriott");
        dto.setDescription("Central Moscow hotel");

        AddressDTO addressDTO = new AddressDTO(5, "Tverskaya", "Moscow", "Russia", "125009");
        dto.setAddress(addressDTO);

        ContactsDTO contactsDTO = new ContactsDTO("+74951234567", "marriott@hotel.com");
        dto.setContacts(contactsDTO);

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Marriott Moscow")))
                .andExpect(jsonPath("$.description", is("Central Moscow hotel")));
    }

    @Test
    void testAddAmenities() throws Exception {
        List<String> amenities = List.of("Free WiFi", "Breakfast included");

        mockMvc.perform(post("/hotels/{id}/amenities", hotel1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amenities)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", hasItem("Free WiFi")))
                .andExpect(jsonPath("$", hasItem("Breakfast included")));
    }
}