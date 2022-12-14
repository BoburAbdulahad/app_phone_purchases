package uz.bob.app_phone_purchases.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.bob.app_phone_purchases.entity.Phone;
import uz.bob.app_phone_purchases.payload.ApiResponse;
import uz.bob.app_phone_purchases.payload.PhoneDto;
import uz.bob.app_phone_purchases.service.PhoneService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/phone")
public class PhoneController {

    @Autowired
    PhoneService phoneService;

    @PreAuthorize(value = "hasAuthority('ADD_PHONE')")
    @PostMapping
    public HttpEntity<?> addPhone(@Valid @RequestBody PhoneDto phoneDto){
        ApiResponse apiResponse=phoneService.addPhone(phoneDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_PHONE')")
    @GetMapping
    public HttpEntity<?> view(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
        List<Phone> makers = phoneService.view(page, size);
        return new HttpEntity<>(makers);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_PHONE')")
    @GetMapping("/{id}")
    public HttpEntity<Phone> getPhone(@PathVariable Long id){
        Phone phone = phoneService.getPhone(id);
        return ResponseEntity.status(phone!=null? HttpStatus.OK:HttpStatus.NOT_FOUND).body(phone);
    }


    @PreAuthorize(value = "hasAuthority('EDIT_PHONE')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Long id,
                              @Valid @RequestBody PhoneDto phoneDto){
        ApiResponse apiResponse = phoneService.edit(id, phoneDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PreAuthorize(value = "hasAuthority('DELETE_PHONE')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id){
        boolean delete = phoneService.delete(id);
        return delete?ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }


    //==========================SEARCH==============================//

    @GetMapping("/searchMaker/{maker}")
    public HttpEntity<?> searchByMaker(@PathVariable String maker){
        List<Phone> searchByMaker = phoneService.searchByMaker(maker);
        return new HttpEntity<>(searchByMaker);
    }


    @GetMapping("searchPrice/{price}")
    public HttpEntity<?> searchByPrice(@PathVariable double price){
        List<Phone> searchByPrice = phoneService.searchByPrice(price);
        return new HttpEntity<>(searchByPrice);
    }

    @GetMapping("searchColor/{color}")
    public HttpEntity<?> searchByColor(@PathVariable String color){
        List<Phone> searchByColor = phoneService.searchByColor(color);
        return new HttpEntity<>(searchByColor);
    }





}
