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

    @Column(length = 100)
    private String link;

    @Lob
    private byte[] qr;

    @OneToOne
    private Shorter shorter;

}
