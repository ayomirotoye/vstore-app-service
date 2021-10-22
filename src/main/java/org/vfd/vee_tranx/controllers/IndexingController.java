package org.vfd.vee_tranx.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vfd.vee_tranx.enums.ResponseCodeEnum;
import org.vfd.vee_tranx.models.AccountLookupResponse;
import org.vfd.vee_tranx.models.CreateBillerReq;
import org.vfd.vee_tranx.models.GenericResponse;
import org.vfd.vee_tranx.models.IndexDetailsData;
import org.vfd.vee_tranx.services.IndexingOpsService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("vstore")
@Slf4j
@Validated
public class IndexingController {
	private IndexingOpsService indexingOpsService;

	public IndexingController(IndexingOpsService indexingOpsService) {
		this.indexingOpsService = indexingOpsService;
	}

	@PostMapping("/store")
	public ResponseEntity<?> storeDocs(@RequestBody List<IndexDetailsData> indexDetailsDatas) {
		List<IndexDetailsData> traDao = indexingOpsService.storeIndex(indexDetailsDatas);
		if (traDao != null) {
			return ResponseEntity.ok().body(traDao);
		} else {
			return ResponseEntity.badRequest()
					.body(GenericResponse.builder().message(ResponseCodeEnum.BAD_REQUEST.getMessage()).build());
		}

	}

	@GetMapping
	public ResponseEntity<?> getDocs(@RequestParam(required = true) @NotNull String searchBy) {
		if (searchBy != null && !searchBy.isEmpty()) {

			List<IndexDetailsData> traDao = indexingOpsService.searchIndex(searchBy);
			if (traDao != null) {
				return ResponseEntity.ok().body(traDao);
			} else {
				return ResponseEntity.ok()
						.body(GenericResponse.builder().message(ResponseCodeEnum.NO_RECORD.getMessage()).build());
			}
		} else {
			return ResponseEntity.ok()
					.body(GenericResponse.builder().message(ResponseCodeEnum.NO_RECORD.getMessage()).build());
		}

	}

	@GetMapping("categories")
	public ResponseEntity<?> getCategories() {
		List<HashMap<String, String>> traDao = new ArrayList<>();
		HashMap<String, String> hMap = new HashMap<>();
		hMap.put("id", "ELECTRICITY");
		hMap.put("name", "Electricity");
		traDao.add(hMap);
		HashMap<String, String> hMap2 = new HashMap<>();
		hMap2.put("id", "AIRTIME");
		hMap2.put("name", "Airtime");
		traDao.add(hMap2);
		return ResponseEntity.ok().body(traDao);

	}

	@PostMapping("/biller")
	public ResponseEntity<?> storeBiller(@RequestBody List<CreateBillerReq> indexDetailsDatas) {
		List<CreateBillerReq> traDao = indexingOpsService.storeBiller(indexDetailsDatas);
		if (traDao != null) {
			return ResponseEntity.ok().body(traDao);
		} else {
			return ResponseEntity.badRequest()
					.body(GenericResponse.builder().message(ResponseCodeEnum.BAD_REQUEST.getMessage()).build());
		}

	}

	@GetMapping("name-enquiry")
	public ResponseEntity<?> nameEnquiry(@RequestParam String accountNo) {
		try {
			AccountLookupResponse res = indexingOpsService.nameEnquiry(accountNo);

			if (res != null) {
				return ResponseEntity.ok().body(res);
			}
		} catch (Exception e) {
			log.info("ERROR OCURRED" + e);
		}
		return ResponseEntity.badRequest()
				.body(GenericResponse.builder().message(ResponseCodeEnum.NO_RECORD.getMessage()).build());

	}

}
