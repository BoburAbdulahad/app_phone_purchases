package uz.bob.app_phone_purchases.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.bob.app_phone_purchases.entity.Role;
import uz.bob.app_phone_purchases.entity.User;
import uz.bob.app_phone_purchases.entity.enums.Permission;
import uz.bob.app_phone_purchases.repository.RoleRepository;
import uz.bob.app_phone_purchases.repository.UserRepository;
import uz.bob.app_phone_purchases.utils.AppConstants;

import java.util.Arrays;

import static uz.bob.app_phone_purchases.entity.enums.Permission.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("ALWAYS")){
            Permission[] permissions = Permission.values();
            Role admin = roleRepository.save(new Role(
                    AppConstants.ADMIN,
                    Arrays.asList(permissions),"This is admin role"
            ));

            Role operator = roleRepository.save(new Role(
                    AppConstants.OPERATOR,
                    Arrays.asList(ADD_PHONE, EDIT_PHONE, DELETE_PHONE,
                            ADD_MAKER, EDIT_MAKER, DELETE_MAKER,
                            ADD_PHOTO, EDIT_PHOTO, DELETE_PHOTO,
                            VIEW_PHONE),"This is operator role"
            ));

            Role user = roleRepository.save(new Role(
                    AppConstants.USER, Arrays.asList(VIEW_PHONE),"This is user role"
            ));


            userRepository.save(
                    new User(
                            "Admin",
                            "admin",
                            passwordEncoder.encode("admin123"),
                            admin,
                            true
                    )
            );

            userRepository.save(
                    new User(
                            "Operator",
                            "operator",
                            passwordEncoder.encode("operator123"),
                            operator,
                            true
                    )
            );

            userRepository.save(new User(
                    "User",
                    "user",
                    passwordEncoder.encode("user123"),
                    user,
                    true
                   )
            );

        }

    }
}
