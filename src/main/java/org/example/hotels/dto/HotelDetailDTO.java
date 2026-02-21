package org.example.hotels.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "HotelDetailDTO", description = "Полная информация об отеле")
public class HotelDetailDTO {
    @Schema(description = "ID отеля", example = "1")
    private Long id;

    @Schema(description = "Название отеля", example = "Hilton Minsk")
    private String name;

    @Schema(description = "Описание отеля", example = "Современный отель в центре города")
    private String description;

    @Schema(description = "Бренд отеля", example = "Hilton")
    private String brand;

    @Schema(description = "Адрес отеля")
    private AddressDTO address;

    @Schema(description = "Контактная информация отеля")
    private ContactsDTO contacts;

    @Schema(description = "Время заезда и выезда")
    private ArrivalTimeDTO arrivalTime;

    @Schema(description = "Список удобств отеля", example = "[\"Free WiFi\", \"Free parking\"]")
    private List<String> amenities;
}
