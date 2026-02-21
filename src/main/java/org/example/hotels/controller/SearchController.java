package org.example.hotels.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.hotels.dto.HotelDTO;
import org.example.hotels.mapper.HotelMapper;
import org.example.hotels.service.HotelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Tag(name = "Search", description = "API для поиска отелей по различным параметрам")
public class SearchController {
    private final HotelService service;
    private final HotelMapper mapper;

    @Operation(
            summary = "Поиск отелей",
            description = "Возвращает список отелей, соответствующих заданным критериям поиска. " +
                    "Все параметры являются необязательными и могут использоваться вместе."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список отелей успешно найден",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HotelDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры поиска")
    })
    @GetMapping
    public List<HotelDTO> search(
            @Parameter(description = "Название отеля для поиска", example = "Hilton")
            @RequestParam(required = false) String name,

            @Parameter(description = "Бренд отеля для поиска", example = "Marriott")
            @RequestParam(required = false) String brand,

            @Parameter(description = "Город, в котором искать отель", example = "Minsk")
            @RequestParam(required = false) String city,

            @Parameter(description = "Страна, в которой искать отель", example = "Belarus")
            @RequestParam(required = false) String country,

            @Parameter(description = "Список удобств, которыми должен обладать отель", example = "[\"Free WiFi\", \"Fitness center\"]")
            @RequestParam(required = false) List<String> amenities
    ) {
        return service.search(name, brand, city, country, amenities)
                .stream()
                .map(mapper::hotelToHotelDTO)
                .toList();
    }
}
