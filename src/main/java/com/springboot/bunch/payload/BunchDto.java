package com.springboot.bunch.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data

public class BunchDto {
    private Long id;

    @Size(min = 2, message = "Title should have at least 2 characters")
    private String title;

    @Size(min = 2, message = "Subtitle should have at least 2 characters")
    private String subtitle;

    @NotEmpty(message = "Description should not be null or empty")
    private String description;

    @NotEmpty(message = "Image url should not be null or empty")
    private String imageUrl;

    @NotEmpty(message = "Address should not be null or empty")
    private String address;

    @NotEmpty(message = "Email should not be null or empty")
    @Email(message = "Email is invalid")
    private String email;
}
