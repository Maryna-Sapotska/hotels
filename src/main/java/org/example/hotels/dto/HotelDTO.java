package org.example.hotels.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "HotelDTO", description = "Краткая информация об отеле")
public class HotelDTO {
    @Schema(description = "ID отеля", example = "1")
    private Long id;

    @Schema(description = "Название отеля", example = "Hilton Minsk")
    private String name;

    @Schema(description = "Описание отеля", example = "Современный отель в центре города")
    private String description;

    @Schema(description = "Адрес отеля", example = "Minsk, Independence Avenue 10")
    private String address;

    @Schema(description = "Телефон отеля", example = "+375 29 123-45-67")
    private String phone;
}
