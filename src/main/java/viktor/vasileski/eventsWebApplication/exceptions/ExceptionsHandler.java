package viktor.vasileski.eventsWebApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import viktor.vasileski.eventsWebApplication.payloads.ErrorsDTO;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler extends RuntimeException {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsDTO handlerNotFound(NotFoundException e){
        return new ErrorsDTO("Elemento non trovato o id errato", LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorsDTO handleUnauthorizedException(UnauthorizedException ex) {
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ExceptionHandler(Exception.class) // Tutte le eccezioni che non sono BadRequestException o NotFoundException vengono gestite da questo handler
    public ErrorsDTO handleServerError(Exception ex) {
        ex.printStackTrace(); // E' importante avere il print dello stack trace per sapere dove intervenire per fixare il bug
        return new ErrorsDTO("C'è stato un errore generico, giuro che lo risolveremo presto!", LocalDateTime.now());
    }
}
