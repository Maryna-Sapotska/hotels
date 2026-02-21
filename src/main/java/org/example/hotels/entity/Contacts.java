package org.example.hotels.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Schema(name = "Contacts", description = "Контактная информация отеля")
public class Contacts {
    @Schema(description = "Номер телефона отеля", example = "+375291234567")
    private String phone;

    @Schema(description = "Электронная почта отеля", example = "info@hotel.com")
    private String email;
}
