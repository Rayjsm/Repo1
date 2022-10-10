package com.wizeline.controller;

import com.wizeline.model.PaisesDTO;
import com.wizeline.model.ApiPublica;
import com.wizeline.service.PaisService;
import feign.HeaderMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/paises")
@RestController
public class PaisesController {

    @Autowired
    private PaisService paisService;

    @PostMapping("/inserta")
    public Boolean insertaPais(@RequestParam String pais, @RequestParam String poblacion) {
        try {
            paisService.agregaPais(pais,poblacion);
            return  true;
        }catch (Exception e)
        {
            return false;
        }


    }
    @GetMapping("/obtiene")
    public List<PaisesDTO> obtienePaises() {
        return paisService.obtienePaises();
    }

    @PutMapping("/modifica")
    public Boolean modificaPais(@RequestBody PaisesDTO pais) {
        return paisService.actualizaPoblacion(pais);
    }

    @DeleteMapping("/borra")
    public PaisesDTO borraPais(@RequestBody PaisesDTO pais) {
        return  paisService.borraPais(pais);
    }

    @GetMapping("/api/publica")
    public ApiPublica consultaApi(){
        return paisService.consultaApiPublica();
    }

}
