package viktor.vasileski.eventsWebApplication.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Event {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String title;
    private String description;
    private LocalDate date;
    private String place;
    @Column(name = "n_max")
    private int nMax;
    @Column(name = "creator_id")
    private UUID creatorId;
    @ManyToMany
    @JoinTable(
            name="event_reservations",
            joinColumns = @JoinColumn(name="event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();

    public Event(String title, String description, LocalDate date, String place, int nMax, UUID idCreator){
        this.title=title;
        this.description=description;
        this.date=date;
        this.place=place;
        this.nMax=nMax;
        this.creatorId=idCreator;
    }

    public void addParticipant(User user){
        participants.add(user);
        user.getReservedEvents().add(this);
    }
}
