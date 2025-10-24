package viktor.vasileski.eventsWebApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import viktor.vasileski.eventsWebApplication.entities.User;
import viktor.vasileski.eventsWebApplication.exceptions.UnauthorizedException;
import viktor.vasileski.eventsWebApplication.payloads.LoginDTO;
import viktor.vasileski.eventsWebApplication.security.JWTTools;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(LoginDTO body){
        User found = usersService.findByEmail(body.email());
        if(bcrypt.matches(body.password(), found.getPassword())){
            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali errate.");
        }
    }
}
