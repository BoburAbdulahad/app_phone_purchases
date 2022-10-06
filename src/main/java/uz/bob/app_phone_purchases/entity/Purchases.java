package uz.bob.app_phone_purchases.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.bob.app_phone_purchases.entity.template.AbstractEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Purchases extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Phone phone;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private User user;

    @Column(updatable = false,nullable = false)
    @CreationTimestamp
    private Timestamp dateAndTime;

    public Purchases(Phone phone, User user) {
        this.phone = phone;
        this.user = user;
    }
}
