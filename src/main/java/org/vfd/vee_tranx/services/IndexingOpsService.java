package org.vfd.vee_tranx.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vfd.vee_tranx.configs.AppProperties;
import org.vfd.vee_tranx.daos.mongodb.BillerDAO;
import org.vfd.vee_tranx.daos.mongodb.IndexDetailsDAO_;
import org.vfd.vee_tranx.models.AccountLookupResponse;
import org.vfd.vee_tranx.models.CreateBillerReq;
import org.vfd.vee_tranx.models.IndexDetailsData;
import org.vfd.vee_tranx.repositories.db.BillerDAORepository;
import org.vfd.vee_tranx.repositories.db.IndexDAORepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class IndexingOpsService {
	private IndexDAORepository indexDAORepository;

	private MongoTemplate mongoTemplate;

	private ObjectMapper objectMapper;

	private RestTemplate restTemplate;

	private AppProperties appProperties;

	private BillerDAORepository billerDAORepo;

	@Autowired
	public IndexingOpsService(IndexDAORepository indexDAORepository, MongoTemplate mongoTemplate,
			ObjectMapper objectMapper, RestTemplate restTemplate, AppProperties appProperties,
			BillerDAORepository billerDAORepository) {
		this.indexDAORepository = indexDAORepository;
		this.mongoTemplate = mongoTemplate;
		this.objectMapper = objectMapper;
		this.restTemplate = restTemplate;
		this.appProperties = appProperties;
		this.billerDAORepo = billerDAORepository;
	}

	public List<IndexDetailsData> storeIndex(List<IndexDetailsData> indexDetailsData) {
		try {

			List<IndexDetailsDAO_> lss = indexDetailsData.stream()
					.map(x -> (IndexDetailsDAO_) copierTool(x, new IndexDetailsDAO_(), "LEFT_TO_RIGHT"))
					.collect(Collectors.toList());

			indexDAORepository.saveAll(lss);

			return indexDetailsData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<CreateBillerReq> storeBiller(List<CreateBillerReq> indexDetailsData) {
		try {

			List<BillerDAO> lss = indexDetailsData.stream()
					.map(x -> (BillerDAO) copierToolForBiller(x, new BillerDAO(), "LEFT_TO_RIGHT"))
					.collect(Collectors.toList());

			billerDAORepo.saveAll(lss);

			return indexDetailsData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Object copierToolForBiller(CreateBillerReq indexDetailsData, BillerDAO indexDetailsDAO_, String direction) {
		if (direction == "LEFT_TO_RIGHT") {
			BeanUtils.copyProperties(indexDetailsData, indexDetailsDAO_);

			return indexDetailsDAO_;
		} else {
			BeanUtils.copyProperties(indexDetailsDAO_, indexDetailsData);
			return indexDetailsData;
		}
	}

	@SuppressWarnings("unchecked")
	private Object copierTool(IndexDetailsData indexDetailsData, IndexDetailsDAO_ indexDetailsDAO_, String direction) {
		if (direction == "LEFT_TO_RIGHT") {
			BeanUtils.copyProperties(indexDetailsData, indexDetailsDAO_, new String[] { "data" });
			try {
				indexDetailsData.getData().put("tag", indexDetailsData.getTag());
				indexDetailsDAO_.setReference(indexDetailsData.getId() != null ? indexDetailsData.getId()
						: String.valueOf(System.currentTimeMillis()));
				indexDetailsDAO_.setFieldValues(indexDetailsData.getData().values());
				indexDetailsDAO_.setRawData(objectMapper.writeValueAsString(indexDetailsData.getData()));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				indexDetailsDAO_.setRawData(indexDetailsData.toString());
			}
			return indexDetailsDAO_;
		} else {
			BeanUtils.copyProperties(indexDetailsDAO_, indexDetailsData, new String[] { "data" });
			try {
				indexDetailsData.setData(objectMapper.readValue(indexDetailsDAO_.getRawData(), HashMap.class));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				indexDetailsData.setData(
						(HashMap<String, String>) Collections.singletonMap("data", indexDetailsDAO_.getRawData()));
			}
			return indexDetailsData;
		}
	}

	public List<IndexDetailsData> searchIndex(String searchQuery) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("fieldValues").regex(searchQuery));
//			TextQuery query = TextQuery.queryText(new TextCriteria().matching(searchQuery)).sortByScore()
//					.includeScore();
			List<IndexDetailsDAO_> records = mongoTemplate.find(query, IndexDetailsDAO_.class);
			if (!records.isEmpty()) {
				List<IndexDetailsData> lss = records.stream()
						.map(x -> (IndexDetailsData) copierTool(new IndexDetailsData(), x, "RIGHT_TO_LEFT"))
						.collect(Collectors.toList());
				return lss;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public AccountLookupResponse nameEnquiry(String accountNo) {
		try {
			ResponseEntity<AccountLookupResponse> res = restTemplate.exchange(appProperties.getLookupUrl() + accountNo,
					HttpMethod.GET, null, AccountLookupResponse.class);

			if (res != null && res.getStatusCode().is2xxSuccessful() && res.getBody().isExist()) {
				return res.getBody();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
