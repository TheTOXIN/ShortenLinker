package com.toxin.shorten.service;

import com.toxin.shorten.dto.ShorterRequest;
import com.toxin.shorten.dto.ShorterResponse;
import com.toxin.shorten.entity.Linker;
import com.toxin.shorten.entity.Shorter;
import com.toxin.shorten.repository.ShorterRepository;
import com.toxin.shorten.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShorterService {

    private final ShorterRepository shorterRepository;
    private final QRService qrService;
    private final MD5Service md5Service;
    private final LinkerService linkerService;

    @Autowired
    public ShorterService(
        ShorterRepository shorterRepository,
        MD5Service md5Service,
        LinkerService linkerService,
        QRService qrService
    ) {
        this.shorterRepository = shorterRepository;
        this.md5Service = md5Service;
        this.linkerService = linkerService;
        this.qrService = qrService;
    }

    @Transactional
    public ShorterResponse shorter(ShorterRequest request) {
        String hash = md5Service.hash(request.getLink());
        String hashLink = HttpUtil.buildHashLink(request.getBaseUrl(), hash);

        Shorter shorter = shorterRepository.findByHash(hash);
        if (shorter == null) create(hash, hashLink, request);

        return new ShorterResponse(
            hash,
            hashLink
        );
    }

    private void create(String hash, String hashLink, ShorterRequest request) {
        Linker linker = linkerService.make(request.getLink(), hashLink);

        Shorter shorter = new Shorter();

        shorter.setHash(hash);
        shorter.setLinker(linker);

        shorterRepository.save(shorter);
    }

}
