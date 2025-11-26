package brend.vandeneynde.aanwezigheidlogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aanwezigheid")
@CrossOrigin(origins = "*")
public class AanwezigheidController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Aanwezigheid API werkt! ✅");
    }
}
