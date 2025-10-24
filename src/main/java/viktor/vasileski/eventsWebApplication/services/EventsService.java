package viktor.vasileski.eventsWebApplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import viktor.vasileski.eventsWebApplication.entities.Event;
import viktor.vasileski.eventsWebApplication.entities.User;
import viktor.vasileski.eventsWebApplication.exceptions.BadRequestException;
import viktor.vasileski.eventsWebApplication.exceptions.NotFoundException;
import viktor.vasileski.eventsWebApplication.payloads.EventDTO;
import viktor.vasileski.eventsWebApplication.repositories.EventRepository;
import viktor.vasileski.eventsWebApplication.security.JWTTools;

import java.time.LocalDate;
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

    public Event save(EventDTO payload, User user){
        Event newEvent = new Event(payload.title(), payload.description(), payload.date(), payload.place(), payload.nMax(), user.getId());
        Event savedEvent = eventRepository.save(newEvent);
        log.info("L'evento {} con id {} è stato salvato con successo.", savedEvent.getTitle(), savedEvent.getId());
        return savedEvent;
    }

    public Event findById(UUID eventId){
        return this.eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
    }

    public Event addReservation(UUID eventId, User user){
        Event foundEvent = findById(eventId);
        User foundUser = usersService.findById(user.getId());
        if(foundEvent.getParticipants().size()>=foundEvent.getNMax()){
            throw new BadRequestException("Non sono più disponibili posti per questo evento!");
        }
        if(foundEvent.getDate().isBefore(LocalDate.now())){
            throw new BadRequestException("L'evento selezionato è concluso, non può essere selezionato");
        }
        if(foundEvent.getParticipants().contains(foundUser)){
            throw new BadRequestException("Ti sei già iscritto a questo evento!");
        }
        foundEvent.addParticipant(foundUser);
        Event savedEvent = eventRepository.save(foundEvent);
        log.info("Il partecipante {} con id {} è stato aggiunto all'evento {}", user.getName(), user.getId(), savedEvent.getTitle());
        return savedEvent;
    }

    public Page<Event> findAll(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, 25);
        return eventRepository.findAll(pageable);
    }

    public Event findByIdAndUpdate(UUID eventId, EventDTO payload){
        Event found = findById(eventId);
        found.setTitle(payload.title());
        found.setDescription(payload.description());
        found.setDate(payload.date());
        found.setPlace(payload.place());
        found.setNMax(payload.nMax());
        Event modifiedEvent = this.eventRepository.save(found);
        log.info("L'evento {} è stato inserito con successo", modifiedEvent.getTitle());
        return modifiedEvent;
    }

    public void findByIdAndDelete(UUID eventId){
        Event found = findById(eventId);
        eventRepository.delete(found);
    }
}
