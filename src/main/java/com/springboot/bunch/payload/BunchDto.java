package com.springboot.bunch.payload;

import lombok.Data;

import javax.persistence.Column;

@Data

public class BunchDto {
    private Long id;

    private String title;

    private String subtitle;

    private String description;

    private String imageUrl;

    private String address;

    private String email;
}
