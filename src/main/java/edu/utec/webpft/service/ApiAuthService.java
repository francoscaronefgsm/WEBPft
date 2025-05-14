package edu.utec.webpft.service;

import edu.utec.webpft.dtos.LoginDto;
import edu.utec.webpft.dtos.UsuarioDto;
import edu.utec.webpft.entidades.Usuario;
import edu.utec.webpft.security.CustomUserDetailsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiAuthService {

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<AuthResponse> login(LoginDto loginDto) {

        Usuario user = userService.findByUsername(loginDto.getUsuario());


        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else if(!user.isActivo()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsuario(), loginDto.getContrasena()));
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getUsuario());
            String token = jwtService.getToken(userDetails);
            AuthResponse authResponse = new AuthResponse(token, user);
            return ResponseEntity.ok(authResponse);
        }
    }

    @Setter
    @Getter
    public static class AuthResponse {
        // Getters y setters
        private String token;
        private UsuarioDto usuarioDTO;

        public AuthResponse(String token, Usuario usuario) {
            this.token = token;
            this.usuarioDTO = new UsuarioDto(
                    usuario.getId(),
                    usuario.getNombreUsuario(),
                    usuario.getContrasena(),
                    usuario.getPrimerNombre(),
                    usuario.getSegundoNombre(),
                    usuario.getPrimerApellido(),
                    usuario.getSegundoApellido(),
                    usuario.getDocumento(),
                    usuario.getFechaNacimiento(),
                    usuario.getCorreoPersonal(),
                    usuario.getTelefono(),
                    usuario.getDepartamento().getId(),
                    usuario.getLocalidad().getId(),
                    usuario.getCorreoInstitucional(),
                    usuario.getItr().getId(),
                    usuario.isActivo()
            );
        }

    }
}

