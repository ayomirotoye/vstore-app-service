package org.vfd.vee_tranx.daos.mongodb;

import javax.validation.constraints.Min;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("transactions")
public class TransactionDAO {
	@Id
	private String id;
	@Indexed(unique = true)	
	private String transactionRef;
	@Min(value = 1)
	private double amount;
	private String username;
	private String narration;
	private String transactionDate;
}
