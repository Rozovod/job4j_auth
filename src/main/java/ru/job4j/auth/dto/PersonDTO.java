package ru.job4j.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class PersonDTO {
    @NotBlank(message = "Password must be not null")
    @Pattern(regexp = ".[A-ZА-Я].", message = "Password must contain at least one uppercase letter")
    private String password;
}
