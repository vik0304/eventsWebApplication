package viktor.vasileski.eventsWebApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import viktor.vasileski.eventsWebApplication.entities.User;
import viktor.vasileski.eventsWebApplication.exceptions.ValidationException;
import viktor.vasileski.eventsWebApplication.payloads.LoginDTO;
import viktor.vasileski.eventsWebApplication.payloads.LoginResponseDTO;
import viktor.vasileski.eventsWebApplication.payloads.NewUserDTO;
import viktor.vasileski.eventsWebApplication.services.AuthService;
import viktor.vasileski.eventsWebApplication.services.UsersService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body){
        return new LoginResponseDTO(authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Validated NewUserDTO payload, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        } return usersService.save(payload);
    }
}
