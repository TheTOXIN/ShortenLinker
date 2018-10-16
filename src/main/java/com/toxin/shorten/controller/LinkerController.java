package com.toxin.shorten.controller;

import com.toxin.shorten.service.LinkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("link")
public class LinkerController {

    private final LinkerService linkerService;

    @Autowired
    public LinkerController(
        LinkerService linkerService
    ) {
        this.linkerService = linkerService;
    }

    @GetMapping("/{hash}")
    private void linker(
        HttpServletResponse response,
        @PathVariable("hash") String hash
    ) {
        response.setHeader("Location", linkerService.linker(hash));
        response.setStatus(200);
    }

}
