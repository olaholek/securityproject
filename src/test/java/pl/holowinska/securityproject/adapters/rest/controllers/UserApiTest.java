package pl.holowinska.securityproject.adapters.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.holowinska.securityproject.SecurityprojectApplication;
import pl.holowinska.securityproject.api.model.UserRoleDTO;
import pl.holowinska.securityproject.domain.exceptions.RoleNotFoundException;
import pl.holowinska.securityproject.domain.exceptions.UserNotFoundException;
import pl.holowinska.securityproject.domain.model.User;
import pl.holowinska.securityproject.ports.inbound.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SecurityprojectApplication.class)
@AutoConfigureMockMvc
class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser()
    public void shouldReturnForbiddenStatusWhenUserDoesNotHavePermissionToAddRoleToUser() throws Exception {
        //given
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(1L);
        userRoleDTO.setRoleId(1L);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/users/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRoleDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER_ROLE_ASSIGN")
    public void shouldAddRoleToUser() throws Exception {
        //given
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(1L);
        userRoleDTO.setRoleId(1L);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/users/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRoleDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER_ROLE_ASSIGN")
    public void shouldReturnNotFoundStatusWhenAddNonExistentRoleToUser() throws Exception {
        //given
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(1L);
        userRoleDTO.setRoleId(1L);

        //when
        Mockito.doThrow(new RoleNotFoundException()).when(userService).addRoleToUser(Mockito.any(Long.class), Mockito.any(Long.class));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/users/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRoleDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "USER_ROLE_ASSIGN")
    public void shouldReturnNotFoundStatusWhenAddRoleToNonExistentUser() throws Exception {
        //given
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(1L);
        userRoleDTO.setRoleId(1L);

        //when
        Mockito.doThrow(new UserNotFoundException()).when(userService).addRoleToUser(Mockito.any(Long.class), Mockito.any(Long.class));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/users/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRoleDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser()
    public void shouldReturnForbiddenStatusWhenUserDoesNotHavePermissionToGetUser() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER_READ")
    public void shouldReturnUserById() throws Exception {
        //given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("Jan");

        //when
        when(userService.getUserById(userId)).thenReturn(user);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()));
    }

    @Test
    @WithMockUser(authorities = "USER_READ")
    public void shouldReturnNotFoundStatusWhenUserWithThisIdDoesNotExists() throws Exception {
        //given
        Long userId = 1L;

        //when
        Mockito.doThrow(new UserNotFoundException())
                .when(userService).getUserById(Mockito.any(Long.class));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userId))
                .andExpect(status().isNotFound());
    }
}