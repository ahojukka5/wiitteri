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
    private List<Followers> followers;

    @OneToMany(mappedBy = "from")
    private List<Followers> following;

    public User(String username2, String password, String handle2) {
        this.username = username2;
        this.passwordHash = "";
        this.handle = handle2;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }
}
