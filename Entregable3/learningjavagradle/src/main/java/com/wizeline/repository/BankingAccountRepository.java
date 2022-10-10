package com.wizeline.repository;

import com.wizeline.model.BankAccountDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long> {
}
