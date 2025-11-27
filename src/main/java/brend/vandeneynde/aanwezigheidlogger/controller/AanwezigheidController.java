package brend.vandeneynde.aanwezigheidlogger.controller;

import brend.vandeneynde.aanwezigheidlogger.dto.AanwezigheidRequest;
import brend.vandeneynde.aanwezigheidlogger.model.Aanwezigheid;
import brend.vandeneynde.aanwezigheidlogger.service.AanwezigheidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;


@RestController
@RequestMapping("/api/aanwezigheid")
@CrossOrigin(origins = "*")
public class AanwezigheidController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Aanwezigheid API werkt! ✅");
    }

    @Autowired
    private AanwezigheidService aanwezigheidService;

    @PostMapping
    public ResponseEntity<?> registreerAanwezigheid(@RequestBody AanwezigheidRequest request) {
        try {
            // Validatie: is stamnr ingevuld?
            if (request.getStamnr() == null || request.getStamnr().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Stamnummer is verplicht");
            }

            // Roep service aan om aanwezigheid te registreren
            Aanwezigheid aanwezigheid = aanwezigheidService.registreerAanwezigheid(request);

            // Success! Stuur 200 OK terug met de aanwezigheid data
            return ResponseEntity.ok(aanwezigheid);

        } catch (Exception e) {
            // Fout! Student niet gevonden of andere error
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/datum/{datum}")
    public ResponseEntity<?> getAanwezighedenVoorDag(@PathVariable String datum) {
        try {
            // Converteer String naar LocalDateTime
            LocalDate date = LocalDate.parse(datum);
            LocalDateTime dateTime = date.atStartOfDay();

            List<Aanwezigheid> aanwezigheden = aanwezigheidService.getAanwezighedenVoorDag(dateTime);
            return ResponseEntity.ok(aanwezigheden);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Ongeldige datum formaat. Gebruik: YYYY-MM-DD");
        }
    }

    @GetMapping
    public ResponseEntity<List<Aanwezigheid>> getAlleAanwezigheden() {
        List<Aanwezigheid> aanwezigheden = aanwezigheidService.getAlleAanwezigheden();
        return ResponseEntity.ok(aanwezigheden);
    }

}
