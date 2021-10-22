package org.vfd.vee_tranx.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Ayomide.Akinrotoye
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AccountLookupResponse {
	private String accountNo;
	private String bvn;
	private String accountName;
	private String savingId;
	private String clientId;
	private String currency;
	private String status;
	private Boolean exist;
	private String dateOfBirth;

	public boolean isExist() {
		return this.exist;
	}
}
