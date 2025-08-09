package com.gym.gym_ver2.infraestructure.jwt;
//patrones: singleton, builder, fachada, estrategy, decorador en .signWith(getKey()

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service//interfaz que carga los datos específicos del usuario. Servicio que se encarga de la creacion y validacion de los tokens
public class JwtService {

    private final JwtConfig jwtConfig;// Configuración del JWT, contiene la clave secreta

    public JwtService(JwtConfig jwtConfig) {//  recibe la configuración del JWT
        this.jwtConfig = jwtConfig;
    }

    public String getClave() {// obtener la clave secreta del JWT
        return jwtConfig.getSecretKey();
    }

    public String createToken(UserDetails usuario) {
        return generateToken(new HashMap<>(), usuario);
    }

    // Crear un token con información adicional
    public String generateToken(Map<String, Object> extraClaims, UserDetails user) {

        String roles = user.getAuthorities() // Obtén los roles del usuario
                .stream()//convierte la lista en un stream
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        System.out.println("Rol: " + roles);

        return Jwts.builder()// Construir el token mediante la librería Jwts
                .setClaims(extraClaims) // Información adicional, correo
                .claim("rol", roles) // Agregar los roles del usuario
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Expira en 24 minutos
                .signWith(getKey(), SignatureAlgorithm.HS256) // Firma con clave secreta, añade seguridad
                .compact(); // Generar el token de tipo String
    }
    
    // Obtener la clave secreta en formato Key
    public  Key getKey() {
        byte[] secretEncode = Decoders.BASE64.decode(this.getClave()); // Decodificar la clave secreta en base64
        if (secretEncode.length < 32) {
            throw new IllegalArgumentException("La clave secreta debe tener al menos 32 bytes (256 bits) después de decodificarla.");
        }
        return Keys.hmacShaKeyFor(secretEncode);//devuelve una clave secreta
    }

    // Validar el token con el usuario y la información adicional
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            System.err.println("Error validando el token: " + e.getMessage());
            return false; // El token no es válido
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private JwtParser getParser() {
        return Jwts.parserBuilder().setSigningKey(getKey()).build();
    }

    private boolean isTokenExpired(String token) {
        try {
            Claims claims = getParser().parseClaimsJws(token).getBody();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // Si el token ya está expirado
        } catch ( MalformedJwtException | UnsupportedJwtException e) {
            throw new IllegalArgumentException("El token JWT no es válido.", e);
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar la expiración del token.", e);
        }
    }

}
