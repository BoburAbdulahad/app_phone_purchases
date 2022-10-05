package uz.bob.app_phone_purchases.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.bob.app_phone_purchases.entity.Role;
import uz.bob.app_phone_purchases.payload.ApiResponse;
import uz.bob.app_phone_purchases.payload.RegisterDto;
import uz.bob.app_phone_purchases.payload.RoleDto;
import uz.bob.app_phone_purchases.service.AuthService;
import uz.bob.app_phone_purchases.service.RoleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @PostMapping
    public HttpEntity<?> addRole(@Valid @RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = roleService.addRole(roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_ROLE')")
    @GetMapping
    public HttpEntity<?> view(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        List<Role> roleList = roleService.view(page, size);
        return new HttpEntity<>(roleList);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_ROLE')")
    @GetMapping("/{id}")
    public HttpEntity<?> getRole(@PathVariable Long id) {
        Role role = roleService.getRole(id);
        return ResponseEntity.status(role != null ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(role);
    }

    @PreAuthorize("hasAuthority('EDIT_ROLE')")
    @PutMapping("/{id}")
    public HttpEntity<?> editRole(@PathVariable Long id,
                                  @Valid @RequestBody RoleDto roleDTO) {
        ApiResponse apiResponse = roleService.editRole(id, roleDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);

    }


    @PreAuthorize(value = "hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        boolean delete = roleService.delete(id);
        return delete ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


}
