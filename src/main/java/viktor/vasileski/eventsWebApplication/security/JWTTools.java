package viktor.vasileski.eventsWebApplication.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import viktor.vasileski.eventsWebApplication.entities.User;
import io.jsonwebtoken.Jwts;
import viktor.vasileski.eventsWebApplication.exceptions.UnauthorizedException;

import java.util.Date;
import java.util.UUID;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user){
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String accessToken){
        try{
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(accessToken);
        }catch(Exception e){
            throw new UnauthorizedException("Ci sono stati errori nella verifica del token! Effettua di nuovo il login");
        }
    }

    public UUID extractIdFromToken(String accessToken) {
        return UUID.fromString(Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .getSubject());
    }
}
