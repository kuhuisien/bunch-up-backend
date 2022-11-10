package com.springboot.bunch.payload;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonalBunchDto extends BunchDto{
    private boolean favourite;
}
