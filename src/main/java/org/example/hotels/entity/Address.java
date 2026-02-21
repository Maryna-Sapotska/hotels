package org.example.hotels.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Schema(name = "Address", description = "Адрес отеля")
public class Address {
    @Schema(description = "Номер дома", example = "10")
    private Integer houseNumber;

    @Schema(description = "Улица", example = "Independence Avenue")
    private String street;

    @Schema(description = "Город", example = "Minsk")
    private String city;

    @Schema(description = "Страна", example = "Belarus")
    private String country;

    @Schema(description = "Почтовый индекс", example = "220030")
    private String postCode;
}
