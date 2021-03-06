package wiitteri.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wiitteri.models.Account;
import wiitteri.repositories.AccountRepository;

@Service
public class SearchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> search(String query) {
        logger.debug("Searching by username, query string = " + query);
        return accountRepository.findByUsernameContainingIgnoreCase(query);
    }

    public List<Account> searchByName(String query) {
        return accountRepository.findByNameContainingIgnoreCase(query);
    }

}
