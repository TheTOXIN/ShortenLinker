package com.toxin.shorten.controller;

import com.toxin.shorten.service.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("QR")
public class QRController {

    private final QRService qrService;

    @Autowired
    public QRController(
        QRService qrService
    ) {
        this.qrService = qrService;
    }

    @GetMapping(
        value = "/{hash}",
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getImageWithMediaType(
        @PathVariable("hash") String hash
    ) {
        return qrService.load(hash);
    }

}
