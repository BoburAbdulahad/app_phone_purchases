package uz.bob.app_phone_purchases.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MakerDto {

    @NotBlank(message = "Maker name is null")
    private String name;

    @NotBlank(message = "Maker model is null")
    private String model;
}
