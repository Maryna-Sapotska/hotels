package org.example.hotels.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@Schema(name = "Hotel", description = "Сущность отеля")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор отеля", example = "1")
    private Long id;

    @Schema(description = "Название отеля", example = "Hilton Minsk")
    private String name;

    @Schema(description = "Описание отеля", example = "Роскошный отель в центре города")
    private String description;

    @Schema(description = "Бренд отеля", example = "Hilton")
    private String brand;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "houseNumber", column = @Column(name = "house_number")),
            @AttributeOverride(name = "postCode", column = @Column(name = "post_code"))
    })
    @Schema(description = "Адрес отеля")
    private Address address;

    @Embedded
    @Schema(description = "Контактная информация отеля")
    private Contacts contacts;

    @Embedded
    @Schema(description = "Время заезда и выезда")
    private ArrivalTime arrivalTime;

    @ElementCollection
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    @Schema(description = "Список удобств отеля", example = "[\"Free WiFi\", \"Pool\"]")
    private List<String> amenities =  new ArrayList<>();
}
