package org.vfd.vee_tranx.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vfd.vee_tranx.daos.mongodb.IndexingTagsDAO;
import org.vfd.vee_tranx.enums.ResponseCodeEnum;
import org.vfd.vee_tranx.models.GenericResponse;
import org.vfd.vee_tranx.services.IndexingTagsOpsService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("vstore")
public class TagController {
	private IndexingTagsOpsService indexingTagsOpsService;

	public TagController(IndexingTagsOpsService indexingTagsOpsService) {
		this.indexingTagsOpsService = indexingTagsOpsService;
	}

	@PostMapping("/tags")
	public ResponseEntity<?> createTags(@RequestBody List<IndexingTagsDAO> indexingTagsDAOs) {
		List<IndexingTagsDAO> traDao = indexingTagsOpsService.storeTags(indexingTagsDAOs);
		if (traDao != null) {
			return ResponseEntity.ok().body(traDao);
		} else {
			return ResponseEntity.badRequest()
					.body(GenericResponse.builder().message(ResponseCodeEnum.BAD_REQUEST.getMessage()).build());
		}

	}

	@GetMapping("/tags")
	public ResponseEntity<?> getTags(@RequestParam(required = false) String name) {
		if (name != null) {
			IndexingTagsDAO traDao = indexingTagsOpsService.fetchTags(name);
			return ResponseEntity.ok().body(traDao);
		} else {
			List<IndexingTagsDAO> records = indexingTagsOpsService.fetchAllTags();
			return ResponseEntity.ok().body(records);
		}

	}
}
