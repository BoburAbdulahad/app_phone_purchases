package uz.bob.app_phone_purchases.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import uz.bob.app_phone_purchases.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Purchases extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Phone phone;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private User user;

    @Column(updatable = false,nullable = false)
    @CreationTimestamp
    private Timestamp dateAndTime;


}
