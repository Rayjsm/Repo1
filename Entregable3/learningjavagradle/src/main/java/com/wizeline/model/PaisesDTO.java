package com.wizeline.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Paises")
public class PaisesDTO {

    @Id
    private String pais;
    private String poblacion;

    public PaisesDTO(String pais, String poblacion) {
        this.pais = pais;
        this.poblacion = poblacion;
    }
    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }


}
