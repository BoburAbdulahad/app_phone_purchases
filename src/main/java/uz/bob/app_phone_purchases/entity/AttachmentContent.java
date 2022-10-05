package uz.bob.app_phone_purchases.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.bob.app_phone_purchases.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AttachmentContent extends AbstractEntity {

    @Column(nullable = false)
    private byte[] mainContent;

    @OneToOne(optional = false)
    private Attachment attachment;
}
