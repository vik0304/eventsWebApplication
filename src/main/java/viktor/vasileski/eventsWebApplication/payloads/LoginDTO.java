package viktor.vasileski.eventsWebApplication.payloads;

public record LoginDTO(
        String email,
        String password
) {
}
