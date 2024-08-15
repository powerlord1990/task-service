package ru.company.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.company.taskmanagement.common.dto.AuthRequest;
import ru.company.taskmanagement.common.dto.JwtAuthenticationResponse;
import ru.company.taskmanagement.common.dto.RegistrationRequest;
import ru.company.taskmanagement.entity.User;
import ru.company.taskmanagement.entity.enums.UserRole;
import ru.company.taskmanagement.exception.InvalidCredentialsException;
import ru.company.taskmanagement.exception.UserAlreadyExistException;
import ru.company.taskmanagement.exception.UserNotFoundException;
import ru.company.taskmanagement.repository.UserRepository;
import ru.company.taskmanagement.security.JwtTokenProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationResponse registerNewUser(RegistrationRequest userDto) {
        // Проверяем, существует ли пользователь с таким же именем
        Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistException(
                    String.format("User with username: %s already exist", userDto.getUsername())
            );
        }

        // Если не существует, создаем нового пользователя
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRole(UserRole.USER); // Устанавливаем роль пользователя
        userRepository.save(user);

        // Возвращаем ответ с JWT-токеном
        return new JwtAuthenticationResponse(jwtTokenProvider.createToken(user.getUsername(), user.getRole().name()));
    }

    public JwtAuthenticationResponse loginUser(AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User not found with username: %s", authRequest.getUsername()
                        )));

        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return new JwtAuthenticationResponse(jwtTokenProvider.createToken(user.getUsername(), user.getRole().name()));
        } else {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }


}
