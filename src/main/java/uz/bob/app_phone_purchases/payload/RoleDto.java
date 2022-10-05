package uz.bob.app_phone_purchases.payload;

import lombok.Data;
import uz.bob.app_phone_purchases.entity.enums.Permission;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class RoleDto {

    @NotBlank
    private String name;

    @NotEmpty
    private List<Permission> permissionList;

    private String description;


}
