package wiitteri.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.domain.AbstractPersistable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Image extends AbstractPersistable<Long> {

    @NotEmpty
    private String description;

    @ManyToOne
    private Account owner;

    @ManyToMany
    private Set<Account> likes;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    public Image(String description, Account owner, byte[] content) {
        this.description = description;
        this.owner = owner;
        this.likes = new HashSet<>();
        this.content = content;
    }

    public int getNumberOfLikes() {
        return likes.size();
    }

}
