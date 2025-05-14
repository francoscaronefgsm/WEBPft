package edu.utec.webpft.controller;

import edu.utec.webpft.dtos.LoginDto;
import edu.utec.webpft.service.ApiAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final ApiAuthService apiAuthService;

    @Autowired
    public ApiAuthController(ApiAuthService apiAuthService) {
        this.apiAuthService = apiAuthService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiAuthService.AuthResponse> login(@RequestBody LoginDto loginDto) {
        return apiAuthService.login(loginDto);
    }

}
