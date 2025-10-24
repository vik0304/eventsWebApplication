package viktor.vasileski.eventsWebApplication.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import viktor.vasileski.eventsWebApplication.entities.User;
import viktor.vasileski.eventsWebApplication.exceptions.UnauthorizedException;
import viktor.vasileski.eventsWebApplication.services.UsersService;

import java.io.IOException;
import java.net.Authenticator;
import java.util.UUID;

@Component
public class JTWFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UsersService usersService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire il token nell'authorization header nel formato giusto!");
        String accessToken = authHeader.replace("Bearer ", "");
        jwtTools.verifyToken(accessToken);

        UUID userId = jwtTools.extractIdFromToken(accessToken);
        User found = usersService.findById(userId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(found, null, found.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
