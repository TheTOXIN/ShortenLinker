package com.toxin.shorten.service;

import com.toxin.shorten.dto.ShorterRequest;
import com.toxin.shorten.dto.ShorterResponse;
import com.toxin.shorten.repository.ShorterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShorterService {

    private final ShorterRepository shorterRepository;

    @Autowired
    public ShorterService(
        ShorterRepository shorterRepository
    ) {
        this.shorterRepository = shorterRepository;
    }

    public ShorterResponse shorter(ShorterRequest request) {
        return null;
    }

}
