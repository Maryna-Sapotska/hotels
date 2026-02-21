package org.example.hotels.service;

import lombok.RequiredArgsConstructor;
import org.example.hotels.entity.Hotel;
import org.example.hotels.repository.HotelRepository;
import org.example.hotels.search.HotelSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository repository;

    public List<Hotel> getAll() {
        return repository.findAll();
    }

    public Hotel getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public Hotel create(Hotel hotel) {
        return repository.save(hotel);
    }

    public List<String> addAmenities(Long id, List<String>  amenities) {
        Hotel hotel = getById(id);
        hotel.getAmenities().addAll(amenities);
        repository.save(hotel);
        return hotel.getAmenities();
    }

    public List<Hotel> search(String name, String brand, String city, String country, List<String> amenities) {
        Specification<Hotel> spec = HotelSpecification.byParams(name, brand, city, country, amenities);
        return repository.findAll(spec);
    }
}
