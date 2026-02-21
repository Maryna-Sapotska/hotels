package org.example.hotels.search;

import jakarta.persistence.criteria.Join;
import org.example.hotels.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class HotelSpecification {
    public static Specification<Hotel> byParams(String name, String brand, String city, String country, List<String> amenities) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (brand != null) {
                predicates.add(criteriaBuilder.equal(root.get("brand"), brand));
            }

            if (city != null) {
                predicates.add(criteriaBuilder.equal(root.get("address").get("city"), city));
            }

            if (country != null) {
                predicates.add(criteriaBuilder.equal(root.get("address").get("country"), country));
            }

            if (amenities != null && !amenities.isEmpty()) {
                Join<Hotel, String> join = root.join("amenities");
                predicates.add(join.in(amenities));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
