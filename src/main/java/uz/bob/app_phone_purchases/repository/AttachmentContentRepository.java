package uz.bob.app_phone_purchases.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.app_phone_purchases.entity.Attachment;
import uz.bob.app_phone_purchases.entity.AttachmentContent;

@Repository
public interface AttachmentContentRepository extends JpaRepository<AttachmentContent,Long> {

    AttachmentContent findByAttachmentId(Long attachment_id);

}
