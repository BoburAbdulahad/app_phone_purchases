package uz.bob.app_phone_purchases.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.bob.app_phone_purchases.entity.User;
import uz.bob.app_phone_purchases.exceptions.ResourceNotFoundException;
import uz.bob.app_phone_purchases.payload.ApiResponse;
import uz.bob.app_phone_purchases.payload.RegisterDto;
import uz.bob.app_phone_purchases.repository.RoleRepository;
import uz.bob.app_phone_purchases.repository.UserRepository;
import uz.bob.app_phone_purchases.utils.AppConstants;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public ApiResponse registerUser(RegisterDto registerDto) {
        if (!registerDto.getPassword().equals(registerDto.getPrePassword()))
            return new ApiResponse("Passwords do not match",false);
        if (userRepository.existsByUsername(registerDto.getUsername()))
            return new ApiResponse("Such a user has already registered",false);
        User user=new User(
                registerDto.getFullName(),
                registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword()),
                roleRepository.findByName(AppConstants.USER).orElseThrow(() -> new ResourceNotFoundException("roleTable","name",AppConstants.USER)),
                true
        );
        userRepository.save(user);
return new ApiResponse("User successfully registered",true);
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

    }
}
