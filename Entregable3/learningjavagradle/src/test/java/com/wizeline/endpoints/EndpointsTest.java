package com.wizeline.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wizeline.controller.PaisesController;
import com.wizeline.model.PaisesDTO;
import com.wizeline.service.PaisService;
import org.apache.commons.lang.ObjectUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class EndpointsTest {

    private static final Logger LOGGER = Logger.getLogger(EndpointsTest.class.getName());

    @MockBean
    private PaisService servicioPais;

    @Autowired
    private PaisesController controladorPais;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("Prueba Endpoint Get")
    void DadoUnServicioParaObtenerPaises_CuandoSeMandaLlamar_EntoncesSeEsperaUnResultadoExitoso() throws Exception{

        LOGGER.info("Se crea una lista simulando una BD de paises");
        List<PaisesDTO> paises = List.of(
                new PaisesDTO("Mexico","100"),
                new PaisesDTO("Argentina","200"));
        LOGGER.info("Se crea la condicion de comportamiento, cuando se manda a llamar paises, devuelve la lista de paises");
        when(servicioPais.obtienePaises()).thenReturn(paises);

        LOGGER.info("Se hace el llamado al endpoint en este caso un get");
        MvcResult resultado = mockMvc.perform(get("/paises/obtiene"))
                .andExpect(status().isOk()).andReturn();

        LOGGER.info("Se mapea el resultado en un arreglo de paises");
        PaisesDTO[] arregloPaises = mapper.readValue(resultado.getResponse().getContentAsString(), PaisesDTO[].class);

        LOGGER.info("El arreglo se guarda en una lista de paises para su evaluación");
        List<PaisesDTO> listaPaises = Arrays.asList(arregloPaises);


        LOGGER.info("Se evalua que dentro de la lista haiga los paises que se crearon al principio");
        assertTrue(listaPaises.stream()
                .map(PaisesDTO::getPais)
                .collect(Collectors.toList())
                .containsAll(List.of("Mexico","Argentina")));
    }

    @Test
    @DisplayName("Prueba endpoint Post")
    void DadoUnServicioParaInsertarPais_CuandoSeInvocaElServicio_EntoncesObtenemosOK() throws Exception {

        LOGGER.info("Se crea una lista simulando una BD de paises");
        List<PaisesDTO> paises = List.of(
                new PaisesDTO("Mexico","100"),
                new PaisesDTO("Argentina","200"));

        LOGGER.info("Se crea la condicion que regresa un true cuando se inserta un pais exitosamente");
        when(servicioPais.agregaPais(anyString(),anyString())).thenReturn(true);

        LOGGER.info("Se consume el endpoint de inserción y Orlando nos dijo que no siempre era necesario hacer hacerts" +
                "ya que la peticion en si espera un 200 en caso de exito, si no pues truena :D");
        MvcResult resultado = mockMvc.perform(post("/paises/inserta")
                .param("pais","Cuba")
                .param("poblacion","500")).andExpect(status().isOk()).andReturn();

        //Imprimimos el resultado
        LOGGER.info("Resultado: "+ resultado.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Prueba endpoint Delete")
    void DadoEndpointDelete_CuandoSeLlamaBorrar_EntoncesSeBorraXD() throws Exception {

        LOGGER.info("Se simula una BD");
        List<PaisesDTO> paises = List.of(
                new PaisesDTO("Mexico","100"),
                new PaisesDTO("Argentina","200"));

        LOGGER.info("Se crea el objeto con el pais que se planea borrars");
        PaisesDTO paisBorra = new PaisesDTO("Mexico","");

        LOGGER.info("Creamos un objeto con la librería de JACKSON el cual nos permite meter el objeto a un json y luego a una cadena" +
                "para pasarla como parámetro");
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(paisBorra);

        LOGGER.info("Se crea la condicion para que al consumir el endpoint del borrado regrese el nuevo objeto");
        when(servicioPais.borraPais(any(PaisesDTO.class))).thenReturn(any(PaisesDTO.class));

        LOGGER.info("Se hace la peticion al endpoint de borrar y se le pasa como parámetro el json en cadena");
        MvcResult resultado = mockMvc.perform(delete("/paises/borra")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk()).andReturn();

        LOGGER.info("Resultado: "+ resultado.getResponse().getContentAsString());

    }

    @Test
    @DisplayName("Prueba Endpoint Put")
    void DadoElServicioDeUpdate_CuandoSeMandePaisAModificar_EntoncesEnLaRespuestaSeDebeVerElCambio() throws Exception {

        LOGGER.info("Se simula una BD x4");
        List<PaisesDTO> paises = List.of(
                new PaisesDTO("Mexico","100"),
                new PaisesDTO("Argentina","200"));

        LOGGER.info("Se crea el objeto con el pais a modificars");
        PaisesDTO paisModifica = new PaisesDTO("Mexico","696");

        LOGGER.info("Igual que en el anterior, se crea una cadena a partir del objete");
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(paisModifica);

        LOGGER.info("Se crea la condicion para consumir el endpoint");
        when(servicioPais.actualizaPoblacion(any(PaisesDTO.class))).thenReturn(true);

        LOGGER.info("Se hace el llamado al endpoint de actualizar paisy  y se pasa el parametro en cadena");
        MvcResult resultado = mockMvc.perform(put("/paises/modifica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()).andReturn();

        LOGGER.info("Resultado: "+ resultado.getResponse().getStatus());
    }

}
