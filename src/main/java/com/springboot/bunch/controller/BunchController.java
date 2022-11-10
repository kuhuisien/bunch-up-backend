package com.springboot.bunch.controller;

import com.springboot.bunch.payload.BunchDto;
import com.springboot.bunch.payload.BunchResponse;
import com.springboot.bunch.service.BunchService;
import com.springboot.bunch.utils.AppConstant;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/bunches")
public class BunchController {

    private BunchService bunchService;

    public BunchController(BunchService bunchService) {
        this.bunchService = bunchService;
    }

    @Operation(summary = "Create Bunch")
    @PostMapping
    public ResponseEntity<BunchDto> createBunch(Authentication authentication,
                                                @Valid @RequestBody BunchDto bunchDto) {
        return new ResponseEntity<>(bunchService.createBunch(bunchDto,
                authentication.getName()), HttpStatus.CREATED);
    }

    @Operation(summary = "Favourite Bunch by Id")
    @PutMapping("/{id}/favourites")
    public ResponseEntity<String> favouriteBunch(Authentication authentication,
                                                 @PathVariable(name = "id") long id) {
        bunchService.favouriteBunch(id, authentication.getName());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Operation(summary = "Unfavourite Bunch by Id")
    @DeleteMapping("/{id}/favourites")
    public ResponseEntity<String> unfavouriteBunch(Authentication authentication,
                                                 @PathVariable(name = "id") long id) {
        bunchService.unfavouriteBunch(id, authentication.getName());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Operation(summary = "Get All Bunches",
            description = "For logged-in user, favourite response field indicates if bunch is user favourite. " +
                    "For non-logged-in user, favourite response field is always 'false'.")
    @GetMapping
    public BunchResponse getAllBunches(
            Authentication authentication,
            @RequestParam(value = "pageNo", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        String usernameOrEmail = authentication != null ? authentication.getName() : null;
        return  bunchService.getAllBunches(pageNo, pageSize, sortBy, sortDir, usernameOrEmail);
    }

    @Operation(summary = "Get Bunch by Id")
    @GetMapping("/{id}")
    public ResponseEntity<BunchDto> getBunchById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(bunchService.getBunchById(id));
    }
}
