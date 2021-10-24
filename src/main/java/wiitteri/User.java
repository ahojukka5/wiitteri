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
public class User extends AbstractPersistable<Long> {

    private String username;
    private String passwordHash;
    private String handle;

    @OneToMany(mappedBy = "to")
    private List<Connection> followers;

    @OneToMany(mappedBy = "from")
    private List<Connection> following;

    public User(String username, String password, String handle) {
        this.username = username;
        this.passwordHash = "";
        this.handle = handle;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }
}
