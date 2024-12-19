package com.gutendex.libreria.record;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosResult(
        @JsonAlias("results") List<DatosLibro> resultLibro
) {
}
