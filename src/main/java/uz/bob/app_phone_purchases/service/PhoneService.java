package uz.bob.app_phone_purchases.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.bob.app_phone_purchases.entity.Attachment;
import uz.bob.app_phone_purchases.entity.Maker;
import uz.bob.app_phone_purchases.entity.Phone;
import uz.bob.app_phone_purchases.payload.ApiResponse;
import uz.bob.app_phone_purchases.payload.PhoneDto;
import uz.bob.app_phone_purchases.repository.AttachmentRepository;
import uz.bob.app_phone_purchases.repository.MakerRepository;
import uz.bob.app_phone_purchases.repository.PhoneRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    @Autowired
    PhoneRepository phoneRepository;
    @Autowired
    MakerRepository makerRepository;
    @Autowired
    AttachmentRepository attachmentRepository;


    public ApiResponse addPhone(PhoneDto phoneDto) {
        if (phoneRepository.existsByMakerIdAndColorAndPrice(phoneDto.getMakerId(), phoneDto.getColor(), phoneDto.getPrice())) {
            return new ApiResponse("There is already such a phone with maker, color and price",false);
        }
        Optional<Maker> optionalMaker = makerRepository.findById(phoneDto.getMakerId());
        if (!optionalMaker.isPresent())
            return new ApiResponse("Maker not found",false);
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(phoneDto.getPhotoId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("Photo not found",false);
        Phone phone=new Phone(
                optionalMaker.get(),
                phoneDto.getColor(),
                phoneDto.getPrice(),
                optionalAttachment.get(),
                true
        );

        phoneRepository.save(phone);
        return new ApiResponse("Phone has been created",true);
    }


    public List<Phone> view(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Phone> phonePage = phoneRepository.findAll(pageable);
        return phonePage.getContent();
    }


    public Phone getPhone(Long id) {
        Optional<Phone> optionalPhone = phoneRepository.findById(id);
        return optionalPhone.orElse(null);
    }


    public ApiResponse edit(Long id, PhoneDto phoneDto) {
        Optional<Phone> optionalPhone = phoneRepository.findById(id);
        if (!optionalPhone.isPresent()) {
            return new ApiResponse("Phone not found",false);
        }
        if (phoneRepository.existsByMakerIdAndColorAndPriceAndIdNot(phoneDto.getMakerId(), phoneDto.getColor(), phoneDto.getPrice(), id)) {
            return new ApiResponse("There is already such a phone with maker, color and price in other id",false);
        }
        Phone editingPhone = optionalPhone.get();
        editingPhone.setMaker(makerRepository.findById(phoneDto.getMakerId()).orElse(null));
        editingPhone.setColor(phoneDto.getColor());
        editingPhone.setPrice(phoneDto.getPrice());
        editingPhone.setPhoto(attachmentRepository.findById(phoneDto.getPhotoId()).orElse(null));

        Phone savedPhone = phoneRepository.save(editingPhone);
        return new ApiResponse("Phone successfully edited",true,savedPhone);
    }


    public boolean delete(Long id) {
        try {
            phoneRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<Phone> searchByMaker(String maker) {
        List<Phone> byMaker = phoneRepository.getByMaker(maker);
        return byMaker;
    }
}
