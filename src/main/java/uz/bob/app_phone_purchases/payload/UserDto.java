package uz.bob.app_phone_purchases.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    @NotNull(message = "Full Name must not be empty")
    private String fullName;

    @NotNull(message = "Username must not be empty")
    private String username;

    @NotNull(message = "Password must not be empty")
    private String password;

    @NotNull(message = "roleId is null")
    private Long roleId;

}
