package wiitteri;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    public List<User> search(String query) {
        logger.debug("Searching by username, query string = " + query);
        return userRepository.findByUsernameContainingIgnoreCase(query);
    }

}
