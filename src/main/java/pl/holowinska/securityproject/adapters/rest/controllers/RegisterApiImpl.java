package pl.holowinska.securityproject.adapters.rest.controllers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.holowinska.securityproject.adapters.rest.mappers.UserMapper;
import pl.holowinska.securityproject.api.RegisterApi;
import pl.holowinska.securityproject.api.model.RegisterDTO;
import pl.holowinska.securityproject.ports.inbound.UserService;

@RestController
@AllArgsConstructor
public class RegisterApiImpl implements RegisterApi {

    private static final Logger logger = LoggerFactory.getLogger(RegisterApiImpl.class);

    private final UserService userService;

    @Override
    public ResponseEntity<Void> register(RegisterDTO registerDTO) {
        userService.register(UserMapper.mapRegisterDTOToEntity(registerDTO));
        logger.info("User was created");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
