package ru.zubov.pizzacloud.entity.mapper;

import org.mapstruct.*;
import ru.zubov.pizzacloud.entity.User;
import ru.zubov.pizzacloud.entity.dtos.UserDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserDto(UserDto userDto, @MappingTarget User user);
}
