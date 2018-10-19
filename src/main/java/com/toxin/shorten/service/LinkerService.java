package com.toxin.shorten.service;

import com.toxin.shorten.entity.Linker;
import com.toxin.shorten.entity.Shorter;
import com.toxin.shorten.repository.ShorterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LinkerService {

    private final ShorterRepository shorterRepository;
    private final QRService qrService;

    @Autowired
    public LinkerService(
        ShorterRepository shorterRepository,
        QRService qrService
    ) {
        this.shorterRepository = shorterRepository;
        this.qrService = qrService;
    }

    @Transactional
    public String linker(String hash) {
        Shorter shorter = shorterRepository.findByHash(hash);

        if (shorter == null) return "";

        Linker linker = shorter.getLinker();

        return linker.getLink();
    }

    public Linker make(String link, String hashLink) {
        Linker linker = new Linker();

        byte[] qrBytes = qrService.generate(hashLink);

        linker.setLink(link);
        linker.setQr(qrBytes);

        return linker;
    }

}
