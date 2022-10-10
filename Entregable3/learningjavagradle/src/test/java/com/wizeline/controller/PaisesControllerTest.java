package com.wizeline.controller;


import com.wizeline.model.PaisesDTO;
import com.wizeline.service.PaisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaisesControllerTest {

    private static final Logger LOGGER = Logger.getLogger(PaisesControllerTest.class.getName());

    @Mock
    private PaisService servicioPais;

    @InjectMocks
    private PaisesController controladorPaises;

    @Test
    @DisplayName("Prueba Get Paises")
    public void DadaListaPaises_CuandoExistePeticionDePaises_EntoncesDevuelveListaDePaises() {
        //Organización
        LOGGER.info("Inicia Organizacion");
        LOGGER.info("Se agrega lista de paises a regresar cuando se haga la petición");
        List<PaisesDTO> listaPaises =
                List.of(new PaisesDTO("Argentina","500"),
                        new PaisesDTO("Afganistan","100"));
        LOGGER.info("Se crea la condición para el ejecutable");
        when(servicioPais.obtienePaises()).thenReturn(listaPaises);
        //Ejecución
        LOGGER.info("Se ejecuta el llamado al controlador");
        List<PaisesDTO> paises = controladorPaises.obtienePaises();
        //Verificación
        LOGGER.info("Se hace la verificación");
        assertEquals(listaPaises, paises);
    }

//    @Test
//    @DisplayName("Prueba Post Paises")
//    public void DadaUnaInsercionDePais_CuandoSeHacePost_EntoncesSeAgrega() {
//        //Organización
//        LOGGER.info("Inicia Organizacion");
//        LOGGER.info("Se agrega lista de paises a regresar cuando se haga la petición");
//        PaisesDTO pais = new PaisesDTO("Antigua y Barbuda","700");
//
//        LOGGER.info("Se crea la condición para el ejecutable");
//        when(servicioPais.agregaPais(pais)).thenReturn(listaPaises);
//        //Ejecución
//        LOGGER.info("Se ejecuta el llamado al controlador");
//        List<PaisesDTO> paises = controladorPaises.obtienePaises();
//        //Verificación
//        LOGGER.info("Se hace la verificación");
//        assertEquals(listaPaises, paises);
//    }

}
