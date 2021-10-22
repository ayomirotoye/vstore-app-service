package org.vfd.vee_tranx.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillerReq {
	private String id;
	private String billerId;
	private String billerName;
	private String categoryId;
	private String categoryName;
	private String tag;
}
