package ru.company.taskmanagenent.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.company.taskmanagement.common.dto.AuthRequest;
import ru.company.taskmanagement.common.dto.JwtAuthenticationResponse;
import ru.company.taskmanagement.common.dto.RegistrationRequest;
import ru.company.taskmanagement.controller.AuthController;
import ru.company.taskmanagement.service.UserService;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testRegister() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        // заполните registrationRequest необходимыми данными

        when(userService.registerNewUser(registrationRequest)).thenReturn(new JwtAuthenticationResponse("mockToken"));

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"test\", \"password\":\"password\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testLogin() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        // заполните authRequest необходимыми данными

        when(userService.loginUser(authRequest)).thenReturn(new JwtAuthenticationResponse("mockToken"));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"test\", \"password\":\"password\"}"))
                .andExpect(status().isOk());
    }
}