package org.vfd.vee_tranx.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vfd.vee_tranx.daos.mongodb.TransactionDAO;
import org.vfd.vee_tranx.enums.ResponseCodeEnum;
import org.vfd.vee_tranx.models.GenericResponse;
import org.vfd.vee_tranx.services.TransactionOpsService;

@CrossOrigin(origins = "*")
@RestController
public class TransactionController {

	private TransactionOpsService transactionOpsService;

	public TransactionController(TransactionOpsService transactionOpsService) {
		this.transactionOpsService = transactionOpsService;
	}

	@GetMapping("/transactions")
	public ResponseEntity<?> getTnx(@RequestParam(required = false) String transactionRef) {
		if (transactionRef != null) {
			TransactionDAO traDao = transactionOpsService.getTransaction(transactionRef);
			if (traDao != null && traDao.getId() != "") {
				return ResponseEntity.ok().body(traDao);
			} else {
				return ResponseEntity.ok()
						.body(GenericResponse.builder().message(ResponseCodeEnum.NO_RECORD.getMessage()).build());
			}
		} else {
			List<TransactionDAO> traDao = transactionOpsService.getAllTransaction();
			if (traDao != null) {
				return ResponseEntity.ok().body(traDao);
			} else {
				return ResponseEntity.ok()
						.body(GenericResponse.builder().message(ResponseCodeEnum.NO_RECORD.getMessage()).build());
			}
		}
	}

	@PostMapping("/transactions")
	public ResponseEntity<?> createTnx(@RequestBody TransactionDAO transactionDAO) {
		TransactionDAO traDao = transactionOpsService.createTransaction(transactionDAO);
		if (traDao != null && traDao.getId() != "") {
			return ResponseEntity.ok().body(traDao);
		} else {
			return ResponseEntity.badRequest()
					.body(GenericResponse.builder().message(ResponseCodeEnum.BAD_REQUEST.getMessage()).build());
		}

	}

	@PutMapping("/transactions/{transactionRef}")
	public ResponseEntity<?> updateTnx(@RequestBody TransactionDAO transactionDAO,
			@PathVariable String transactionRef) {
		transactionDAO.setTransactionRef(transactionRef);
		TransactionDAO traDao = transactionOpsService.updateTransaction(transactionDAO);
		if (traDao != null && traDao.getId() != "") {
			return ResponseEntity.ok().body(traDao);
		} else {
			return ResponseEntity.badRequest()
					.body(GenericResponse.builder().message(ResponseCodeEnum.BAD_REQUEST.getMessage()).build());
		}

	}

	@DeleteMapping("/transactions/{transactionRef}")
	public ResponseEntity<?> deleteTnx(@PathVariable String transactionRef) {
		String res = transactionOpsService.deleteTransaction(transactionRef);
		return res != null ? ResponseEntity.ok().body(ResponseCodeEnum.OK.getMessage())
				: ResponseEntity.badRequest().body(ResponseCodeEnum.BAD_REQUEST.getMessage());
	}
}
