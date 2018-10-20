package com.toxin.shorten.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Linker {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(length = 500, nullable = false)
    private String link;

    @Lob
    @Column(nullable = false)
    private byte[] qr;

}
