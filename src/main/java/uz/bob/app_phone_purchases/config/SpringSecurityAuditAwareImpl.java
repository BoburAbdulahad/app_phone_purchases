package uz.bob.app_phone_purchases.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.bob.app_phone_purchases.entity.User;

import java.util.Optional;

public class SpringSecurityAuditAwareImpl implements AuditorAware {
    @Override
    public Optional getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.isAuthenticated() &&
                !authentication.getPrincipal().equals("anonymousUser")
        ) {
            User user = (User) authentication.getPrincipal();
            return Optional.of(user.getId());
        }

        return Optional.empty();
    }
}
