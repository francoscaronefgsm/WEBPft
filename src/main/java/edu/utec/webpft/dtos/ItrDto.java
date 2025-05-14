package edu.utec.webpft.dtos;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ItrDto {
    private Long id;
    private String nombre;
}

