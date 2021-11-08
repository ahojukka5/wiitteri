package wiitteri.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account extends AbstractPersistable<Long> {

    @NotEmpty
    @Size(min = 2, max = 30)
    private String name;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String handle;

    @OneToMany(mappedBy = "to")
    private List<Connection> followers;

    @OneToMany(mappedBy = "from")
    private List<Connection> following;

    public Account(String name, String username, String password, String handle) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.handle = handle;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }
}
