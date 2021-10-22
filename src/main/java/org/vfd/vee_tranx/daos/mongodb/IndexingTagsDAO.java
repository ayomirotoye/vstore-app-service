package org.vfd.vee_tranx.daos.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("tags")
public class IndexingTagsDAO {
	@Id
	private String id;
	@Indexed(unique = true)
	private String name;
}
