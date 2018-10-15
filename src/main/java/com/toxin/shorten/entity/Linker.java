package com.toxin.shorten.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Linker {

    @Id
    @GeneratedValue
    private UUID id;

    @Max(100)
    @Min(10)
    private String link;

    @Lob
    private byte[] qr;

}
