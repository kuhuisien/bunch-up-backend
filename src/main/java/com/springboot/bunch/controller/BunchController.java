package com.springboot.bunch.controller;

import com.springboot.bunch.payload.BunchDto;
import com.springboot.bunch.service.BunchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bunches")
public class BunchController {

    private BunchService bunchService;

    public BunchController(BunchService bunchService) {
        this.bunchService = bunchService;
    }

    @PostMapping
    public ResponseEntity<BunchDto> createBunch(@RequestBody BunchDto bunchDto) {
        return new ResponseEntity<>(bunchService.createBunch(bunchDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<BunchDto> getAllBunches() {
        return  bunchService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BunchDto> getBunchById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(bunchService.getBunchById(id));
    }
}
