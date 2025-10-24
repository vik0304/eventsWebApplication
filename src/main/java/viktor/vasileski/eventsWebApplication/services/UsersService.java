package viktor.vasileski.eventsWebApplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import viktor.vasileski.eventsWebApplication.entities.User;
import viktor.vasileski.eventsWebApplication.exceptions.NotFoundException;
import viktor.vasileski.eventsWebApplication.repositories.UserRepository;

@Service
@Slf4j
public class UsersService {
    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("L'utente con l'email " + email + " non è stato trovato"));
    }
}
