package ru.zubov.pizzacloud.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.zubov.pizzacloud.entity.User;
import ru.zubov.pizzacloud.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    void findUserByUserName() {
        //When
        User user = new User();
        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        //Given
        UserDetails result = customUserDetailsService.loadUserByUsername(username);

        //Then
        assertEquals(user, result);
    }

    @Test
    void findUserByUserNameWhenUserDontFindThenTrowException() {
        //When
        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        //Given
        UsernameNotFoundException thrown = assertThrows(
                UsernameNotFoundException.class,
                () ->  customUserDetailsService.loadUserByUsername(username),
                ""
        );

        //Then
        assertTrue(thrown.getMessage().contains("User not found with username: "+ username));
    }

}