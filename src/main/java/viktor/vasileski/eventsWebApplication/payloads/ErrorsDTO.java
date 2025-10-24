package viktor.vasileski.eventsWebApplication.payloads;

import java.time.LocalDateTime;

public record ErrorsDTO(
        String message,
        LocalDateTime date
) {
}
