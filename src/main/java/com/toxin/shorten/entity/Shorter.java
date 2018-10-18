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
public class Shorter {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String hash;

    @JoinColumn(nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private Linker linker;

}
