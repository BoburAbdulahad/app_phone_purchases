package uz.bob.app_phone_purchases.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.bob.app_phone_purchases.entity.Maker;
import uz.bob.app_phone_purchases.payload.ApiResponse;
import uz.bob.app_phone_purchases.payload.MakerDto;
import uz.bob.app_phone_purchases.repository.MakerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MakerService {

    @Autowired
    MakerRepository makerRepository;

    public ApiResponse addMaker(MakerDto makerDto) {
        if (makerRepository.existsByNameAndModel(makerDto.getName(),makerDto.getModel()))
            return new ApiResponse("Such a maker and model already exist",false);
        Maker maker=new Maker(
                makerDto.getName(),
                makerDto.getModel()
        );
        makerRepository.save(maker);
        return new ApiResponse("Maker has been created",true);
    }


    public List<Maker> viewMakers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Maker> makerPage = makerRepository.findAll(pageable);
        return makerPage.getContent();
    }

    public Maker getMaker(Long id) {
        Optional<Maker> optionalMaker = makerRepository.findById(id);
        return optionalMaker.orElse(null);

    }

    public ApiResponse edit(Long id, MakerDto makerDto) {
        Optional<Maker> optionalMaker = makerRepository.findById(id);
        if (!optionalMaker.isPresent())
            return new ApiResponse("Maker not found",false);
        if (makerRepository.existsByNameAndModelAndIdNot(makerDto.getName(), makerDto.getModel(), id))
            return new ApiResponse("Such maker and model already exist in another id",false);
        Maker maker = optionalMaker.get();
        maker.setName(makerDto.getName());
        maker.setModel(makerDto.getModel());

        Maker editedMaker = makerRepository.save(maker);
        return new ApiResponse("Maker has been edited",true,editedMaker);
    }


}
