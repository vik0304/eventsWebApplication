package viktor.vasileski.eventsWebApplication.payloads;


import jakarta.validation.constraints.*;

public record NewUserDTO(
        @NotBlank(message = "Il nome è obbligatorio")
        @Size(min = 2, max = 20, message = "Il nome deve avere una lunghezza compresa tra 2 e 20 caratteri")
        String name,
        @NotBlank(message = "Il cognome è obbligatorio!")
        @Size(min = 2, max = 30, message = "Il cognome deve avere una lunghezza compresa tra 2 e 30 caratteri")
        String surname,
        @NotBlank(message = "L'email è obbligatoria!")
        @Email(message = "L'indirizzo email inserito non è nel formato corretto!")
        String email,
        @NotBlank(message = "La password è obbligatoria!")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "La password deve avere almeno 8 caratteri e contenere almeno una lettere maiuscola, una lettere minuscola e un numero.")
        String password
) {
}
