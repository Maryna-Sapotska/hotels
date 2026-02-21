package org.example.hotels.service;

import org.example.hotels.entity.Address;
import org.example.hotels.entity.Hotel;
import org.example.hotels.repository.HotelRepository;
import org.example.hotels.search.HotelSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceTest {
    @Mock
    private HotelRepository repository;

    @InjectMocks
    private HotelService service;

    private Hotel hotel1;
    private Hotel hotel2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Address address1 = new Address();
        address1.setCity("Minsk");
        address1.setCountry("Belarus");
        address1.setStreet("Lenina");
        address1.setHouseNumber(10);
        address1.setPostCode("220000");

        hotel1 = new Hotel();
        hotel1.setId(1L);
        hotel1.setName("Hilton Minsk");
        hotel1.setBrand("Hilton");
        hotel1.setAddress(address1);
        hotel1.setAmenities(new ArrayList<>());

        Address address2 = new Address();
        address2.setCity("Moscow");
        address2.setCountry("Russia");
        address2.setStreet("Tverskaya");
        address2.setHouseNumber(5);
        address2.setPostCode("101000");

        hotel2 = new Hotel();
        hotel2.setId(2L);
        hotel2.setName("Marriott Moscow");
        hotel2.setBrand("Marriott");
        hotel2.setAddress(address2);
        hotel2.setAmenities(new ArrayList<>());
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(hotel1, hotel2));

        List<Hotel> hotels = service.getAll();

        assertEquals(2, hotels.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetByIdFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(hotel1));

        Hotel found = service.getById(1L);

        assertEquals("Hilton Minsk", found.getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.getById(99L));
        assertEquals("Hotel not found", exception.getMessage());
    }

    @Test
    void testCreate() {
        when(repository.save(hotel1)).thenReturn(hotel1);

        Hotel created = service.create(hotel1);

        assertEquals("Hilton Minsk", created.getName());
        verify(repository, times(1)).save(hotel1);
    }

    @Test
    void testAddAmenities() {
        when(repository.findById(1L)).thenReturn(Optional.of(hotel1));
        when(repository.save(any(Hotel.class))).thenReturn(hotel1);

        List<String> amenities = List.of("Free WiFi", "Parking");
        List<String> result = service.addAmenities(1L, amenities);

        assertEquals(2, result.size());
        assertTrue(result.contains("Free WiFi"));
        assertTrue(result.contains("Parking"));

        // Проверяем, что репозиторий сохранил объект
        ArgumentCaptor<Hotel> captor = ArgumentCaptor.forClass(Hotel.class);
        verify(repository).save(captor.capture());
        assertEquals(2, captor.getValue().getAmenities().size());
    }

    @Test
    void testSearch() {
        List<Hotel> expected = List.of(hotel1);
        Specification<Hotel> spec = HotelSpecification.byParams("Hilton", null, null, null, null);
        when(repository.findAll(any(Specification.class))).thenReturn(expected);

        List<Hotel> result = service.search("Hilton", null, null, null, null);

        assertEquals(1, result.size());
        assertEquals("Hilton Minsk", result.get(0).getName());
        verify(repository, times(1)).findAll(any(Specification.class));
    }
}