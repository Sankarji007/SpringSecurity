package com.example.SpringJWT.Config;


import com.example.SpringJWT.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthendication extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private  UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().contains("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader=request.getHeader("Authorization");
        if(!authHeader.startsWith("Bearer ")||authHeader.equals(null))
        {
            filterChain.doFilter(request,response);
            return;
        }

        String token =authHeader.substring(7);
        String username= null;

            username = jwtService.ExtractUsername(token);

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {

            UserDetails userDetails= userDetailsService.loadUserByUsername(username);
            try {
                if(jwtService.isValidToken(userDetails, token))
                {
                    UsernamePasswordAuthenticationToken AuthToken=new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    AuthToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(AuthToken);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        filterChain.doFilter(request,response);
    }
}
