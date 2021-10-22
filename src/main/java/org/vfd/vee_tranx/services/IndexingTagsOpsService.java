package org.vfd.vee_tranx.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.vfd.vee_tranx.daos.mongodb.IndexingTagsDAO;
import org.vfd.vee_tranx.repositories.db.IndexTagsDAORepository;

@Service
public class IndexingTagsOpsService {
	private IndexTagsDAORepository indexTagsDAORepository;

	public IndexingTagsOpsService(IndexTagsDAORepository indexTagsDAORepository) {
		this.indexTagsDAORepository = indexTagsDAORepository;
	}

	public List<IndexingTagsDAO> storeTags(List<IndexingTagsDAO> indexingTagsDAOs) {
		try {

			indexingTagsDAOs = indexTagsDAORepository.saveAll(indexingTagsDAOs);

			return indexingTagsDAOs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public IndexingTagsDAO fetchTags(String name) {
		try {

			Optional<IndexingTagsDAO> indexingTagsDAOs = indexTagsDAORepository.findByName(name);

			return indexingTagsDAOs.get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<IndexingTagsDAO> fetchAllTags() {
		try {

			List<IndexingTagsDAO> indexingTagsDAOs = indexTagsDAORepository.findAll();

			return indexingTagsDAOs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
