package wiitteri;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Connection extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "from_user_fk")
    private User from;

    @ManyToOne
    @JoinColumn(name = "to_user_fk")
    private User to;

    private LocalDateTime created;
    private boolean active;

    public Connection(User from, User to) {
        this.from = from;
        this.to = to;
        this.created = LocalDateTime.now();
        this.active = true;
    }
}
