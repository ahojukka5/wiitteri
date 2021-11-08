package wiitteri.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "from_user_fk", "to_user_fk" }) })
public class Connection extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "from_user_fk")
    private Account from;

    @ManyToOne
    @JoinColumn(name = "to_user_fk")
    private Account to;

    private LocalDateTime created;
    private boolean active;

    public Connection(Account from, Account to) {
        this.from = from;
        this.to = to;
        this.created = LocalDateTime.now();
        this.active = true;
    }
}
