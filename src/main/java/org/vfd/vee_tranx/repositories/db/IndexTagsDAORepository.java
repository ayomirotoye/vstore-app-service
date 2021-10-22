package org.vfd.vee_tranx.repositories.db;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vfd.vee_tranx.daos.mongodb.IndexingTagsDAO;

public interface IndexTagsDAORepository extends MongoRepository<IndexingTagsDAO, String> {
	Optional<IndexingTagsDAO> findById(String id);

	void deleteById(String id);

	Optional<IndexingTagsDAO> findByName(String name);
	
//	Optional<List<IndexDetailsData>> searchIndex(String searchParam);
	
}
