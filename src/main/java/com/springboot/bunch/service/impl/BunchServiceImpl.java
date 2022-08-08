package com.springboot.bunch.service.impl;

import com.springboot.bunch.entity.Bunch;
import com.springboot.bunch.exception.ResourceNotFoundException;
import com.springboot.bunch.payload.BunchDto;
import com.springboot.bunch.payload.BunchResponse;
import com.springboot.bunch.repository.BunchRepository;
import com.springboot.bunch.service.BunchService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BunchServiceImpl implements BunchService {
    private BunchRepository bunchRepository;

    private ModelMapper mapper;

    public BunchServiceImpl(BunchRepository bunchRepository,ModelMapper mapper) {
        this.bunchRepository = bunchRepository;
        this.mapper = mapper;
    }

    @Override
    public BunchDto createBunch(BunchDto bunchDto) {
        Bunch bunch = mapDtoToEntity(bunchDto);

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

    private BunchDto mapEntityToDto(Bunch bunch) {
        return mapper.map(bunch, BunchDto.class);
    }

    private Bunch mapDtoToEntity(BunchDto bunchDto) {
        return mapper.map(bunchDto, Bunch.class);
    }
}
