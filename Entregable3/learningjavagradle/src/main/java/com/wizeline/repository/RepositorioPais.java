package com.wizeline.repository;

import com.wizeline.model.PaisesDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioPais extends MongoRepository<PaisesDTO,String> {
}
