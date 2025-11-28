package brend.vandeneynde.aanwezigheidlogger.service;

import brend.vandeneynde.aanwezigheidlogger.model.User;
import brend.vandeneynde.aanwezigheidlogger.repository.UserRepository;
import brend.vandeneynde.aanwezigheidlogger.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public String login(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new Exception("Gebruiker niet gevonden");
        }


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Verkeerd wachtwoord");
        }
        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }

    public User register(String username, String password) throws Exception {
        if (userRepository.findByUsername(username) != null) {
            throw new Exception("Username bestaat al");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ADMIN");



        return userRepository.save(user);
    }
}