package com.toxin.shorten.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShorterRequest {

    private String link;
    private String baseUrl;

}
