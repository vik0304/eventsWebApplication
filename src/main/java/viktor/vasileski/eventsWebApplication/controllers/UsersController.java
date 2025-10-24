package viktor.vasileski.eventsWebApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import viktor.vasileski.eventsWebApplication.entities.Event;
import viktor.vasileski.eventsWebApplication.entities.User;
import viktor.vasileski.eventsWebApplication.payloads.EventDTO;
import viktor.vasileski.eventsWebApplication.services.UsersService;

import java.util.Set;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService usersService;

    @GetMapping("/me/reservations")
    public Set<EventDTO> showReservations(@AuthenticationPrincipal User currentAuthenticatedUser){
        return usersService.showReservations(currentAuthenticatedUser);
    }
}
