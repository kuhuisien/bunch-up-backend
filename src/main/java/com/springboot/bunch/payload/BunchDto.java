package com.springboot.bunch.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data

public class BunchDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 2, message = "Subtitle should have at least 2 characters")
    private String subtitle;

    @NotEmpty
    private String description;

    @NotEmpty
    private String imageUrl;

    @NotEmpty
    private String address;

    @Email
    private String email;
}
