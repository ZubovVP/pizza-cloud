package ru.zubov.pizzacloud.entity.dtos;

import java.io.Serializable;

public record UserDto(Long id, String username, String fullname, String street, String city, String state, String zip,
                      String phoneNumber) implements Serializable {
}
