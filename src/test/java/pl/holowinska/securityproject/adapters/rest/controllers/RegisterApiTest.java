package pl.holowinska.securityproject.adapters.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.holowinska.securityproject.SecurityprojectApplication;
import pl.holowinska.securityproject.api.model.UserDTO;
import pl.holowinska.securityproject.domain.exceptions.UsernameConflictException;
import pl.holowinska.securityproject.domain.model.User;
import pl.holowinska.securityproject.ports.inbound.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SecurityprojectApplication.class)
@AutoConfigureMockMvc
class RegisterApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnConflictStatusWhenUserWithThisUsernameAlreadyExists() throws Exception {
        //given
        UserDTO user = new UserDTO();

        //when
        Mockito.doThrow(new UsernameConflictException())
                .when(userService).register(Mockito.any(User.class));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                        .andExpect(status().isConflict());
    }

    @Test
    public void shouldSaveUser() throws Exception {
        //given
        UserDTO user = new UserDTO();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }
}