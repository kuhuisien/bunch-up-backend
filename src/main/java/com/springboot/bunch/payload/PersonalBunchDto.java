package com.springboot.bunch.payload;

import lombok.Data;

@Data
public class PersonalBunchDto extends BunchDto{
    private boolean favourite;
}
