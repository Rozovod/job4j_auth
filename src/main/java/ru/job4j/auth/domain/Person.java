package ru.job4j.auth.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Login must be not null")
    private String login;
    @NotBlank(message = "Password must be not null")
    @Pattern(regexp = ".[A-ZА-Я].", message = "Password must contain at least one uppercase letter")
    private String password;
}
