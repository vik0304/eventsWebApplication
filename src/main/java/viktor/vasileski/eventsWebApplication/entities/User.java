package viktor.vasileski.eventsWebApplication.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;
    private String name;
    private String surname;
    private String email;
    private String password;
    @ManyToMany(mappedBy = "participants")
    private Set<Event> reservedEvents = new HashSet<>();

    public User(UserType userType, String name, String surname, String email, String password){
        this.userType=userType;
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.password=password;
    }

    public void addReservation(Event event){
        reservedEvents.add(event);
        event.getParticipants().add(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.userType.name()));
    }

    @Override
    public String getUsername(){
        return this.email;
    }
}
