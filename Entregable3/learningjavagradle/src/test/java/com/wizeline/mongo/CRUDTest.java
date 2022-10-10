package com.wizeline.mongo;

import com.wizeline.model.PaisesDTO;
import com.wizeline.repository.RepositorioPais;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class CRUDTest {

    private static final Logger LOGGER = Logger.getLogger(CRUDTest.class.getName());

    @Autowired
    private RepositorioPais repositorioPais;

    @BeforeAll
    public void Organizar() {
        LOGGER.info("Se Pobla la Base de Datos en un Before All");
        repositorioPais.save(new PaisesDTO("Mexico","100"));
        repositorioPais.save(new PaisesDTO("Argentina","70"));
    }


    @Test
    @DisplayName("Prueba Read")
    public void DadoUnaBDDePaises_CuandoSeMandeLlamar_EntoncesDevuelveLosPaises() {
        LOGGER.info("Se guarda en una lista los paises de la base de datos");
        List<PaisesDTO> listaPaises = repositorioPais.findAll();

        LOGGER.info("Se verifica que el tamaño de la lista sea del numero de paises que ya sabemos que hay");
        LOGGER.info("Se mapea y se verifica que estén los 2 paises que agregamos");
        assertAll(
                () -> assertEquals(2,listaPaises.size()),
                () -> assertTrue(listaPaises.stream().map(PaisesDTO::getPais).collect(Collectors.toList()).containsAll(List.of("Mexico","Argentina")))
        );
    }

    @Test
    @DisplayName("Prueba Create")
    public void DadoUnaBDDePaises_CuandoSeInserteUnPais_EntoncesAlConsultarDeNuevoSeVeraElPaisAgregado()
    {
        LOGGER.info("Se guarda un nuevo pais");
        PaisesDTO paises = new PaisesDTO("Antigua y Barbuda","200");
        PaisesDTO paisGuardado = repositorioPais.save(paises);
        LOGGER.info("Se verifica que el pais se haya agregado");
        assertEquals("Antigua y Barbuda", paisGuardado.getPais());
    }

    @Test
    @DisplayName("Prueba Delete")
    public void DadaUnaBD_CuandoSeElimineUnPais_EntoncesDevuelveLosPaisesRestantes() {
        LOGGER.info("Se hace el borrado de un pais por ID");
        repositorioPais.deleteById("Mexico");
        List<PaisesDTO> paises = repositorioPais.findAll();
        LOGGER.info("Se verifica que el tamaño de la lista corresponda al nuevo numero de paises");
        assertEquals(1,paises.size());
    }

    @Test
    @DisplayName("Prueba Update")
    public void DadaUnaBD_CuandoSeActualiceUnPais_EntoncesDevuelveLosPaisesConLasModificacionesCorrespondientes() {
        LOGGER.info("Se Prepara todo para actualizar un pais, mediante un guardado");
        PaisesDTO paises = repositorioPais.findById("Mexico").get();
        paises.setPoblacion("500");
        LOGGER.info("Se gurda el pais con la nueva poblacion");
        repositorioPais.save(paises);

        paises = repositorioPais.findById("Mexico").get();

        LOGGER.info("Se verifica que la nueva poblacion del pais, sea la que nosotros definimos");
        assertEquals("500",paises.getPoblacion());
    }

}
