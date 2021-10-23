package wiitteri;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account extends AbstractPersistable<Long> {

    private String username;
    private String passwordHash;
    private String handle;

    @OneToMany
    private List<Account> followedUsers;

    public Account(String username2, String password, String handle2) {
        this.username = username2;
        this.passwordHash = "";
        this.handle = handle2;
        this.followedUsers = new ArrayList<>();
    }
}
