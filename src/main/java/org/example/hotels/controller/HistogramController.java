package org.example.hotels.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.hotels.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/histogram")
@RequiredArgsConstructor
@Tag(name = "Histogram", description = "API для получения статистики по отелям (гистограммы)")
public class HistogramController {
    private final HotelService service;

    @Operation(
            summary = "Получить гистограмму по выбранному параметру",
            description = "Возвращает количество отелей, сгруппированных по указанному параметру. " +
                    "Допустимые параметры: brand, city, country, amenities."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Гистограмма успешно сформирована",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Неверный параметр для гистограммы")
    })
    @Parameter(
            name = "param",
            description = "Параметр, по которому строится гистограмма (brand, city, country, amenities)",
            required = true,
            example = "city"
    )
    @GetMapping("/{param}")
    public Map<String, Long> histogram(@PathVariable String param) {

        List<String> allowed = List.of("brand", "city", "country", "amenities");

        if (!allowed.contains(param)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid histogram parameter"
            );
        }

        if (param.equals("amenities")) {
            return service.getAll().stream()
                    .flatMap(h -> h.getAmenities().stream())
                    .collect(Collectors.groupingBy(a -> a, Collectors.counting()));
        }

        return service.getAll().stream()
                .collect(Collectors.groupingBy(
                        h -> switch (param) {
                            case "brand" -> h.getBrand();
                            case "city" -> h.getAddress().getCity();
                            case "country" -> h.getAddress().getCountry();
                            default -> throw new IllegalStateException();
                        },
                        Collectors.counting()
                ));
    }
}
