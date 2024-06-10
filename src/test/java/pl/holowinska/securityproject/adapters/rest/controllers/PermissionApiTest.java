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
import pl.holowinska.securityproject.api.model.PermissionDTO;
import pl.holowinska.securityproject.domain.model.Permission;
import pl.holowinska.securityproject.ports.inbound.PermissionService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SecurityprojectApplication.class)
@AutoConfigureMockMvc
class PermissionApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PermissionService permissionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser()
    public void shouldReturnForbiddenStatusWhenUserDoesNotHavePermissionToAddPermission() throws Exception {
        //given
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setName("CREATE_ROLE");

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permissionDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldAddNewPermission() throws Exception {
        //given
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setName("CREATE_ROLE");

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permissionDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnBadRequestStatusWhenPermissionNameStartsWithIncorrectPrefix() throws Exception {
        //given
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setName("ROLE_CREATE");

        //when
        Mockito.doThrow(new IllegalArgumentException())
                .when(permissionService).addPermission(Mockito.any(Permission.class));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permissionDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnBadRequestStatusWhenPermissionAlreadyExists() throws Exception {
        //given
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setName("CREATE_ROLE");

        //when
        Mockito.doThrow(new IllegalStateException())
                .when(permissionService).addPermission(Mockito.any(Permission.class));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permissionDTO)))
                .andExpect(status().isConflict());
    }
}