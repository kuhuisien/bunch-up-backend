package com.springboot.bunch.controller;

import com.springboot.bunch.payload.BunchDto;
import com.springboot.bunch.payload.BunchResponse;
import com.springboot.bunch.service.BunchService;
import com.springboot.bunch.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/bunches")
public class BunchController {

    private BunchService bunchService;

    public BunchController(BunchService bunchService) {
        this.bunchService = bunchService;
    }

    @PostMapping
    public ResponseEntity<BunchDto> createBunch(Authentication authentication,
                                                @Valid @RequestBody BunchDto bunchDto) {
        return new ResponseEntity<>(bunchService.createBunch(bunchDto,
                authentication.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/favourites")
    public ResponseEntity<String> favouriteBunch(Authentication authentication,
                                                 @PathVariable(name = "id") long id) {
        bunchService.favouriteBunch(id, authentication.getName());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("/{id}/favourites")
    public ResponseEntity<String> unfavouriteBunch(Authentication authentication,
                                                 @PathVariable(name = "id") long id) {
        bunchService.unfavouriteBunch(id, authentication.getName());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping
    public BunchResponse getAllBunches(
            Authentication authentication,
            @RequestParam(value = "pageNo", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        String usernameOrEmail = authentication != null ? authentication.getName() : null;
        return  bunchService.getAllPosts(pageNo, pageSize, sortBy, sortDir, usernameOrEmail);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BunchDto> getBunchById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(bunchService.getBunchById(id));
    }
}
