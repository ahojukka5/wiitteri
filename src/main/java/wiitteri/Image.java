package wiitteri;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Image extends AbstractPersistable<Long> {

    private String description;

    @ManyToOne
    private User owner;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;
}
