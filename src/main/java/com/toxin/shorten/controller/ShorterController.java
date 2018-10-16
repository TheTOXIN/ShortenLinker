package com.toxin.shorten.controller;

import com.toxin.shorten.dto.ShorterRequest;
import com.toxin.shorten.dto.ShorterResponse;
import com.toxin.shorten.service.ShorterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("short")
public class ShorterController {

    private final ShorterService shorterService;

    @Autowired
    public ShorterController(
        ShorterService shorterService
    ) {
        this.shorterService = shorterService;
    }

    @PostMapping
    private ShorterResponse shorter(ShorterRequest request) {
        return shorterService.shorter(request);
    }

}
