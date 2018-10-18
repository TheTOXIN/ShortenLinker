package com.toxin.shorten.controller;

import com.toxin.shorten.dto.ShorterRequest;
import com.toxin.shorten.dto.ShorterResponse;
import com.toxin.shorten.service.ShorterService;
import com.toxin.shorten.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    private ShorterResponse shorter(
        HttpServletRequest httpServletRequest,
        @RequestBody ShorterRequest request
    ) {
        request.setBaseUrl(HttpUtil.getURLBase(httpServletRequest));
        return shorterService.shorter(request);
    }

}
