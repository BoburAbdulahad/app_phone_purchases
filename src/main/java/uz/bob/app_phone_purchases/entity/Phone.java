package uz.bob.app_phone_purchases.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.bob.app_phone_purchases.entity.template.AbstractEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Phone extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    private Maker maker;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private double price;

    @OneToOne(fetch = FetchType.EAGER,optional = false)
    private Attachment photo;

    private boolean active=true;







}
