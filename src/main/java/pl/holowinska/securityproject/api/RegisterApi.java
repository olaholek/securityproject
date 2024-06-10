package pl.holowinska.securityproject.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.holowinska.securityproject.api.model.RegisterDTO;

@RequestMapping("/register")
public interface RegisterApi {

    @PostMapping
    ResponseEntity<Void> register(@RequestBody RegisterDTO registerDTO);
}
