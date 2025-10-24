package viktor.vasileski.eventsWebApplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import viktor.vasileski.eventsWebApplication.entities.User;
import viktor.vasileski.eventsWebApplication.entities.UserType;
import viktor.vasileski.eventsWebApplication.exceptions.BadRequestException;
import viktor.vasileski.eventsWebApplication.exceptions.NotFoundException;
import viktor.vasileski.eventsWebApplication.payloads.NewUserDTO;
import viktor.vasileski.eventsWebApplication.repositories.UserRepository;

import java.util.UUID;

@Service
@Slf4j
public class UsersService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public User findByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("L'utente con l'email " + email + " non è stato trovato"));
    }

    public User findById(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User save(NewUserDTO payload){
        userRepository.findByEmail(payload.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già in uso");
        });
        User newUser = new User(UserType.STANDARD, payload.name(), payload.surname(), payload.email(), bcrypt.encode(payload.password()));
        User savedUser = userRepository.save(newUser);
        log.info("L'utente con id " + savedUser.getId() + " è stato salvato correttamente");
        return savedUser;
    }
}
