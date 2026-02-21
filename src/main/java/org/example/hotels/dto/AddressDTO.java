package org.example.hotels.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "AddressDTO", description = "DTO с информацией об адресе отеля")
public class AddressDTO {
    @Schema(description = "Номер дома", example = "12")
    private Integer houseNumber;

    @Schema(description = "Улица", example = "Lenina")
    private String street;

    @Schema(description = "Город", example = "Minsk")
    private String city;

    @Schema(description = "Страна", example = "Belarus")
    private String country;

    @Schema(description = "Почтовый индекс", example = "220030")
    private String postCode;
}
