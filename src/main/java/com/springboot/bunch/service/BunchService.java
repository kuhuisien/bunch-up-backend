package com.springboot.bunch.service;

import com.springboot.bunch.payload.BunchDto;

import java.util.List;

public interface BunchService {
    BunchDto createBunch(BunchDto bunchDto);

    List<BunchDto> getAllPosts();

    BunchDto getBunchById(long id);
}
