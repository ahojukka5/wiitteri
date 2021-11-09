package wiitteri.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @ManyToMany
    private Set<Account> likes;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private TweetKind kind;

    public Tweet(TweetKind kind, Account owner, String content) {
        this.owner = owner;
        this.content = content;
        this.created = LocalDateTime.now();
        this.likes = new HashSet<>();
        this.kind = kind;
    }

    public int getNumberOfLikes() {
        return likes.size();
    }
}
