package org.vfd.vee_tranx.services;

import java.util.List;
import java.util.Optional;

import org.joda.time.LocalDate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.vfd.vee_tranx.daos.mongodb.TransactionDAO;
import org.vfd.vee_tranx.repositories.db.TransactionDAORepository;

@Service
public class TransactionOpsService {
	private TransactionDAORepository tDaoRepository;

	public TransactionOpsService(TransactionDAORepository tDaoRepository) {
		this.tDaoRepository = tDaoRepository;
	}

	public TransactionDAO createTransaction(TransactionDAO transactionDAO) {
		try {
			String ref = String.valueOf(System.currentTimeMillis());
			transactionDAO.setTransactionRef(ref);
			transactionDAO.setTransactionDate(LocalDate.now().toString());
			TransactionDAO tDao = tDaoRepository.save(transactionDAO);
			
			return tDao;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//	READ
	public TransactionDAO updateTransaction(TransactionDAO transactionDAO) {
		try {
			Optional<TransactionDAO> tDao = tDaoRepository.findByTransactionRef(transactionDAO.getTransactionRef());
			if (tDao.isPresent()) {
				TransactionDAO td = tDao.get();
				BeanUtils.copyProperties(transactionDAO, td, new String[] { "id", "transactionDate" });

				tDaoRepository.save(td);
				
				return td;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	UPDATE
//	DELETE
	public String deleteTransaction(String transactionRef) {
		try {
			tDaoRepository.deleteByTransactionRef(transactionRef);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return transactionRef;
	}

	public TransactionDAO getTransaction(String transactionRef) {
		try {
			Optional<TransactionDAO> tDao = tDaoRepository.findByTransactionRef(transactionRef);
			if (tDao.isPresent()) {
				TransactionDAO td = tDao.get();
				return td;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<TransactionDAO> getAllTransaction() {
		try {
			List<TransactionDAO> tDao = tDaoRepository.findAll();
			if (!tDao.isEmpty()) {

				return tDao;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
