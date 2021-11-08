package wiitteri.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.domain.AbstractPersistable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tweet extends AbstractPersistable<Long> {
    @ManyToOne
    private Account owner;

    @NotEmpty
    private String content;

    private LocalDateTime created;

    public Tweet(Account owner, String content) {
        this.owner = owner;
        this.content = content;
        this.created = LocalDateTime.now();
    }
}
