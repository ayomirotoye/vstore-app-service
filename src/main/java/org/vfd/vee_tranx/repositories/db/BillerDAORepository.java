package org.vfd.vee_tranx.repositories.db;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vfd.vee_tranx.daos.mongodb.BillerDAO;

public interface BillerDAORepository extends MongoRepository<BillerDAO, String> {
	Optional<BillerDAO> findById(String id);

	void deleteById	(String reference);

	
}
