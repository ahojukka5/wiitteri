package wiitteri.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public boolean isLogged() {
        String username = getUsername();
        if (username != "anonymousUser") {
            logger.debug("User is logged in with username " + username);
            return true;
        }
        logger.debug("User is not logged in");
        return false;
    }

}
