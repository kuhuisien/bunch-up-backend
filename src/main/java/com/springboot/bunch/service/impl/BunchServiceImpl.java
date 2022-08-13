package com.springboot.bunch.service.impl;

import com.springboot.bunch.entity.Bunch;
import com.springboot.bunch.entity.User;
import com.springboot.bunch.exception.BunchAPIException;
import com.springboot.bunch.exception.ResourceNotFoundException;
import com.springboot.bunch.payload.BunchDto;
import com.springboot.bunch.payload.BunchResponse;
import com.springboot.bunch.repository.BunchRepository;
import com.springboot.bunch.repository.UserRepository;
import com.springboot.bunch.service.BunchService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BunchServiceImpl implements BunchService {
    private BunchRepository bunchRepository;

    private UserRepository userRepository;

    private ModelMapper mapper;

    public BunchServiceImpl(BunchRepository bunchRepository,
                            UserRepository userRepository,
                            ModelMapper mapper) {
        this.bunchRepository = bunchRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public BunchDto createBunch(BunchDto bunchDto, String usernameOrEmail) {
        User user =  userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow( () ->
                new BunchAPIException(HttpStatus.BAD_REQUEST, "user error")
        );

        Bunch bunch = mapDtoToEntity(bunchDto, user);

        Bunch createdBunch = bunchRepository.save(bunch);

        return  mapEntityToDto(createdBunch);
    }

    @Override
    public BunchResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Bunch> bunches = bunchRepository.findAll(pageable);

        List<Bunch> listOfBunches = bunches.getContent();

        List<BunchDto> content = listOfBunches.stream()
                .map(bunch -> mapEntityToDto(bunch)).collect(Collectors.toList());

        BunchResponse bunchResponse = new BunchResponse();
        bunchResponse.setContent(content);
        bunchResponse.setPageNo(pageNo);
        bunchResponse.setPageSize(pageSize);
        bunchResponse.setTotalElements(bunches.getTotalElements());
        bunchResponse.setTotalPages(bunches.getTotalPages());
        bunchResponse.setLast(bunches.isLast());

        return bunchResponse;
    }

    @Override
    public BunchDto getBunchById(long id) {
        Bunch bunch = bunchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bunch", "id", id));
        return mapEntityToDto(bunch);
    }

    @Override
    public void favouriteBunch(long id, String usernameOrEmail) {
        User user =  userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow( () ->
                        new BunchAPIException(HttpStatus.BAD_REQUEST, "user error")
                );

        Bunch bunch = bunchRepository.
                findById(id).orElseThrow(() -> new ResourceNotFoundException("Bunch", "id", id));

        Set<Bunch> bunchSet = user.getFavouriteBunches();
        if (bunchSet.contains((bunch))) {
            throw new BunchAPIException(HttpStatus.BAD_REQUEST, "user already favourited this bunch before");
        }

        bunchSet.add(bunch);
        user.setFavouriteBunches(bunchSet);
        userRepository.save(user);
    }

    @Override
    public void unfavouriteBunch(long id, String usernameOrEmail) {
        User user =  userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow( () ->
                        new BunchAPIException(HttpStatus.BAD_REQUEST, "user error")
                );

        Bunch bunch = bunchRepository.
                findById(id).orElseThrow(() -> new ResourceNotFoundException("Bunch", "id", id));

        Set<Bunch> bunchSet = user.getFavouriteBunches();
        if (!bunchSet.contains((bunch))) {
            throw new BunchAPIException(HttpStatus.BAD_REQUEST, "user has not favourited this bunch before");
        }

        bunchSet.remove(bunch);
        user.setFavouriteBunches(bunchSet);
        userRepository.save(user);
    }

    private BunchDto mapEntityToDto(Bunch bunch) {
        return mapper.map(bunch, BunchDto.class);
    }

    private Bunch mapDtoToEntity(BunchDto bunchDto, User user) {
        Bunch bunch = mapper.map(bunchDto, Bunch.class);
        bunch.setUser(user);
        return bunch;
    }
}
