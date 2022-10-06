package uz.bob.app_phone_purchases.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bob.app_phone_purchases.entity.Phone;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone,Long> {

    boolean existsByMakerIdAndColorAndPrice(Long maker_id, String color, double price);

    boolean existsByMakerIdAndColorAndPriceAndIdNot(Long maker_id, String color, double price, Long id);

    //===searching===/
    @Query(value = " select p.id,p.created_at,p.created_by,p.updated_at,p.updated_by,p.active,p.color,p.price,p.maker_id,p.photo_id from phone p join maker m on p.maker_id=m.id  where m.name=?1",nativeQuery = true)
    List<Phone> getByMaker(String maker);

    List<Phone> findAllByPrice(double priceD);

    List<Phone> findAllByColor(String color);



}
