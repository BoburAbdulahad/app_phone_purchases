package uz.bob.app_phone_purchases.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.app_phone_purchases.entity.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone,Long> {

    boolean existsByMakerIdAndColorAndPrice(Long maker_id, String color, double price);

    boolean existsByMakerIdAndColorAndPriceAndIdNot(Long maker_id, String color, double price, Long id);
}
