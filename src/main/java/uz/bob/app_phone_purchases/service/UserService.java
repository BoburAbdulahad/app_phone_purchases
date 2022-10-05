package uz.bob.app_phone_purchases.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.bob.app_phone_purchases.entity.User;
import uz.bob.app_phone_purchases.exceptions.ResourceNotFoundException;
import uz.bob.app_phone_purchases.payload.ApiResponse;
import uz.bob.app_phone_purchases.payload.UserDto;
import uz.bob.app_phone_purchases.repository.RoleRepository;
import uz.bob.app_phone_purchases.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    public ApiResponse adduser(UserDto userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return new ApiResponse("Username already exist",false);
        }
        User user=new User(
                userDTO.getFullName(),
                userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword()),
                roleRepository.findById(userDTO.getRoleId()).orElseThrow(() ->
                        new ResourceNotFoundException("roleTable","roleName",userDTO.getRoleId())),
                true
        );
        userRepository.save(user);
        return new ApiResponse("User added by admin",true);
    }


    public List<User> view(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.getContent();
    }


    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
//        return userRepository.findById(id).orElse(null);
    }


    public ApiResponse editUser(Long id, UserDto userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("User not found",false);
        if (roleRepository.existsByNameAndIdNot(userDTO.getUsername(), id)) {
            return new ApiResponse("User with username and with id already exist",false);
        }
        User user = optionalUser.get();
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(roleRepository.findById(userDTO.getRoleId()).orElseThrow(() ->
                new ResourceNotFoundException("roleTable","roleName",userDTO.getRoleId())));

        User editedUser = userRepository.save(user);
        return new ApiResponse("User successfully edited",true,editedUser);
    }


    public boolean delete(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
