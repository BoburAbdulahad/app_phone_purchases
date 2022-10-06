package uz.bob.app_phone_purchases.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.bob.app_phone_purchases.entity.Purchases;
import uz.bob.app_phone_purchases.entity.User;
import uz.bob.app_phone_purchases.payload.ApiResponse;
import uz.bob.app_phone_purchases.security.CurrentUser;
import uz.bob.app_phone_purchases.service.PurchasesService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchasesController {

    @Autowired
    PurchasesService purchasesService;

    @PostMapping("/{id}")
    public HttpEntity<?> purchasePhone(@Valid @PathVariable Long id, @CurrentUser User user){
        ApiResponse apiResponse=purchasesService.purchasePhone(id,user);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_PURCHASES')")
    @GetMapping
    public HttpEntity<?> view(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
        List<Purchases> view = purchasesService.view(page, size);
        return new HttpEntity<>(view);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_PURCHASES')")
    @GetMapping("/{id}")
    public HttpEntity<?> viewById(@PathVariable Long id){
        Purchases purchase = purchasesService.viewById(id);
        return ResponseEntity.status(purchase!=null?200:404).body(purchase);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_PURCHASES')")
    public HttpEntity<?> delete(@PathVariable Long id){
        boolean delete = purchasesService.delete(id);
        return delete?ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }


}
