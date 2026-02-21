package org.example.hotels.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.hotels.dto.HotelCreateDTO;
import org.example.hotels.dto.HotelDTO;
import org.example.hotels.dto.HotelDetailDTO;
import org.example.hotels.entity.Hotel;
import org.example.hotels.mapper.HotelMapper;
import org.example.hotels.service.HotelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Tag(name = "Hotels", description = "API для управления отелями")
public class HotelController {

    private final HotelService service;
    private final HotelMapper hotelMapper;

    @Operation(
            summary = "Получить все отели",
            description = "Возвращает список всех отелей в системе"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список отелей успешно получен",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HotelDTO.class))))
    })
    @GetMapping
    public List<HotelDTO> getAll() {
        return service.getAll().stream()
                .map(hotelMapper::hotelToHotelDTO)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Получить отель по ID",
            description = "Возвращает детальную информацию по отелю"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Отель найден",
                    content = @Content(schema = @Schema(implementation = HotelDetailDTO.class))),
            @ApiResponse(responseCode = "404", description = "Отель с указанным ID не найден")
    })
    @GetMapping("/{id}")
    public HotelDetailDTO getById(@PathVariable Long id) {
        Hotel hotel = service.getById(id);
        return hotelMapper.hotelToHotelDetailDTO(hotel);
    }

    @Operation(
            summary = "Создать новый отель",
            description = "Создает новый отель в системе и возвращает его DTO"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Отель успешно создан",
                    content = @Content(schema = @Schema(implementation = HotelDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для создания отеля")
    })
    @PostMapping
    public HotelDTO create(@RequestBody HotelCreateDTO dto) {
        Hotel hotel = hotelMapper.hotelCreateDTOToHotel(dto);
        Hotel createdHotel = service.create(hotel);
        return hotelMapper.hotelToHotelDTO(createdHotel);
    }

    @Operation(
            summary = "Добавить удобства к отелю",
            description = "Добавляет новые удобства к существующему отелю"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Удобства успешно добавлены",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "404", description = "Отель с указанным ID не найден")
    })
    @PostMapping("/{id}/amenities")
    public List<String> addAmenities(@PathVariable Long id,
                             @RequestBody List<String> amenities) {
        return service.addAmenities(id, amenities);
    }
}
