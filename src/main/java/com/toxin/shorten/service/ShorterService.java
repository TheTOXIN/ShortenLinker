package com.toxin.shorten.service;

import com.toxin.shorten.dto.ShorterRequest;
import com.toxin.shorten.dto.ShorterResponse;
import com.toxin.shorten.entity.Linker;
import com.toxin.shorten.entity.Shorter;
import com.toxin.shorten.repository.ShorterRepository;
import com.toxin.shorten.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShorterService {

    private final static int HASH_BATCH = 32 / 8;

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

    public ShorterResponse shorter(ShorterRequest request) {
        String hash = md5Service.hash(request.getLink());
        String shortHash = shortHash(hash);

        String hashLink = HttpUtil.buildHashLink(request.getBaseUrl(), shortHash);

        Shorter shorter = shorterRepository.findByHash(shortHash);

        if (shorter == null) shorter = create(shortHash, hashLink, request);

        String pathQR = qrService.load(shorter.getLinker(), shortHash);

        return new ShorterResponse(
            pathQR,
            hashLink
        );
    }

    private Shorter create(String hash, String hashLink, ShorterRequest request) {
        Linker linker = linkerService.make(request.getLink(), hashLink);

        Shorter shorter = new Shorter();

        shorter.setHash(hash);
        shorter.setLinker(linker);

        shorterRepository.save(shorter);

        return shorter;
    }

    private String shortHash(String hash) {
        String result = "";

        for (int i = 0; i < hash.length(); i += HASH_BATCH) {
            int batch = 0;

            for (int j = 0; j < HASH_BATCH; j++) {
                String hex = String.valueOf(hash.charAt(i));
                batch += Integer.parseInt(hex, 16);
            }

            batch /= HASH_BATCH;

            result += Integer.toHexString(batch);
        }

        return result;
    }

}
