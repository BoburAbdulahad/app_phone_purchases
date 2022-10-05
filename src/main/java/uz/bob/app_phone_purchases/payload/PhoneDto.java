package uz.bob.app_phone_purchases.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PhoneDto {

    @NotNull
    private Long makerId;

    @NotNull
    private String color;

    @NotNull
    private double price;

    @NotNull
    private Long photoId;


}
