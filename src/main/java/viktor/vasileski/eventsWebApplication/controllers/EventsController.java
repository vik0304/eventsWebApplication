package viktor.vasileski.eventsWebApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import viktor.vasileski.eventsWebApplication.entities.Event;
import viktor.vasileski.eventsWebApplication.entities.User;
import viktor.vasileski.eventsWebApplication.exceptions.ValidationException;
import viktor.vasileski.eventsWebApplication.payloads.EventDTO;
import viktor.vasileski.eventsWebApplication.security.JWTTools;
import viktor.vasileski.eventsWebApplication.services.EventsService;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    EventsService eventsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event saveEvent (@RequestBody @Validated EventDTO body, BindingResult validationResult, @AuthenticationPrincipal User currentAuthenticatedUser){
        if(validationResult.hasErrors()){
            throw new ValidationException(validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return eventsService.save(body, currentAuthenticatedUser);
    }


    @PatchMapping("/{eventId}")
    public Event addReservation(@PathVariable UUID eventId, @AuthenticationPrincipal User currentAuthenticatedUser){
        return eventsService.addReservation(eventId, currentAuthenticatedUser);
    }
}
