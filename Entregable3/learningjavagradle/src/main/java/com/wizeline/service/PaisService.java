package com.wizeline.service;

import com.jayway.jsonpath.Criteria;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.wizeline.model.PaisesDTO;
import com.wizeline.model.ApiPublica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PaisService {

    @Autowired
    private MongoTemplate mongoTemplate;
    private static final String ID ="_id";

    public ApiPublica consultaApiPublica() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/todos/1";
        return restTemplate.getForEntity(url, ApiPublica.class).getBody();
    }

    public Boolean agregaPais(String pais, String poblacion) {

        try {
            PaisesDTO paisNuevo = new PaisesDTO(pais,poblacion);
            mongoTemplate.save(paisNuevo);
            return  true;
        }catch (Exception e)
        {
            return false;
        }

    }

    public List<PaisesDTO> obtienePaises() {
        return mongoTemplate.findAll(PaisesDTO.class);
    }

    public Boolean actualizaPoblacion(PaisesDTO request) {
        Query query = Query.query((CriteriaDefinition) Criteria.where(ID).is(request.getPais()));
        UpdateResult resultade = mongoTemplate.updateFirst(query, Update.update("poblacion",request.getPoblacion()),PaisesDTO.class);
        return resultade.wasAcknowledged();
    }

    public PaisesDTO borraPais(PaisesDTO request) {
        Query query = Query.query((CriteriaDefinition) Criteria.where(ID).is(request.getPais()));

        return mongoTemplate.findAndRemove(query,PaisesDTO.class);
    }


}
