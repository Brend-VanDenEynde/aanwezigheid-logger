package brend.vandeneynde.aanwezigheidlogger.controller;

import brend.vandeneynde.aanwezigheidlogger.dto.LoginRequest;
import brend.vandeneynde.aanwezigheidlogger.dto.LoginResponse;
import brend.vandeneynde.aanwezigheidlogger.model.User;
import brend.vandeneynde.aanwezigheidlogger.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.getUsername(), request.getPassword());

            LoginResponse response = new LoginResponse(token, request.getUsername(), "ADMIN");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Login mislukt: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
        try {
            // Validatie
            if (request.getUsername() == null || request.getUsername().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Username is verplicht");
            }

            if (request.getPassword() == null || request.getPassword().length() < 6) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Wachtwoord moet minimaal 6 karakters zijn");
            }

            User user = authService.register(request.getUsername(), request.getPassword());

            return ResponseEntity.ok("Gebruiker geregistreerd: " + user.getUsername() + " (ADMIN)");

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Registratie mislukt: " + e.getMessage());
        }
    }
}