package com.springboot.bunch.service;

import com.springboot.bunch.payload.BunchDto;
import com.springboot.bunch.payload.BunchResponse;


public interface BunchService {
    BunchDto createBunch(BunchDto bunchDto);

    BunchResponse getAllPosts(int pageNo, int pageSize);

    BunchDto getBunchById(long id);
}
