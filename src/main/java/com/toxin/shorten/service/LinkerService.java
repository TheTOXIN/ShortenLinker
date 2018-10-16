package com.toxin.shorten.service;

import com.toxin.shorten.repository.LinkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkerService {

    private final LinkerRepository linkerRepository;

    @Autowired
    public LinkerService(
        LinkerRepository linkerRepository
    ) {
        this.linkerRepository = linkerRepository;
    }

    public String linker(String hash) {
        return hash;
    }

}
