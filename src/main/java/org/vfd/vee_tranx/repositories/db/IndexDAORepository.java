package org.vfd.vee_tranx.repositories.db;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vfd.vee_tranx.daos.mongodb.IndexDetailsDAO_;

public interface IndexDAORepository extends MongoRepository<IndexDetailsDAO_, String> {
	Optional<IndexDetailsDAO_> findById(String id);

	void deleteByReference(String reference);

	Optional<IndexDetailsDAO_> findByReference(String reference);
	
//	Optional<List<IndexDetailsData>> searchIndex(String searchParam);
	
}
