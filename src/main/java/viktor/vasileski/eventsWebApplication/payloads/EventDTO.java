package viktor.vasileski.eventsWebApplication.payloads;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDate;

public record EventDTO(
        @NotBlank(message = "L'evento deve avere un titolo")
        String title,
        @NotBlank(message = "L'evento deve avere una descrizione")
        String description,
        @Future(message = "La data dell'evento non può essere oggi o nel passato")
        LocalDate date,
        @NotBlank(message = "L'evento deve avere un luogo")
        String place,
        @Positive(message = "Il numero massimo di partecipanti deve essere maggiore di 0")
        int nMax
) {
}
