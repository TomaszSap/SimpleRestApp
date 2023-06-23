package com.tomaszsap.simplerestapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @JsonIgnore
    private int id;
    @NotBlank(message = "Name must be not blank")
    @Size(min = 2, max = 100, message = "Name should be min 2 symbols and less than 100")
    String name;
    @NotBlank(message = "Surname must be not blank")
    @Size(min = 2, max = 100, message = "Surname should be min 2 symbols and less than 100")
    String surname;
    @NotBlank(message = "Email must not be blank")

    @Email(message = "Please provide a valid email address")
    String email;

    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{9})", message = "Mobile number must be 9 digits")
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    @NotBlank(message = "PESEL must not be blank")
    @Pattern(regexp = "(^$|[0-9]{11})", message = "PESEL  must be 11 digits")
    String pesel;
}
