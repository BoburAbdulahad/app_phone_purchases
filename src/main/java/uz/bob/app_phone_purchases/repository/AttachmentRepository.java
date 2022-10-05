package uz.bob.app_phone_purchases.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.app_phone_purchases.entity.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,Long> {

}
