package ru.company.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.taskmanagement.common.dto.AuthRequest;
import ru.company.taskmanagement.common.dto.JwtAuthenticationResponse;
import ru.company.taskmanagement.common.dto.RegistrationRequest;
import ru.company.taskmanagement.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Process a register operation", description = "Processes a register operation based on the provided request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation processed successfully"),
            @ApiResponse(responseCode = "400", description = "User with this username exist")
    })
    public JwtAuthenticationResponse register(@RequestBody RegistrationRequest user){
        return userService.registerNewUser(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Process a login operation", description = "Processes a login operation based on the provided request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid login information or bad request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public JwtAuthenticationResponse login(@RequestBody AuthRequest authRequest) {
        return userService.loginUser(authRequest);
    }


}
