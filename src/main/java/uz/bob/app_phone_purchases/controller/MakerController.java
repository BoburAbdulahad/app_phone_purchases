package uz.bob.app_phone_purchases.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.bob.app_phone_purchases.entity.Maker;
import uz.bob.app_phone_purchases.payload.ApiResponse;
import uz.bob.app_phone_purchases.payload.MakerDto;
import uz.bob.app_phone_purchases.service.MakerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/maker")
public class MakerController {

    @Autowired
    MakerService makerService;

    @PreAuthorize(value = "hasAuthority('ADD_MAKER')")
    @PostMapping
    public HttpEntity<?> addMaker(@Valid @RequestBody MakerDto makerDto){
        ApiResponse apiResponse=makerService.addMaker(makerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @GetMapping
    public HttpEntity<?> viewMakers(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
        List<Maker> makers = makerService.viewMakers(page, size);
        return new HttpEntity<>(makers);
    }

    @GetMapping("/{id}")
    public HttpEntity<Maker> getMaker(@PathVariable Long id){
        Maker maker = makerService.getMaker(id);
        return ResponseEntity.status(maker!=null? HttpStatus.OK:HttpStatus.NOT_FOUND).body(maker);
    }


    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Long id,
                              @Valid @RequestBody MakerDto makerDto){
        ApiResponse apiResponse = makerService.edit(id, makerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



}
