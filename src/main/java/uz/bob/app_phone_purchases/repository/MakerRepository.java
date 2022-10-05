package uz.bob.app_phone_purchases.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.app_phone_purchases.entity.Maker;

@Repository
public interface MakerRepository extends JpaRepository<Maker,Long> {

    boolean existsByNameAndModel(String name, String model);

    boolean existsByNameAndModelAndIdNot(String name, String model, Long id);

}
