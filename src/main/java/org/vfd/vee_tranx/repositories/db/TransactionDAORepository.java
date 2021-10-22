package org.vfd.vee_tranx.repositories.db;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vfd.vee_tranx.daos.mongodb.TransactionDAO;

public interface TransactionDAORepository extends MongoRepository<TransactionDAO, String> {
	Optional<TransactionDAO> findById(String id);

	void deleteByTransactionRef(String transactionRef);

	Optional<TransactionDAO> findByTransactionRef(String findByTransactionRef);
	
}
