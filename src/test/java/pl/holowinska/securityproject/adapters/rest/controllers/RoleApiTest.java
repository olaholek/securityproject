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
import pl.holowinska.securityproject.api.model.PermissionRoleDTO;
import pl.holowinska.securityproject.api.model.RoleDTO;
import pl.holowinska.securityproject.domain.exceptions.PermissionNotFoundException;
import pl.holowinska.securityproject.domain.exceptions.RoleNotFoundException;
import pl.holowinska.securityproject.domain.model.Role;
import pl.holowinska.securityproject.ports.inbound.RoleService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SecurityprojectApplication.class)
@AutoConfigureMockMvc
class RoleApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser()
    public void shouldReturnForbiddenStatusWhenUserDoesNotHaveAuthorityToAddRole() throws Exception {
        //given
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_MANAGER");

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CREATE_ROLE")
    public void shouldAddNewRole() throws Exception {
        //given
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_MANAGER");

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "CREATE_ROLE")
    public void shouldReturnBadRequestStatusWhenRoleDoesNotStartsWithCorrectPrefix() throws Exception {
        //given
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("MANAGER");

        //when
        Mockito.doThrow(new IllegalArgumentException())
                .when(roleService).addRole(Mockito.any(Role.class));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "CREATE_ROLE")
    public void shouldReturnBadRequestStatusWhenRoleAlreadyExistsInRoles() throws Exception {
        //given
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("MANAGER");

        //when
        Mockito.doThrow(new IllegalStateException())
                .when(roleService).addRole(Mockito.any(Role.class));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser()
    public void shouldReturnForbiddenStatusWhenUserDoesNotHaveAuthorityToAddPermissionToRole() throws Exception {
        //given
        PermissionRoleDTO permissionRoleDTO = new PermissionRoleDTO();
        permissionRoleDTO.setRoleId(1L);
        permissionRoleDTO.setPermissionId(1L);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permissionRoleDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldAddPermissionToRole() throws Exception {
        //given
        PermissionRoleDTO permissionRoleDTO = new PermissionRoleDTO();
        permissionRoleDTO.setRoleId(1L);
        permissionRoleDTO.setPermissionId(1L);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permissionRoleDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnNotFoundStatusWhenAddNonExistentPermissionToRole() throws Exception {
        //given
        PermissionRoleDTO permissionRoleDTO = new PermissionRoleDTO();
        permissionRoleDTO.setRoleId(1L);
        permissionRoleDTO.setPermissionId(1L);

        //when
        Mockito.doThrow(new PermissionNotFoundException())
                .when(roleService).addPermissionToRole(Mockito.any(Long.class), Mockito.any(Long.class));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permissionRoleDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnNotFoundStatusWhenAddPermissionToNonExistentRole() throws Exception {
        //given
        PermissionRoleDTO permissionRoleDTO = new PermissionRoleDTO();
        permissionRoleDTO.setRoleId(1L);
        permissionRoleDTO.setPermissionId(1L);

        //when
        Mockito.doThrow(new RoleNotFoundException())
                .when(roleService).addPermissionToRole(Mockito.any(Long.class), Mockito.any(Long.class));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permissionRoleDTO)))
                .andExpect(status().isNotFound());
    }
}