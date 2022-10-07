package uz.bob.app_phone_purchases.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.bob.app_phone_purchases.entity.Phone;
import uz.bob.app_phone_purchases.entity.Purchases;
import uz.bob.app_phone_purchases.entity.User;
import uz.bob.app_phone_purchases.payload.ApiResponse;
import uz.bob.app_phone_purchases.repository.PhoneRepository;
import uz.bob.app_phone_purchases.repository.PurchasesRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PurchasesService {

    @Autowired
    PurchasesRepository purchasesRepository;
    @Autowired
    PhoneRepository phoneRepository;

    public ApiResponse purchasePhone(Long id, User user) {
        Optional<Phone> optionalPhone = phoneRepository.findById(id);
        if (!optionalPhone.isPresent()) {
            return new ApiResponse("This type phone not found",false);
        }
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        Purchases purchases=new Purchases(
                optionalPhone.get(),
                user,
                timestamp
        );
        purchasesRepository.save(purchases);
        return new ApiResponse("Phone purchased",true);
    }


    public List<Purchases> view(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Purchases> purchasesPage = purchasesRepository.findAll(pageable);
        return purchasesPage.getContent();
    }


    public Purchases viewById(Long id) {
        Optional<Purchases> optionalPurchases = purchasesRepository.findById(id);
        return optionalPurchases.orElse(null);
    }

    public boolean delete(Long id) {
        try {
            purchasesRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
