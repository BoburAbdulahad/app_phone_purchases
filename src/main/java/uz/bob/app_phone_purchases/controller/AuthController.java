package uz.bob.app_phone_purchases.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.bob.app_phone_purchases.entity.Role;
import uz.bob.app_phone_purchases.entity.User;
import uz.bob.app_phone_purchases.payload.ApiResponse;
import uz.bob.app_phone_purchases.payload.LoginDto;
import uz.bob.app_phone_purchases.payload.RegisterDto;
import uz.bob.app_phone_purchases.security.JwtProvider;
import uz.bob.app_phone_purchases.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto){
        ApiResponse apiResponse=authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> loginUser(@Valid @RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()
        ));
        User user = (User) authentication.getPrincipal();
        Role role = user.getRole();
        String token = jwtProvider.generateToken(user.getUsername(), role);

        return ResponseEntity.ok(token);
    }




}
