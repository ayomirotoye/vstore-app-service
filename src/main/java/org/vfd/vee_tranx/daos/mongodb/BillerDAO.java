package org.vfd.vee_tranx.daos.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("billers")
public class BillerDAO {
	@Id
	private String id;
	private String billerId;
	private String tag;
	@Indexed(unique = true)
	@TextIndexed
	private String billerName;
	private String categoryId;
	@TextIndexed
	private String categoryName;
}
