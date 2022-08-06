package com.springboot.bunch.service.impl;

import com.springboot.bunch.entity.Bunch;
import com.springboot.bunch.exception.ResourceNotFoundException;
import com.springboot.bunch.payload.BunchDto;
import com.springboot.bunch.repository.BunchRepository;
import com.springboot.bunch.service.BunchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BunchServiceImpl implements BunchService {
    private BunchRepository bunchRepository;

    public BunchServiceImpl(BunchRepository bunchRepository) {
        this.bunchRepository = bunchRepository;
    }

    @Override
    public BunchDto createBunch(BunchDto bunchDto) {
        Bunch bunch = mapDtoToEntity(bunchDto);

        Bunch createdBunch = bunchRepository.save(bunch);

        return  mapEntityToDto(createdBunch);
    }

    @Override
    public List<BunchDto> getAllPosts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Bunch> bunches = bunchRepository.findAll(pageable);

        List<Bunch> listOfBunches = bunches.getContent();

        return listOfBunches.stream().map(bunch -> mapEntityToDto(bunch)).collect(Collectors.toList());
    }

    @Override
    public BunchDto getBunchById(long id) {
        Bunch bunch = bunchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bunch", "id", id));
        return mapEntityToDto(bunch);
    }

    private BunchDto mapEntityToDto(Bunch bunch) {
        BunchDto bunchDto = new BunchDto();
        bunchDto.setId(bunch.getId());
        bunchDto.setTitle(bunch.getTitle());
        bunchDto.setSubtitle(bunch.getSubtitle());
        bunchDto.setDescription(bunch.getDescription());
        bunchDto.setImageUrl(bunch.getImageUrl());
        bunchDto.setEmail(bunch.getEmail());
        bunchDto.setAddress(bunch.getAddress());
        return  bunchDto;
    }

    private Bunch mapDtoToEntity(BunchDto bunchDto) {
        Bunch bunch = new Bunch();
        bunch.setTitle(bunchDto.getTitle());
        bunch.setSubtitle(bunchDto.getSubtitle());
        bunch.setDescription(bunchDto.getDescription());
        bunch.setImageUrl(bunchDto.getImageUrl());
        bunch.setEmail(bunchDto.getEmail());
        bunch.setAddress(bunchDto.getAddress());
        return  bunch;
    }
}
