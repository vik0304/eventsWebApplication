package viktor.vasileski.eventsWebApplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import viktor.vasileski.eventsWebApplication.entities.Event;
import viktor.vasileski.eventsWebApplication.payloads.EventDTO;
import viktor.vasileski.eventsWebApplication.repositories.EventRepository;
import viktor.vasileski.eventsWebApplication.security.JWTTools;

import java.util.UUID;

@Service
@Slf4j
public class EventsService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTTools jwtTools;

    public Event save(EventDTO payload, String authHeader){
        String token = jwtTools.extractToken(authHeader);
        UUID userId = jwtTools.extractIdFromToken(token);
        Event newEvent = new Event(payload.title(), payload.description(), payload.date(), payload.place(), payload.nMax(), userId);
        Event savedEvent = eventRepository.save(newEvent);
        log.info("L'evento {} con id {} è stato salvato con successo.", savedEvent.getTitle(), savedEvent.getId());
        return savedEvent;
    }
}
