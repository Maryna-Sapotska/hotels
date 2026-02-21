package org.example.hotels.mapper;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.hotels.dto.*;
import org.example.hotels.entity.Address;
import org.example.hotels.entity.ArrivalTime;
import org.example.hotels.entity.Contacts;
import org.example.hotels.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "contacts")
    @Schema(description = "Преобразование сущности Hotel в DTO для краткого отображения")
    HotelDTO hotelToHotelDTO(Hotel hotel);

    @Mapping(source = "address", target = "address")
    @Mapping(source = "contacts", target = "contacts")
    @Mapping(source = "arrivalTime", target = "arrivalTime")
    @Mapping(source = "amenities", target = "amenities")
    @Schema(description = "Преобразование сущности Hotel в детальный DTO")
    HotelDetailDTO hotelToHotelDetailDTO(Hotel hotel);

    @Schema(description = "Преобразование Address в AddressDTO")
    AddressDTO addressToAddressDTO(Address address);

    @Schema(description = "Преобразование Contacts в ContactsDTO")
    ContactsDTO contactsToContactsDTO(Contacts contacts);

    @Schema(description = "Преобразование ArrivalTime в ArrivalTimeDTO")
    ArrivalTimeDTO arrivalTimeToArrivalTimeDTO(ArrivalTime arrivalTime);

    default String map(Address address) {
        if (address == null) return null;
        return address.getStreet() + ", " + address.getCity() + ", " + address.getPostCode();
    }

    default String map(Contacts contacts) {
        if (contacts == null) return null;
        return contacts.getPhone();
    }

    @Schema(description = "Преобразование DTO для создания отеля в сущность Hotel")
    Hotel hotelCreateDTOToHotel(HotelCreateDTO dto);
}
