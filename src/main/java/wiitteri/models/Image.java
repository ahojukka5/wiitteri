package wiitteri.models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
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
public class Image extends AbstractPersistable<Long> {

    @NotEmpty
    private String description;

    @ManyToOne
    private Account owner;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;
}
