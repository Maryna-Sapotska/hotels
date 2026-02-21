package org.example.hotels.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "ArrivalTimeDTO", description = "DTO с информацией о времени заезда и выезда")
public class ArrivalTimeDTO {
    @Schema(description = "Время заезда (check-in)", example = "14:00")
    private String checkIn;

    @Schema(description = "Время выезда (check-out)", example = "12:00")
    private String checkOut;
}
