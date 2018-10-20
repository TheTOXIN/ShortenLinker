package com.toxin.shorten.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShorterResponse {

    private String hash;
    private String hashLink;

}
