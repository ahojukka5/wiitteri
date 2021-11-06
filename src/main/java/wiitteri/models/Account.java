package wiitteri.models;

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
    private String password;
    private String handle;

    @OneToMany(mappedBy = "to")
    private List<Connection> followers;

    @OneToMany(mappedBy = "from")
    private List<Connection> following;

    public Account(String username, String password, String handle) {
        this.username = username;
        this.password = password;
        this.handle = handle;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }
}
