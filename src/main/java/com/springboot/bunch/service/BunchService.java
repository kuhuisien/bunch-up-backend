package com.springboot.bunch.service;

import com.springboot.bunch.payload.BunchDto;
import com.springboot.bunch.payload.BunchResponse;


public interface BunchService {
    BunchDto createBunch(BunchDto bunchDto, String usernameOrEmail);

    BunchResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    BunchDto getBunchById(long id);

    void favouriteBunch(long id, String usernameOrEmail);

    void unfavouriteBunch(long id, String usernameOrEmail);
}
