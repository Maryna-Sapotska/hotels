package org.example.hotels.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Schema(name = "ArrivalTime", description = "Время заезда и выезда отеля")
public class ArrivalTime {
    @Schema(description = "Время заезда (check-in)", example = "14:00")
    private String checkIn;

    @Schema(description = "Время выезда (check-out)", example = "12:00")
    private String checkOut;
}
