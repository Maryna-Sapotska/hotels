package org.example.hotels.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "ContactsDTO", description = "DTO с контактной информацией отеля")
public class ContactsDTO {
    @Schema(description = "Телефон отеля", example = "+375291234567")
    private String phone;

    @Schema(description = "Электронная почта отеля", example = "info@hotel.com")
    private String email;
}
