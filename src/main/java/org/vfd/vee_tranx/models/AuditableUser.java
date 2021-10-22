package org.vfd.vee_tranx.models;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditableUser {
	@CreatedBy
	private String createdBy;
	 
	@LastModifiedBy
	private String lastModifiedBy;
}
